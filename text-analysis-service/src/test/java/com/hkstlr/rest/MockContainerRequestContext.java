package com.hkstlr.rest;

import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

public class MockContainerRequestContext implements ContainerRequestContext {

	MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();

	@Override
	public Object getProperty(String name) {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getPropertyNames() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public void setProperty(String name, Object object) {
		// Auto-generated method stub

	}

	@Override
	public void removeProperty(String name) {
		// Auto-generated method stub

	}

	@Override
	public UriInfo getUriInfo() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public void setRequestUri(URI requestUri) {
		// Auto-generated method stub

	}

	@Override
	public void setRequestUri(URI baseUri, URI requestUri) {
		// Auto-generated method stub

	}

	@Override
	public Request getRequest() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public String getMethod() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public void setMethod(String method) {
		// Auto-generated method stub

	}

	@Override
	public MultivaluedMap<String, String> getHeaders() {
		// Auto-generated method stub
		return headers;
	}

	@Override
	public String getHeaderString(String name) {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLanguage() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public int getLength() {
		// Auto-generated method stub
		return 0;
	}

	@Override
	public MediaType getMediaType() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public List<MediaType> getAcceptableMediaTypes() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public List<Locale> getAcceptableLanguages() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Cookie> getCookies() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasEntity() {
		// Auto-generated method stub
		return false;
	}

	@Override
	public InputStream getEntityStream() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public void setEntityStream(InputStream input) {
		// Auto-generated method stub

	}

	@Override
	public SecurityContext getSecurityContext() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public void setSecurityContext(SecurityContext context) {
		// Auto-generated method stub

	}

	@Override
	public void abortWith(Response response) {
		// Auto-generated method stub

	}

}
