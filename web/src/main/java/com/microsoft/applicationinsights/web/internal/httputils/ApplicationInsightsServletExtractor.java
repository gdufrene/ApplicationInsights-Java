package com.microsoft.applicationinsights.web.internal.httputils;

import org.apache.commons.lang3.Validate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Adopter to extract information from {@link HttpServletRequest} and {@link HttpServletResponse}
 */
// @Experimental
public class ApplicationInsightsServletExtractor implements HttpExtractor<HttpServletRequest, HttpServletResponse> {

    private static final String USER_AGENT_HEADER = "User-Agent";

    @Override
    public String getUrl(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    @Override
    public String getMethod(HttpServletRequest request) {
        return request.getMethod();
    }

    @Override
    public String getHost(HttpServletRequest request) {
        return request.getServerName() + ":" + request.getServerPort();
    }

    @Override
    public String getQuery(HttpServletRequest request) {
        return request.getQueryString();
    }

    @Override
    public String getPath(HttpServletRequest request) {
        return request.getPathInfo();
    }

    @Override
    public String getUserAgent(HttpServletRequest request) {
        return request.getHeader(USER_AGENT_HEADER);
    }

    @Override
    public int getStatusCode(HttpServletResponse response) {
        if (response != null) {
            return response.getStatus();
        }
        return 0;
    }

    @Override
    public String getUri(HttpServletRequest request) {
        return removeSessionIdFromUri(request.getRequestURI());
    }

    @Override
    public String getScheme(HttpServletRequest request) {
        return request.getScheme();
    }

    /**
     * Returns uri without session-id
     * @param uri String uri
     * @return stripped uri
     */
    private String removeSessionIdFromUri(String uri) {
        Validate.notNull(uri);
        int separatorIndex = uri.indexOf(';');
        if (separatorIndex != -1) {
            return uri.substring(0, separatorIndex);
        }
        return uri;
    }
}
