package com.webservices;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

public interface WebServices {

	public Response retrieve();

	public Response create(HttpServletRequest request);

	public Response update(HttpServletRequest request);

	public Response delete(Integer obj_id);

	public Response getById(Integer obj_id);
}
