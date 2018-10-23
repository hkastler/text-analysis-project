package com.hkstlr.rest;

import java.io.IOException;
import java.util.Optional;
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
		LOG.info(CORSFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN + "=" + allowOrigin);
		String allowMethods = response.getHeaderString(CORSFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS);
		LOG.info(CORSFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS + "=" + allowMethods);

		Assertions.assertTrue("[*]".equals(allowOrigin));
		Assertions.assertTrue("[GET]".equals(allowMethods));

		request = new MockContainerRequestContext();
		response = new MockContainerResponseContext();
		request.getHeaders().putSingle(CORSFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS, 
		CORSFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
		cut.filter(request, response);
		Optional<String> allowHeaders = Optional.ofNullable(
			response.getHeaderString(CORSFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS)
			) ;
		if(allowHeaders.isPresent()){
			Assertions.assertEquals("["+CORSFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS+"]", allowHeaders.get());
		}else{
			Assertions.fail("allowHeaders is null");
		}
		
	}

}
