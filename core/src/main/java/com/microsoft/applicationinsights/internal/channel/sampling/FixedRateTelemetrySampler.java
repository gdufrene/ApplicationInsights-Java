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

package com.microsoft.applicationinsights.internal.channel.sampling;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.microsoft.applicationinsights.channel.TelemetrySampler;
import com.microsoft.applicationinsights.internal.logger.InternalLogger;
import com.microsoft.applicationinsights.internal.util.LocalStringsUtils;
import com.microsoft.applicationinsights.telemetry.EventTelemetry;
import com.microsoft.applicationinsights.telemetry.ExceptionTelemetry;
import com.microsoft.applicationinsights.telemetry.PageViewTelemetry;
import com.microsoft.applicationinsights.telemetry.RemoteDependencyTelemetry;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.microsoft.applicationinsights.telemetry.SupportSampling;
import com.microsoft.applicationinsights.telemetry.Telemetry;
import com.microsoft.applicationinsights.telemetry.TraceTelemetry;

/**
 * Created by gupele on 11/2/2016.
 *
 * Represents a telemetry sampler for sampling telemetry at a fixed-rate before sending to Application Insights.
 */
public final class FixedRateTelemetrySampler implements TelemetrySampler {
    private AtomicReference<Double> samplingPercentage = new AtomicReference<Double>(100.0);
    private HashSet<Class> excludeTypes = new HashSet<Class>();
    private HashSet<Class> includeTypes = new HashSet<Class>();
    private final HashMap<String, Class> allowedTypes = new HashMap<String, Class>();

    public FixedRateTelemetrySampler() {
        allowedTypes.put("Dependency", RemoteDependencyTelemetry.class);
        allowedTypes.put("Event", EventTelemetry.class);
        allowedTypes.put("Exception", ExceptionTelemetry.class);
        allowedTypes.put("PageView", PageViewTelemetry.class);
        allowedTypes.put("Request", RequestTelemetry.class);
        allowedTypes.put("Trace", TraceTelemetry.class);
    }

    @Override
    public Set<Class> getExcludeTypes() {
        return Collections.unmodifiableSet(excludeTypes);
    }

    @Override
    public void setExcludeTypes(String types) {
        excludeTypes = parseToSet(types, "ExcludeTypes");
    }

    @Override
    public Set<Class> getIncludeTypes() {
        return Collections.unmodifiableSet(includeTypes);
    }

    @Override
    public void setIncludeTypes(String types) {
        includeTypes = parseToSet(types, "IncludeTypes");
    }

    @Override
    public Double getSamplingPercentage() {
        return samplingPercentage.get();
    }

    @Override
    public void setSamplingPercentage(Double samplingPercentage) {
        if (samplingPercentage != null) {
            this.samplingPercentage.set(samplingPercentage);
        }
        InternalLogger.INSTANCE.info( "Setting sampling percentage to %s percent", this.samplingPercentage);
    }

    @Override
    public boolean isSampledIn(Telemetry telemetry) {
        double currentSamplingPercentage = samplingPercentage.get();

        if (currentSamplingPercentage < 100.0 - 1.0E-12) {
            if (telemetry instanceof SupportSampling) {
                SupportSampling samplingSupportingTelemetry = (SupportSampling)telemetry;

                if (!excludeTypes.isEmpty() && excludeTypes.contains(telemetry.getClass())) {
                    InternalLogger.INSTANCE.trace("Skip sampling since %s is excluded", telemetry.getClass());
                }
                else if (!includeTypes.isEmpty() && !includeTypes.contains(telemetry.getClass())) {
                    InternalLogger.INSTANCE.trace("Skip sampling since %s is not included", telemetry.getClass());
                }
                else {
                    Double testedPercentage = currentSamplingPercentage;
                    if (samplingSupportingTelemetry.getSamplingPercentage() != null) {
                        testedPercentage = samplingSupportingTelemetry.getSamplingPercentage();
                    }

                    double telemetrySamplingScore = SamplingScoreGenerator.getSamplingScore(telemetry);
                    if (telemetrySamplingScore >= testedPercentage) {
                        InternalLogger.INSTANCE.trace("Sampled out %s", telemetry.getClass());

                        return false;
                    }
                    samplingSupportingTelemetry.setSamplingPercentage(testedPercentage);
                }
            }
        }

        return true;
    }

    private HashSet<Class> parseToSet(String value, String prefix) {
        HashSet<Class> set = new HashSet<Class>();

        if (!LocalStringsUtils.isNullOrEmpty(value)) {
            List<String> types = Arrays.asList(value.split(","));

            for (String type : types) {
                type = type.trim();
                if (LocalStringsUtils.isNullOrEmpty(type)) {
                    continue;
                }
                if (!allowedTypes.containsKey(type)) {
                    InternalLogger.INSTANCE.error("%s contains illegal type %s, ignored", prefix, type);
                    continue;
                }

                set.add(allowedTypes.get(type));
            }
        }

        return set;
    }
}
