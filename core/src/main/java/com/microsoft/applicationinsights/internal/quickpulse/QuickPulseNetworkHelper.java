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

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;

/**
 * Created by gupele on 12/12/2016.
 * 
 * Now use Spring WebClient. gdufrene 3/28/2020
 */
final class QuickPulseNetworkHelper {
    private final static long TICKS_AT_EPOCH = 621355968000000000L;
    private static final String HEADER_TRANSMISSION_TIME = "x-ms-qps-transmission-time";
    private final static String QP_STATUS_HEADER = "x-ms-qps-subscribed";

    public ClientRequest.Builder buildRequest(Date currentDate, String address) {
        final long ticks = currentDate.getTime() * 10000 + TICKS_AT_EPOCH;

        return ClientRequest.create(HttpMethod.POST, URI.create(address))
        	.header(HEADER_TRANSMISSION_TIME, String.valueOf(ticks));
    }

    public boolean isSuccess(ClientResponse response) {
        final int responseCode = response.statusCode().value();
        return responseCode == 200;
    }

    public QuickPulseStatus getQuickPulseStatus(ClientResponse response) {
        List<String> headerStatus = response.headers().header(QP_STATUS_HEADER);
        if (headerStatus.size() > 0) {
            final String toPost = headerStatus.get(0);
            if ("true".equalsIgnoreCase(toPost)) {
                return QuickPulseStatus.QP_IS_ON;
            } else {
                return QuickPulseStatus.QP_IS_OFF;
            }
        }

        return QuickPulseStatus.ERROR;
    }
}
