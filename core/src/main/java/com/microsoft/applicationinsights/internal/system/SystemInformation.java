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

package com.microsoft.applicationinsights.internal.system;

import java.lang.management.ManagementFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import com.microsoft.applicationinsights.internal.logger.InternalLogger;


/**
 * Created by gupele on 3/3/2015.
 */
public enum SystemInformation {
    INSTANCE;

    private final static String DEFAULT_PROCESS_NAME = "Java_Process";

    private final String processId = initializeProcessId();

    public String getProcessId() {
        return processId;
    }

    public boolean isWindows() {
        return SystemUtils.IS_OS_WINDOWS;
    }

    public boolean isUnix() {
        return SystemUtils.IS_OS_UNIX;
    }

    private String initializeProcessId() {
        String rawName = ManagementFactory.getRuntimeMXBean().getName();
        if (!StringUtils.isEmpty(rawName)) {
            int i = rawName.indexOf("@");
            if (i != -1) {
                String processIdAsString = rawName.substring(0, i);
                try {
                    Integer.parseInt(processIdAsString);
                    return processIdAsString;
                } catch (Exception e) {
                    InternalLogger.INSTANCE.error("Failed to fetch process id: '%s'", e.toString());
                }
            }
        }

        // Default
        return DEFAULT_PROCESS_NAME;
    }
}
