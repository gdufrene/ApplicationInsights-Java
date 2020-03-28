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

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;

/**
 * Created by gupele on 3/15/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PerformanceCountersXmlElement {

	@XmlElement(name="UseBuiltIn")
    private boolean useBuiltIn = true;

    @XmlAttribute
    private long collectionFrequencyInSec = 60;

    @XmlElement(name="Jvm")
    private PerformanceCounterJvmSectionXmlElement jvmSection;

    @XmlElement(name="Plugin")
    private String plugin;

    @XmlElement(name="Jmx")
    private JmxWrapperXmlElement jmxWrapper = new JmxWrapperXmlElement();

    @XmlElement(name="Windows")
    private WindowsPCWrapperXmlElement windowsPCWrapper = new WindowsPCWrapperXmlElement();

    public ArrayList<JmxXmlElement> getJmxXmlElements() {
        return jmxWrapper.jmxXmlElements;
    }

    public void setJmxXmlElements(ArrayList<JmxXmlElement> jmxXmlElements) {
        jmxWrapper.jmxXmlElements = jmxXmlElements;
    }

    public boolean isUseBuiltIn() {
        return useBuiltIn;
    }

    public void setUseBuiltIn(boolean useBuiltIn) {
        this.useBuiltIn = useBuiltIn;
    }

    public ArrayList<WindowsPerformanceCounterXmlElement> getWindowsPCs() {
        return windowsPCWrapper.windowsPCs;
    }

    public void setWindowsPCs(ArrayList<WindowsPerformanceCounterXmlElement> windowsPCs) {
        windowsPCWrapper.windowsPCs = windowsPCs;
    }

    public long getCollectionFrequencyInSec() {
        return collectionFrequencyInSec;
    }

    public void setCollectionFrequencyInSec(long collectionFrequencyInSec) {
        this.collectionFrequencyInSec = collectionFrequencyInSec;
    }

    public PerformanceCounterJvmSectionXmlElement getJvmSection() {
        return jvmSection;
    }

    public void setJvmSection(PerformanceCounterJvmSectionXmlElement jvmSection) {
        this.jvmSection = jvmSection;
    }

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

    public static class JmxWrapperXmlElement {

    	@XmlElement(name="Add")
        private ArrayList<JmxXmlElement> jmxXmlElements;
    }

    public static class WindowsPCWrapperXmlElement {

    	@XmlElement(name="Add")
        private ArrayList<WindowsPerformanceCounterXmlElement> windowsPCs;
    }
}
