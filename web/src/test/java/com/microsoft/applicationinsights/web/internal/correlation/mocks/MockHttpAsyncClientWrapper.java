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

package com.microsoft.applicationinsights.web.internal.correlation.mocks;

import java.util.concurrent.Future;

import okhttp3.mockwebserver.MockWebServer;

public class MockHttpAsyncClientWrapper {

    // private final CloseableHttpAsyncClient mockClient;
    private MockHttpTask task;
    private String appId;
    private boolean fail;
    private boolean complete;
    private int code = 200;

    public MockHttpAsyncClientWrapper() {
    	this.task = new MockHttpTask();
    }

    public void setAppId(String appId) {
    	task.setResponse(appId);
    }

    public void setFailureOn(boolean fail) {
    	task.setFailureOn(fail);
    }

    public void setTaskAsComplete() {
    	task.setIsDone(true);
    }

    public void setTaskAsPending() {
        task.setIsDone(false);
    }

    public void setStatusCode(int code) {
        task.setCode( code ); 
    }

    public Future<String> mockFuture(String endpoint) {
    	return task;
    }
    
    public void configureMockServer(MockWebServer server) {
    }
}