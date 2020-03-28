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

package com.microsoft.applicationinsights.internal.config;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.StringUtils;

import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.internal.config.connection.EndpointProvider;

/**
 * Created by gupele on 3/15/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelXmlElement {

    @XmlElement(name="EndpointAddress")
    private String endpointAddress;

    @XmlElement(name="MaxTelemetryBufferCapacity")
    private String maxTelemetryBufferCapacity;

    @XmlElement(name="FlushIntervalInSeconds")
    private String flushIntervalInSeconds;

    @XmlElement(name="DeveloperMode")
    private boolean developerMode;

    @XmlElement(name="Throttling")
    private boolean throttling = true;

    @XmlElement(name="MaxTransmissionStorageFilesCapacityInMB")
    private String maxTransmissionStorageFilesCapacityInMB;

    @XmlElement(name="MaxInstantRetry")
    private String maxInstantRetry;

    @XmlAttribute
    private String type = "com.microsoft.applicationinsights.channel.concrete.inprocess.InProcessTelemetryChannel";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * @deprecated Use {@link TelemetryConfiguration#getEndpointProvider()} and {@link EndpointProvider#getIngestionEndpoint()}.
     */
    @Deprecated
    public String getEndpointAddress() {
        return endpointAddress;
    }

    public void setThrottling(boolean throttling) {
        this.throttling = throttling;
    }

    public boolean getThrottling() {
        return throttling;
    }

    /**
     * @deprecated Use {@link TelemetryConfiguration#setConnectionString(String)}.
     */
    @Deprecated
    public void setEndpointAddress(String endpointAddress) {
        this.endpointAddress = endpointAddress;
    }

    public boolean getDeveloperMode() {
        return developerMode;
    }

    public void setDeveloperMode(boolean developerMode) {
        this.developerMode = developerMode;
    }

    public String getMaxTelemetryBufferCapacity() {
        return maxTelemetryBufferCapacity;
    }

    public void setMaxTelemetryBufferCapacity(String maxTelemetryBufferCapacity) {
        this.maxTelemetryBufferCapacity = maxTelemetryBufferCapacity;
    }

    public String getFlushIntervalInSeconds() {
        return flushIntervalInSeconds;
    }

    public void setFlushIntervalInSeconds(String flushIntervalInSeconds) {
        this.flushIntervalInSeconds = flushIntervalInSeconds;
    }

    /**
     * @deprecated Use {@link #getMaxTransmissionStorageFilesCapacityInMB()}
     */
    @Deprecated
    public String isMaxTransmissionStorageFilesCapacityInMB() {
        return maxTransmissionStorageFilesCapacityInMB;
    }

    public String getMaxTransmissionStorageFilesCapacityInMB() {
        return maxTransmissionStorageFilesCapacityInMB;
    }

    public void setMaxTransmissionStorageFilesCapacityInMB(String maxTransmissionStorageFilesCapacityInMB) {
        this.maxTransmissionStorageFilesCapacityInMB = maxTransmissionStorageFilesCapacityInMB;
    }

    public String getMaxInstantRetry() {
        return maxInstantRetry;
    }

    public void setMaxInstantRetry(String maxInstantRetry) {
        this.maxInstantRetry = maxInstantRetry;
    }

    public Map<String, String> getData() {
        HashMap<String, String> data = new HashMap<String, String>();
        if (developerMode) {
            data.put("DeveloperMode", "true");
        }

        if (!StringUtils.isEmpty(endpointAddress)) {
            data.put("EndpointAddress", endpointAddress);
        }


        if (!StringUtils.isEmpty(maxTelemetryBufferCapacity)) {
            data.put("MaxTelemetryBufferCapacity", maxTelemetryBufferCapacity);
        }

        if (!StringUtils.isEmpty(flushIntervalInSeconds)) {
            data.put("FlushIntervalInSeconds", flushIntervalInSeconds);
        }

        if (!StringUtils.isEmpty(maxTransmissionStorageFilesCapacityInMB)) {
            data.put("MaxTransmissionStorageFilesCapacityInMB", maxTransmissionStorageFilesCapacityInMB);
        }

        if (!StringUtils.isEmpty(maxInstantRetry)) {
            data.put("MaxInstantRetry", maxInstantRetry);
        }

        data.put("Throttling", throttling ? "true" : "false");

        return data;
    }
}
