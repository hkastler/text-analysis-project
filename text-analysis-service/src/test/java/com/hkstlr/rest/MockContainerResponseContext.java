package com.hkstlr.rest;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.StatusType;

public class MockContainerResponseContext implements ContainerResponseContext {

	MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
	
	@Override
	public int getStatus() {
		// Auto-generated method stub
		return 0;
	}

	@Override
	public void setStatus(int code) {
		// Auto-generated method stub
		
	}

	@Override
	public StatusType getStatusInfo() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusInfo(StatusType statusInfo) {
		// Auto-generated method stub
		
	}

	@Override
	public MultivaluedMap<String, Object> getHeaders() {
		
		return headers;
	}

	@Override
	public MultivaluedMap<String, String> getStringHeaders() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public String getHeaderString(String name) {
		
		return headers.getOrDefault(name, new ArrayList<Object>()).toString();
	}

	@Override
	public Set<String> getAllowedMethods() {
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
	public Map<String, NewCookie> getCookies() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public EntityTag getEntityTag() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Date getLastModified() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public URI getLocation() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Set<Link> getLinks() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasLink(String relation) {
		// Auto-generated method stub
		return false;
	}

	@Override
	public Link getLink(String relation) {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Builder getLinkBuilder(String relation) {
		// Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasEntity() {
		// Auto-generated method stub
		return false;
	}

	@Override
	public Object getEntity() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getEntityClass() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Type getEntityType() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public void setEntity(Object entity) {
		// Auto-generated method stub
		
	}

	@Override
	public void setEntity(Object entity, Annotation[] annotations, MediaType mediaType) {
		// Auto-generated method stub
		
	}

	@Override
	public Annotation[] getEntityAnnotations() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream getEntityStream() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public void setEntityStream(OutputStream outputStream) {
		// Auto-generated method stub
		
	}

}
