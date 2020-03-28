package com.microsoft.applicationinsights.internal.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;

/**
 * This is the class for binding the xml array list of {@code <ExcludedTypes>}
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ParamExcludedTypeXmlElement {

    public List<String> getExcludedType() {
        return excludedType;
    }

    public void setExcludedType(List<String> excludedType) {
        this.excludedType = excludedType;
    }

    @XmlElement(name="ExcludedType")
    @XmlList
    private List<String> excludedType;
}
