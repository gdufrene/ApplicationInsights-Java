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

package com.microsoft.applicationinsights.internal.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * Created by gupele on 3/15/2015.
 * updated 3/28/2020 to use Spring
 */
public final class AnnotationPackageScanner {
    private AnnotationPackageScanner(){}
    /**
     * The method will scan packages searching for classes that have the needed annotations.
     * @param annotationsToSearch The annotations we need.
     * @param packageToScan The packages to scan from, note that all sub packages will be scanned too.
     * @return A list of class names that are under the package we asked and that carry the needed annotations
     */
    public static List<String> scanForClassAnnotations(final Class<? extends Annotation>[] annotationsToSearch, String packageToScan) {
    	final ArrayList<String> performanceModuleNames = new ArrayList<String>();
    	
    	ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
    	for ( Class<? extends Annotation> annotationClass : annotationsToSearch ) {
    		scanner.addIncludeFilter(new AnnotationTypeFilter(annotationClass));
    	}
    	Set<BeanDefinition> definitions = scanner.findCandidateComponents(packageToScan);
    	for(BeanDefinition def : definitions) {
    	    performanceModuleNames.add( def.getBeanClassName() );
    	}
    	return performanceModuleNames;
    }
}
