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
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by gupele on 11/14/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AdaptiveSamplerXmlElement {

    @XmlElement(name="IncludeTypes")
    private String includeTypes;

    @XmlElement(name="ExcludeTypes")
    private String excludeTypes;

    @XmlElement(name="MaxTelemetryItemsPerSecond")
    private String maxTelemetryItemsPerSecond;

    @XmlElement(name="EvaluationIntervalInSec")
    private String evaluationInterval;

    @XmlElement(name="SamplingPercentageDecreaseTimeoutInSec")
    private String samplingPercentageDecreaseTimeout;

    @XmlElement(name="SamplingPercentageIncreaseTimeoutInSec")
    private String samplingPercentageIncreaseTimeout;

    @XmlElement(name="MinSamplingPercentage")
    private String minSamplingPercentage;

    @XmlElement(name="MaxSamplingPercentage")
    private String maxSamplingPercentage;

    @XmlElement(name="InitialSamplingPercentage")
    private String initialSamplingPercentage;

    @XmlElement(name="MovingAverageRatio")
    private String movingAverageRatio;

    public void setMaxTelemetryItemsPerSecond(String maxTelemetryItemsPerSecond) {
        this.maxTelemetryItemsPerSecond = maxTelemetryItemsPerSecond;
    }

    public String getMaxTelemetryItemsPerSecond() {
        return maxTelemetryItemsPerSecond;
    }

    public void setEvaluationInterval(String evaluationInterval) {
        this.evaluationInterval = evaluationInterval;
    }

    public String getEvaluationInterval() {
        return evaluationInterval;
    }

    public void setSamplingPercentageDecreaseTimeout(String samplingPercentageDecreaseTimeout) {
        this.samplingPercentageDecreaseTimeout = samplingPercentageDecreaseTimeout;
    }

    public String getSamplingPercentageDecreaseTimeout() {
        return samplingPercentageDecreaseTimeout;
    }

    public void setSamplingPercentageIncreaseTimeout(String samplingPercentageIncreaseTimeout) {
        this.samplingPercentageIncreaseTimeout = samplingPercentageIncreaseTimeout;
    }

    public String getSamplingPercentageIncreaseTimeout() {
        return samplingPercentageIncreaseTimeout;
    }

    public void setMinSamplingPercentage(String minSamplingPercentage) {
        this.minSamplingPercentage = minSamplingPercentage;
    }

    public String getMinSamplingPercentage() {
        return minSamplingPercentage;
    }

    public void setInitialSamplingPercentage(String initialSamplingPercentage) {
        this.initialSamplingPercentage = initialSamplingPercentage;
    }

    public String getInitialSamplingPercentage() {
        return initialSamplingPercentage;
    }

    public void setMaxSamplingPercentage(String maxSamplingPercentage) {
        this.maxSamplingPercentage = maxSamplingPercentage;
    }

    public String getMaxSamplingPercentage() {
        return maxSamplingPercentage;
    }

    public void setMovingAverageRatio(String movingAverageRatio) {
        this.movingAverageRatio = movingAverageRatio;
    }

    public String getMovingAverageRatio() {
        return movingAverageRatio;
    }

    public String getIncludeTypes() {
        return includeTypes;
    }

    public void setIncludeTypes(String includeTypes) {
        this.includeTypes = includeTypes;
    }

    public String getExcludeTypes() {
        return excludeTypes;
    }

    public void setExcludeTypes(String excludeTypes) {
        this.excludeTypes = excludeTypes;
    }
}
