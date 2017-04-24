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
import javax.ws.rs.FormParam;
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
import com.model.Project;
import com.model.TaskCategory;

/**
 * @author Amrit
 *
 */

@Path("/Project")
public class ProjectWS implements WebServices {
	
	GenericDAO<Project> dao = new GenericDAO<>(Project.class);
	ObjectMapper mapper = new ObjectMapper();

	@Override
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieve() {

		Set<Project> projectSet = new HashSet<>();
		projectSet = dao.get();

		if (projectSet.isEmpty()) {
			return Response.status(404).entity("No Records found!").build();
		} else {
			String jsonString = null;
			try {
				jsonString = mapper.writeValueAsString(projectSet);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Response.status(200).entity(jsonString).build();
		}
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@Context HttpServletRequest request) {

		Integer projectId = null;
		try {
			String content = readPostBody(request);
			Project project = mapper.readValue(content, Project.class);
			projectId = dao.create(project);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (projectId == null) {
			return Response.status(500).entity("User could not be created!!").build();
		} else {
			return Response.status(200).entity(projectId.toString()).build();
		}
	}

	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@Context HttpServletRequest request) {
		Project projectTmp = null;
		try {
			String content = readPostBody(request);
			Project project = mapper.readValue(content, Project.class);
			projectTmp = dao.update(project, project.getId());
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (projectTmp == null) {
			return Response.status(500).entity("User could not be updated!!").build();
		} else {
			return Response.status(200).entity(projectTmp.toString()).build();
		}
	}

	@Override
	@DELETE
	@Path("{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("userId") Integer projectId) {
		Integer val;
		String message;
		val = dao.delete(projectId);
		
		if(val == 200){
			message = "Project deleted!";
		} else if(val == 404){
			message = "Project couldn't be found!";
		} else {
			message = "Unknown error!!";
		}
		return Response.status(val).entity(message).build();
	}

	@Override
	@GET
	@Path("{project_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("project_id") Integer project_id) {
		Project project = new Project();
		project = dao.getById(project_id);
		if (project == null) {
			return Response.status(404).entity("No Records found!").build();
		} else {
			return Response.status(200).entity(project.toString()).build();
		}
	}
	
	@PUT
	@Path("{project_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateById(@PathParam("project_id") Integer project_id,
			@Context HttpServletRequest request){
		
		Project project = (Project) findProject(project_id);
		Project projectTmp = null;
		Set<TaskCategory> categorySet = new HashSet<>(0);
		if(project!=null){
			try {
				String content = readPostBody(request);
				TaskCategory category = (TaskCategory) mapper.readValue(content, TaskCategory.class);
				categorySet = project.getCategory();
				categorySet.add(category);
				project.setCategory(categorySet);
				projectTmp = dao.update(project, project_id);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (projectTmp == null) {
				return Response.status(500).entity("User could not be updated!!").build();
			} else {
				return Response.status(200).entity(project.toString()).build();
			}
		}
		return Response.status(404).entity("User could not be found!!").build();
	}

	@POST
	@Path("/search/{projectId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Object findProject(@FormParam("projectId") Integer projectId) {
		Project project = new Project();
		project = dao.getById(projectId);
		if (project == null) {
			return null;
		} else {
			return project;
		}
	}
	
	
	private String readPostBody(HttpServletRequest request) throws ServletException, IOException{
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
