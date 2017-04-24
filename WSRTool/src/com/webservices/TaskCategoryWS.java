/**
 * 
 */
package com.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.application.GenericDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.TaskCategory;

/**
 * @author Amrit
 *
 */
@Path("/Category")
public class TaskCategoryWS implements WebServices {

	GenericDAO<TaskCategory> dao = new GenericDAO<>(TaskCategory.class);
	ObjectMapper mapper = new ObjectMapper();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.webservices.WebServices#retrieve()
	 */
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieve() {
		Set<TaskCategory> categorySet = new HashSet<>();
		categorySet = dao.get();

		if (categorySet.isEmpty()) {
			return Response.status(404).entity("No Records found!").build();
		} else {
			String jsonString = null;
			try {
				jsonString = mapper.writeValueAsString(categorySet);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Response.status(200).entity(jsonString).build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.webservices.WebServices#create(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@Context HttpServletRequest request) {

		Integer categoryId = null;
		try {
			String content = readPostBody(request);
			TaskCategory category = mapper.readValue(content, TaskCategory.class);
			categoryId = dao.create(category);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (categoryId == null) {
			return Response.status(500).entity("Category could not be created!!").build();
		} else {
			return Response.status(200).entity(categoryId.toString()).build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.webservices.WebServices#update(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@Context HttpServletRequest request) {
		TaskCategory categoryTmp = null;
		try {
			String content = readPostBody(request);
			TaskCategory category = mapper.readValue(content, TaskCategory.class);
			categoryTmp = dao.update(category, category.getId());
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (categoryTmp == null) {
			return Response.status(500).entity("Category could not be updated!!").build();
		} else {
			return Response.status(200).entity(categoryTmp.toString()).build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.webservices.WebServices#delete(java.lang.Integer)
	 */
	@Override
	@DELETE
	@Path("{categoryId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("categoryId") Integer categoryId) {
		Integer val;
		String message;
		val = dao.delete(categoryId);
		
		if(val == 200){
			message = "Category deleted!";
		} else if(val == 404){
			message = "Category couldn't be found!";
		} else {
			message = "Unknown error!!";
		}
		return Response.status(val).entity(message).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.webservices.WebServices#getById(java.lang.Integer)
	 */
	@Override
	@GET
	@Path("{categoryId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("categoryId") Integer categoryId) {

		TaskCategory category = new TaskCategory();
		category = dao.getById(categoryId);
		if (category == null) {
			return Response.status(404).entity("No Records found!").build();
		} else {
			return Response.status(200).entity(category.toString()).build();
		}
	}
	
	private String readPostBody(HttpServletRequest request) throws ServletException, IOException {
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		body = stringBuilder.toString();
		return body;
	}

}
