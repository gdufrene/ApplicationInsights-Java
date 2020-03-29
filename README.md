# Application Insights for Spring 5.1


## Introduction

This is *an alternate* Java SDK for [Azure Application Insights](https://azure.microsoft.com/en-us/services/application-insights/). Application Insights is a service that monitors the availability, performance and usage of your application. The SDK sends telemetry about the performance and usage of your app to the Application Insights service where your data can be visualized in the [Azure Portal](https://portal.azure.com). The SDK automatically collects telemetry about HTTP requests, dependencies, and exceptions. You can also use the SDK to send your own events and trace logs.

This SDK is a fork of https://github.com/microsoft/ApplicationInsights-Java.  
Some changes are made to try to minimize dependencies on a spring platform.

* remove dependency to google guava library.  
* replace xstream by jaxb for configuration reader
* use spring WebClient as Http Client
* update spring target version to 5.1.x and spring-boot 2.1.x
* Use maven rather than graddle

All Unit tests has been updated.

Changed parts are :

* ApplicationInsightsInternalLogger
* core
* collectd
* web
* web-auto
* azure-application-insights-spring-boot-starter

Other files are not changed yet. In a near future logging modules would be changed.

