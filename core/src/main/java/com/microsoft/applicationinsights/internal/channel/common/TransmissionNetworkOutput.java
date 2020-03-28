/*
 * ApplicationInsights-Java
 * Copyright (c) Microsoft Corporation
 * All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the ""Software""), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package com.microsoft.applicationinsights.internal.channel.common;


import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.common.Preconditions;
import com.microsoft.applicationinsights.internal.channel.TransmissionDispatcher;
import com.microsoft.applicationinsights.internal.channel.TransmissionHandlerArgs;
import com.microsoft.applicationinsights.internal.channel.TransmissionOutput;
import com.microsoft.applicationinsights.internal.logger.InternalLogger;

import reactor.core.publisher.Mono;

/**
 * The class is responsible for the actual sending of
 * {@link com.microsoft.applicationinsights.internal.channel.common.Transmission}
 *
 * The class uses <strike>Apache's HttpClient</strike> Spring WebClient framework for that.
 *
 * Created by gupele on 12/18/2014.
 * Update by gdufrene on 3/28/2020.
 */
public final class TransmissionNetworkOutput implements TransmissionOutput {
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CONTENT_ENCODING_HEADER = "Content-Encoding";
    private static final String RESPONSE_THROTTLING_HEADER = "Retry-After";

    public static final String DEFAULT_SERVER_URI = "https://dc.services.visualstudio.com/v2/track";

    // For future use: re-send a failed transmission back to the dispatcher
    private TransmissionDispatcher transmissionDispatcher;

    private String serverUri;

    private volatile boolean stopped;
    private volatile TelemetryConfiguration configuration;

    // Use one instance for optimization (?)
    private WebClient httpClient;

    private TransmissionPolicyManager transmissionPolicyManager;

    /**
     * Creates an instance of the network transmission class.
     *
     * @param transmissionPolicyManager The transmission policy used to mark this sender active or blocked.
     * @return
     * @deprecated Use {@link #create(TelemetryConfiguration, TransmissionPolicyManager)}
     */
    @Deprecated
    public static TransmissionNetworkOutput create(TransmissionPolicyManager transmissionPolicyManager) {
        return new TransmissionNetworkOutput(null, null, transmissionPolicyManager);
    }

    /**
     * Creates an instance of the network transmission class.
     *
     * @param endpoint The HTTP endpoint to send our telemetry too.
     * @param transmissionPolicyManager The transmission policy used to mark this sender active or blocked.
     * @return
     * @deprecated Use {@link #create(TelemetryConfiguration, TransmissionPolicyManager)}
     */
    @Deprecated
    public static TransmissionNetworkOutput create(@Nullable String endpoint, TransmissionPolicyManager transmissionPolicyManager) {
        return new TransmissionNetworkOutput(endpoint, null, transmissionPolicyManager);
    }

    public static TransmissionNetworkOutput create(TelemetryConfiguration configuration, TransmissionPolicyManager transmissionPolicyManager) {
        return new TransmissionNetworkOutput(null, configuration, transmissionPolicyManager);
    }

    private TransmissionNetworkOutput(@Nullable String serverUri, @Nullable TelemetryConfiguration configuration, TransmissionPolicyManager transmissionPolicyManager) {
        Preconditions.checkNotNull(transmissionPolicyManager, "transmissionPolicyManager should be a valid non-null value");
        this.serverUri = serverUri;
        this.configuration = configuration;
        if (StringUtils.isNotEmpty(serverUri)) {
            InternalLogger.INSTANCE.warn("Setting the endpoint via the <Channel> element is deprecated and will be removed in a future version. Use the top-level element <ConnectionString>.");
        }
        // httpClient = ApacheSenderFactory.INSTANCE.create();
        this.transmissionPolicyManager = transmissionPolicyManager;
        stopped = false;
        if (InternalLogger.INSTANCE.isTraceEnabled()) {
            InternalLogger.INSTANCE.trace("%s using endpoint %s", TransmissionNetworkOutput.class.getSimpleName(), getIngestionEndpoint());
        }
    }
    /**
     * Used to inject the dispatcher used for this output so it can be injected to the retry logic.
     *
     * @param transmissionDispatcher The dispatcher to be injected.
     */
    public void setTransmissionDispatcher(TransmissionDispatcher transmissionDispatcher) {
        this.transmissionDispatcher = transmissionDispatcher;
    }

