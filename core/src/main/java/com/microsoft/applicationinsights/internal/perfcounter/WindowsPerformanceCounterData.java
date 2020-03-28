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

package com.microsoft.applicationinsights.internal.perfcounter;

import org.apache.commons.lang3.StringUtils;

import com.microsoft.applicationinsights.common.Preconditions;
import com.microsoft.applicationinsights.internal.logger.InternalLogger;

/**
 * Created by gupele on 3/30/2015.
 */
@SuppressWarnings("deprecation")
public final class WindowsPerformanceCounterData {
    // TODO v3 make fields private
    /**
     * @deprecated use {@link #getDisplayName()}
     */
    @Deprecated
    public String displayName;

    /**
     * @deprecated use {@link #getCategoryName()}
     */
    @Deprecated
    public String categoryName;

    /**
     * @deprecated use {@link #getCounterName()}
     */
    @Deprecated
    public String counterName;

    /**
     * @deprecated use {@link #getInstanceName()}
     */
    @Deprecated
    public String instanceName;

    public String getDisplayName() {
        return displayName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCounterName() {
        return counterName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public WindowsPerformanceCounterData setDisplayName(String displayName) {
        Preconditions.checkArgument(!StringUtils.isEmpty(displayName), "displayName must be non-null and non empty value.");
        this.displayName = displayName;
        return this;
    }

    public WindowsPerformanceCounterData setCategoryName(String categoryName) {
        Preconditions.checkArgument(!StringUtils.isEmpty(categoryName), "categoryName must be non-null and non empty value.");
        this.categoryName = categoryName;
        return this;
    }

    public WindowsPerformanceCounterData setCounterName(String counterName) {
        Preconditions.checkArgument(!StringUtils.isEmpty(counterName), "counterName must be non-null and non empty value.");
        this.counterName = counterName;
        return this;
    }

    /**
     * Sets the instance name, the method will consult the JniPCConnector for the proper instance name.
     * @param instanceName The requested instance name.
     * @return 'this'.
     * @throws Throwable The method might throw an Error if the JniPCConnector is not able to properly connect to the native code.
     */
    public WindowsPerformanceCounterData setInstanceName(String instanceName) throws Throwable {
        String translatedInstanceName;
        try {
            translatedInstanceName = JniPCConnector.translateInstanceName(instanceName);
            this.instanceName = translatedInstanceName;
        } catch (Throwable e) {
            InternalLogger.INSTANCE.error("Failed to translate instance name '%s': '%s'", instanceName, e.toString());
            throw e;
        }
        return this;
    }
}
