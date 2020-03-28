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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by gupele on 3/13/2015.
 */
@XmlRootElement(name="ApplicationInsights")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationInsightsXmlConfiguration {

    @XmlElement(name="InstrumentationKey")
    private String instrumentationKey;

    @XmlElement(name="ConnectionString")
    private String connectionString;

    @XmlElement(name="RoleName")
    private String roleName;

    @XmlElement(name="DisableTelemetry")
    public boolean disableTelemetry;

    @XmlElement(name="TelemetryInitializers")
    private TelemetryInitializersXmlElement telemetryInitializers;

    @XmlElement(name="TelemetryProcessors")
    private TelemetryProcessorsXmlElement telemetryProcessors;

    @XmlElement(name="ContextInitializers")
    private ContextInitializersXmlElement contextInitializers;

    @XmlElement(name="Channel")
    private ChannelXmlElement channel = new ChannelXmlElement();

    @XmlElement(name="TelemetryModules")
    private TelemetryModulesXmlElement modules;

    @XmlElement(name="PerformanceCounters")
    private PerformanceCountersXmlElement performance = new PerformanceCountersXmlElement();

    @XmlElement(name="SDKLogger")
    private SDKLoggerXmlElement sdkLogger;

    @XmlElement(name="Sampling")
    private SamplerXmlElement sampler;

    @XmlElement(name="QuickPulse")
    private QuickPulseXmlElement quickPulse;

    @XmlAttribute
    private String schemaVersion;

    public String getInstrumentationKey() {
        return instrumentationKey;
    }

    public void setInstrumentationKey(String instrumentationKey) {
        this.instrumentationKey = instrumentationKey;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public TelemetryInitializersXmlElement getTelemetryInitializers() {
        return telemetryInitializers;
    }

    public void setTelemetryInitializers(TelemetryInitializersXmlElement telemetryInitializers) {
        this.telemetryInitializers = telemetryInitializers;
    }

    public ContextInitializersXmlElement getContextInitializers() {
        return contextInitializers;
    }

    public void setTelemetryProcessors(TelemetryProcessorsXmlElement telemetryProcessors) {
        this.telemetryProcessors = telemetryProcessors;
    }

    public TelemetryProcessorsXmlElement getTelemetryProcessors() {
        return telemetryProcessors;
    }

    public void setContextInitializers(ContextInitializersXmlElement contextInitializers) {
        this.contextInitializers = contextInitializers;
    }

    public ChannelXmlElement getChannel() {
        return channel;
    }

    public void setChannel(ChannelXmlElement channel) {
        this.channel = channel;
    }

    public SamplerXmlElement getSampler() {
        return sampler;
    }

    public void setSampler(SamplerXmlElement sampler) {
        this.sampler = sampler;
    }

    public QuickPulseXmlElement getQuickPulse() {
        if (quickPulse == null) {
            quickPulse = new QuickPulseXmlElement();
        }
        return quickPulse;
    }

    public void setQuickPulse(QuickPulseXmlElement quickPulse) {
        this.quickPulse = quickPulse;
    }

    public SDKLoggerXmlElement getSdkLogger() {
        return sdkLogger;
    }

    public void setSdkLogger(SDKLoggerXmlElement sdkLogger) {
        this.sdkLogger = sdkLogger;
    }

    public boolean isDisableTelemetry() {
        return disableTelemetry;
    }

    public void setDisableTelemetry(boolean disableTelemetry) {
        this.disableTelemetry = disableTelemetry;
    }

    public TelemetryModulesXmlElement getModules() {
        return modules;
    }

    public void setModules(TelemetryModulesXmlElement modules) {
        this.modules = modules;
    }

    public PerformanceCountersXmlElement getPerformance() {
        return performance;
    }

    public void setPerformance(PerformanceCountersXmlElement performance) {
        this.performance = performance;
    }
}
