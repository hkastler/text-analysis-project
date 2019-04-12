package com.hkstlr.rest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CORSFilterTest {

	final Logger LOG = Logger.getLogger(this.getClass().getName());
	CORSFilter cut;
	MockContainerRequestContext request;
	MockContainerResponseContext response;

	@BeforeEach
	void setUp() throws Exception {
		LOG.info("setUp()");
		cut = new CORSFilter();

		request = new MockContainerRequestContext();
		response = new MockContainerResponseContext();
	}

	@Test
	public void testFilter() throws IOException, ServletException {
		LOG.info("testFilter()");
		cut.filter(request, response);

		String allowOrigin = response.getHeaderString(CORSFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN);
		LOG.log(Level.INFO,CORSFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN + "={0}", allowOrigin);
		String allowMethods = response.getHeaderString(CORSFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS);
		LOG.log(Level.INFO,CORSFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS + "={0}", allowMethods);

		Assertions.assertTrue("[*]".equals(allowOrigin));
		Assertions.assertTrue("[GET]".equals(allowMethods));

		request = new MockContainerRequestContext();
		response = new MockContainerResponseContext();
		request.getHeaders().putSingle(CORSFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS, 
		CORSFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
		cut.filter(request, response);
		String allowHeaders = response.getHeaderString(CORSFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS);
		LOG.info("allowHeaders: ".concat(allowHeaders));
		LOG.info(Boolean.toString("[]".equals(allowHeaders)));
		if("[]".equals(allowHeaders)){
			Assertions.fail("allowHeaders is empty");
		}else{
			Assertions.assertEquals("["+CORSFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS+"]", allowHeaders);
			
		}

		
		request = new MockContainerRequestContext();
		response = new MockContainerResponseContext();
		request.getHeaders().putSingle(CORSFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS, "");
		cut.filter(request, response);
		allowHeaders = response.getHeaderString(CORSFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS);
		if("[]".equals(allowHeaders)){
			Assertions.assertTrue(true);
		}else{
			Assertions.fail("Filter should not place");
		}
		
	}

}
