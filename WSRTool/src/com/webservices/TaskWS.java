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
import com.model.Task;

/**
 * @author Amrit
 *
 */
@Path("/Task")
public class TaskWS implements WebServices {
	
	GenericDAO<Task> dao = new GenericDAO<>(Task.class);
	ObjectMapper mapper = new ObjectMapper();

	/* (non-Javadoc)
	 * @see com.webservices.WebServices#retrieve()
	 */
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieve() {
		Set<Task> taskSet = new HashSet<>();
		taskSet = dao.get();

		if (taskSet.isEmpty()) {
			return Response.status(404).entity("No Records found!").build();
		} else {
			String jsonString = null;
			try {
				jsonString = mapper.writeValueAsString(taskSet);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Response.status(200).entity(jsonString).build();
		}
	}

	/* (non-Javadoc)
	 * @see com.webservices.WebServices#create(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@Context HttpServletRequest request) {
		Integer taskId = null;
		try {
			String content = readPostBody(request);
			Task task = mapper.readValue(content, Task.class);
			taskId = dao.create(task);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (taskId == null) {
			return Response.status(500).entity("Task could not be created!!").build();
		} else {
			return Response.status(200).entity(taskId.toString()).build();
		}
	}

	/* (non-Javadoc)
	 * @see com.webservices.WebServices#update(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@Context HttpServletRequest request) {
		Task taskTmp = null;
		try {
			String content = readPostBody(request);
			Task task = mapper.readValue(content, Task.class);
			taskTmp = dao.update(task, task.getId());
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (taskTmp == null) {
			return Response.status(500).entity("User could not be updated!!").build();
		} else {
			return Response.status(200).entity(taskTmp.toString()).build();
		}
	}

	/* (non-Javadoc)
	 * @see com.webservices.WebServices#delete(java.lang.Integer)
	 */
	@Override
	@DELETE
	@Path("{taskId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("taskId") Integer taskId) {
		Integer val;
		String message;
		val = dao.delete(taskId);
		
		if(val == 200){
			message = "Task deleted!";
		} else if(val == 404){
			message = "Task couldn't be found!";
		} else {
			message = "Unknown error!!";
		}
		return Response.status(val).entity(message).build();
	}

	/* (non-Javadoc)
	 * @see com.webservices.WebServices#getById(java.lang.Integer)
	 */
	@Override
	@GET
	@Path("{taskId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("taskId") Integer taskId) {
		Task task = new Task();
		task = dao.getById(taskId);
		if (task == null) {
			return Response.status(404).entity("No Records found!").build();
		} else {
			return Response.status(200).entity(task.toString()).build();
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
