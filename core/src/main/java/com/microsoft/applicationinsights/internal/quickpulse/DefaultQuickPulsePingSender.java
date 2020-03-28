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

package com.microsoft.applicationinsights.internal.quickpulse;

import java.util.Date;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.internal.channel.common.HttpSender;
import com.microsoft.applicationinsights.internal.logger.InternalLogger;

/**
 * Created by gupele on 12/12/2016.
 */
final class DefaultQuickPulsePingSender implements QuickPulsePingSender {
    private static final String QP_BASE_URI = "https://rt.services.visualstudio.com/QuickPulseService.svc";

    private final TelemetryConfiguration configuration;
    private final HttpSender apacheSender;
    private final QuickPulseNetworkHelper networkHelper = new QuickPulseNetworkHelper();
    private String pingPrefix;
    private long lastValidTransmission = 0;

    public DefaultQuickPulsePingSender(HttpSender sender, TelemetryConfiguration configuration, String instanceName, String quickPulseId) {
        this.configuration = configuration;
        this.apacheSender = sender;

        pingPrefix = "{" +
                "\"Documents\": null," +
                "\"Instance\":\"" + instanceName + "\"," +
                "\"InstrumentationKey\": null," +
                "\"InvariantVersion\": 2," +
                "\"MachineName\":\"" + instanceName + "\"," +
                "\"Metrics\": null," +
                "\"StreamId\": \"" + quickPulseId + "\"," +
                "\"Timestamp\": \"\\/Date(";
        if (InternalLogger.INSTANCE.isTraceEnabled()) {
            InternalLogger.INSTANCE.trace("%s using endpoint %s", DefaultQuickPulsePingSender.class.getSimpleName(), getQuickPulseEndpoint());
        }
    }

    /**
     * @deprecated Use {@link #DefaultQuickPulsePingSender(HttpSender, TelemetryConfiguration, String, String)}
     */
    @Deprecated
    public DefaultQuickPulsePingSender(final HttpSender apacheSender, final String instanceName, final String quickPulseId) {
        this(apacheSender, null, instanceName, quickPulseId);
    }

    @Override
    public QuickPulseStatus ping() {
        final Date currentDate = new Date();
        
        ClientRequest request = networkHelper.buildRequest(currentDate, getQuickPulsePingUri())
        		.body(BodyInserters.fromObject(buildPingEntity(currentDate.getTime())))
        		.build();
        
        ClientResponse response = ExchangeFunctions.create(new ReactorClientHttpConnector())
        	.exchange(request)
        	.block();

        final long sendTime = System.nanoTime();

        if (networkHelper.isSuccess(response)) {
            final QuickPulseStatus quickPulseResultStatus = networkHelper.getQuickPulseStatus(response);
            switch (quickPulseResultStatus) {
                case QP_IS_OFF:
                case QP_IS_ON:
                    lastValidTransmission = sendTime;
                    return quickPulseResultStatus;

                default:
                    break;
            }
        }

        return onPingError(sendTime);
    }

    // @VisibleForTesting
    String getQuickPulsePingUri() {
        return getQuickPulseEndpoint() + "/ping?ikey=" + getInstrumentationKey();
    }

    private String getInstrumentationKey() {
        TelemetryConfiguration config = this.configuration == null ? TelemetryConfiguration.getActive() : configuration;
        return config.getInstrumentationKey();
    }

    private String getQuickPulseEndpoint() {
        if (configuration != null) {
            return configuration.getEndpointProvider().getLiveEndpointURL().toString();
        } else {
            return QP_BASE_URI;
        }
    }

    private String buildPingEntity(long timeInMillis) {
        return pingPrefix + timeInMillis +
                ")\\/\"," +
                "\"Version\":\"2.2.0-738\"" +
                "}";
    }

    private QuickPulseStatus onPingError(long sendTime) {
        final double timeFromLastValidTransmission = (sendTime - lastValidTransmission) / 1000000000.0;
        if (timeFromLastValidTransmission >= 60.0) {
            return QuickPulseStatus.ERROR;
        }

        return QuickPulseStatus.QP_IS_OFF;
    }
}