    /**
     * Stops all threads from sending data.
     *
     * @param timeout
     *            The timeout to wait, which is not relevant here.
     * @param timeUnit
     *            The time unit, which is not relevant in this method.
     */
    @Override
    public synchronized void stop(long timeout, TimeUnit timeUnit) {
        if (stopped) {
            return;
        }

        // httpClient.close();
        stopped = true;
    }

    /**
     * Tries to send a
     * {@link com.microsoft.applicationinsights.internal.channel.common.Transmission}
     * The thread that calls that method might be suspended if there is a throttling
     * issues, in any case the thread that enters this method is responsive for
     * 'stop' request that might be issued by the application.
     *
     * @param transmission
     *            The data to send
     * @return True when done.
     */
    @Override
    public boolean send(Transmission transmission) {
        if (!stopped) {
            // If we're not stopped but in a blocked state then fail to second
            // TransmissionOutput
            if (transmissionPolicyManager.getTransmissionPolicyState().getCurrentState() != TransmissionPolicy.UNBLOCKED) {
                return false;
            }

            // HttpResponse response = null;
            // HttpPost request = null;
            int code = 0;
            String reason = null;
            String respString = null;
            Throwable ex = null;
            String retryAfterHeader = null;

            // POST the transmission data to the endpoint
            ClientResponse response = 
            	createTransmissionPostRequest(transmission)
            	.doOnError(error -> {
            		InternalLogger.INSTANCE.error("Failed to send, unexpected exception.%nStack Trace:%n%s", ExceptionUtils.getStackTrace(error));
            	})
            	.block();
            code = response.statusCode().value();
            reason = response.statusCode().getReasonPhrase();
            List<String> throttling = response.headers().header(RESPONSE_THROTTLING_HEADER);
            if ( throttling.size() > 0 ) retryAfterHeader = throttling.get(0);
            respString = response.bodyToMono(String.class).block();

            // After we reach our instant retry limit we should fail to second TransmissionOutput
            if (code > HttpStatus.PARTIAL_CONTENT.value() && transmission.getNumberOfSends() > this.transmissionPolicyManager.getMaxInstantRetries()) {
                return false;
            } else if (code == HttpStatus.OK.value()) {
                // If we've completed then clear the back off flags as the channel does not need
                // to be throttled
                transmissionPolicyManager.clearBackoff();
            }

            if (code == HttpStatus.BAD_REQUEST.value()) {
                InternalLogger.INSTANCE.error("Error sending data: %s", reason);
            } else if (code != HttpStatus.OK.value()) {
                // Invoke the listeners for handling things like errors
                // The listeners will handle the back off logic as well as the dispatch
                // operation
                TransmissionHandlerArgs args = new TransmissionHandlerArgs();
                args.setTransmission(transmission);
                args.setTransmissionDispatcher(transmissionDispatcher);
                args.setResponseBody(respString);
                args.setResponseCode(code);
                args.setException(ex);
                args.setRetryHeader(retryAfterHeader);
                this.transmissionPolicyManager.onTransmissionSent(args);
            }
        }
        // If we end up here we've hit an error code we do not expect (403, 401, 400,
        // etc.)
        // This also means that unless there is a TransmissionHandler for this code we
        // will not retry.
        return true;
    }

    /**
     * Generates the HTTP POST to send to the endpoint.
     *
     * @param transmission The transmission to send.
     * @return The completed {@link HttpPost} object
     */
    private Mono<ClientResponse> createTransmissionPostRequest(Transmission transmission) {
    	WebClient client = WebClient.create(getIngestionEndpoint()); 
    	return client
			.post()
			.uri(URI.create(getIngestionEndpoint()))
			.syncBody( transmission.getContent() )
    		.header(CONTENT_TYPE_HEADER, transmission.getWebContentType())
    		.header(CONTENT_ENCODING_HEADER, transmission.getWebContentEncodingType())
    		.exchange();
    }

    private String getIngestionEndpoint() {
        if (serverUri != null) {
            return serverUri;
        } else if (configuration != null) {
            return configuration.getEndpointProvider().getIngestionEndpointURL().toString();
        } else {
            return DEFAULT_SERVER_URI;
        }
    }
}
