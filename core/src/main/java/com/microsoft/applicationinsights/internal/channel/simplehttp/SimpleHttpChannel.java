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

package com.microsoft.applicationinsights.internal.channel.simplehttp;

import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.web.reactive.function.client.WebClient;

import com.microsoft.applicationinsights.channel.TelemetryChannel;
import com.microsoft.applicationinsights.channel.TelemetrySampler;
import com.microsoft.applicationinsights.internal.logger.InternalLogger;
import com.microsoft.applicationinsights.telemetry.JsonTelemetryDataSerializer;
import com.microsoft.applicationinsights.telemetry.Telemetry;

/**
 * A simple HTTP channel, using no buffering, batching, or asynchrony.
 */
final class SimpleHttpChannel implements TelemetryChannel
{
    private final static String DEFAULT_SERVER_URI = "https://dc.services.visualstudio.com/v2/track";

    @Override
    public boolean isDeveloperMode()
    {
        return developerMode;
    }

    @Override
    public void setDeveloperMode(boolean value)
    {
        developerMode = value;
    }

    public SimpleHttpChannel(Map<String, String> namesAndValues) {
    }

    @Override
    public void send(Telemetry item)
    {
            // Establish the payload.
            StringWriter writer = new StringWriter();
            try {
	            item.serialize(new JsonTelemetryDataSerializer(writer));
	
	            // Send it.
	            String payload = writer.toString();
	            if (developerMode) {
	                InternalLogger.INSTANCE.trace("SimpleHttpChannel, payload: %s", payload);
	            }
	
	            int code = WebClient.create(DEFAULT_SERVER_URI)
	            	.post()
	            	.syncBody(payload)
	            	.header("Content-Type", "application/x-json-stream")
	            	.exchange()
	            	.block()
	            	.statusCode()
	            	.value();
	            
	            if (developerMode) {
	            	InternalLogger.INSTANCE.trace("SimpleHttpChannel, response: %s", code);
	            }
            } catch (Exception e) { e.printStackTrace(); }

    }

    @Override
    public void stop(long timeout, TimeUnit timeUnit) {
    }

    @Override
    public void flush() {
    }

    @Override
    public void setSampler(TelemetrySampler telemetrySampler) {
    }

    private boolean developerMode = false;
}
