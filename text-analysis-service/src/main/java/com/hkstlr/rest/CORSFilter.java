package com.hkstlr.rest;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {
	
	public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN =
			"Access-Control-Allow-Origin";
	
	public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS =
			"Access-Control-Allow-Methods";
	
	public static final String REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS =
			"Access-Control-Request-Headers";
	
	public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS =
			"Access-Control-Allow-Headers";
	
    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().putSingle(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        responseContext.getHeaders().putSingle(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");
        String reqHeader = requestContext.getHeaderString(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
        if (reqHeader != null && reqHeader != "") {
            responseContext.getHeaders().putSingle(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS, reqHeader);
        }
    }
}