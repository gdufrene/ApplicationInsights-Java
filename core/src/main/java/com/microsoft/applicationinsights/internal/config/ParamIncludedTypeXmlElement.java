package com.microsoft.applicationinsights.internal.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;

/**
 * This class is used to bind the xml array list of {@code <IncludeTypes>}
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ParamIncludedTypeXmlElement {

    public List<String> getIncludedType() {
        return includedType;
    }

    public void setIncludedType(List<String> includedType) {
        this.includedType = includedType;
    }

    @XmlElement(name="IncludedType")
    @XmlList
    private List<String> includedType;
}
