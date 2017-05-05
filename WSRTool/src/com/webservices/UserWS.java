/**
 * 
 */
package com.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
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
import com.model.User;

/**
 * @author Amrit
 *
 */

@Path("/User")
public class UserWS implements WebServices {

	GenericDAO<User> dao = new GenericDAO<>(User.class);
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

		Set<User> userSet = new HashSet<>();
		userSet = dao.get();

		if (userSet.isEmpty()) {
			return Response.status(404).entity("No Records found!").build();
		} else {
			String jsonString = null;
			try {
				jsonString = mapper.writeValueAsString(userSet);
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
	 * @see com.webservices.WebServices#create()
	 */
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@Context HttpServletRequest request) {

		Integer userId = null;
		try {
			String content = readPostBody(request);
			System.out.println("Content: " + content);
			User user = mapper.readValue(content, User.class);
			userId = dao.create(user);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userId == null) {
			return Response.status(500).entity("User could not be created!!").build();
		} else {
			return Response.status(200).entity(userId.toString()).build();
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
		User userTmp = null;
		try {
			String content = readPostBody(request);
			User user = mapper.readValue(content, User.class);
			userTmp = dao.getById(user.getId());
			user.setProject(userTmp.getProject());
			userTmp = dao.update(user, user.getId());
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userTmp == null) {
			return Response.status(500).entity("User could not be updated!!").build();
		} else {
			return Response.status(200).entity(userTmp.toString()).build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.webservices.WebServices#delete(java.lang.Integer)
	 */
	@Override
	@DELETE
	@Path("{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("userId") Integer userId) {
		Integer val;
		String message;
		val = dao.delete(userId);

		if (val == 200) {
			message = "User deleted!";
		} else if (val == 404) {
			message = "User couldn't be found!";
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
	@Path("{user_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("user_id") Integer user_id) {

		User user = new User();
		user = dao.getById(user_id);
		if (user == null) {
			return Response.status(404).entity("No Records found!").build();
		} else {
			return Response.status(200).entity(user.toString()).build();
		}
	}

	@PUT
	@Path("{userId}/Project/{projectId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProject(@PathParam("userId") Integer userId, @PathParam("projectId") Integer projectId) {

		User user = (User) findUser(userId);
		User userTmp = null;
		Set<Project> projectSet = new HashSet<>(0);
		if (user != null) {
			GenericDAO<Project> projectDAO = new GenericDAO<>(Project.class);
			Project project = projectDAO.getById(projectId);
			if (project != null) {
				projectSet = user.getProject();
				projectSet.add(project);
				user.setProject(projectSet);
				userTmp = dao.update(user, userId);
			} else {
				return Response.status(404).entity("Project could not be found!!").build();
			}
		} else {
			return Response.status(404).entity("User could not be found!!").build();
		}
		if (userTmp == null) {
			return Response.status(500).entity("User could not be updated!!").build();
		} else {
			return Response.status(200).entity(userTmp.toString()).build();
		}
	}
	
	@DELETE
	@Path("{userId}/Project/{projectId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deallocateProject(@PathParam("userId") Integer userId, @PathParam("projectId") Integer projectId){
		
		User user = (User) findUser(userId);
		User userTmp = null;
		Set<Project> projectSet = new HashSet<>(0);
		if (user != null) {
			projectSet = user.getProject();
			Set<Project> tmpSet = projectSet;
			Iterator<Project> iter = tmpSet.iterator();
			for(;iter.hasNext();){
				Project project = iter.next();
				if(project.getId() == projectId){
					projectSet.remove(project);
				}
			}
			user.setProject(projectSet);
			userTmp = dao.update(user, userId);
		} else {
			return Response.status(404).entity("User could not be found!!").build();
		}
		if (userTmp == null) {
			return Response.status(500).entity("User could not be updated!!").build();
		} else {
			return Response.status(200).entity(userTmp.toString()).build();
		}
	}

	/*
	 * @PUT
	 * 
	 * @Path("{userId}")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * updateById(@PathParam("userId") Integer userId, @Context UriInfo ui) {
	 * 
	 * MultivaluedMap<String, String> payload = ui.getQueryParameters(); String
	 * st = ui.getQueryParameters().toString(); //String st =
	 * prepareParameters(payload); final ObjectMapper mapper = new
	 * ObjectMapper(); try { User user = mapper.readValue(st, User.class); }
	 * catch (JsonParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (JsonMappingException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * return null; }
	 * 
	 * private Map<String, String> prepareParameters(MultivaluedMap<String,
	 * String> queryParameters) { Map<String, String> parameters = new
	 * HashMap<String, String>(); Iterator<String> it =
	 * queryParameters.keySet().iterator(); while (it.hasNext()) { String theKey
	 * = (String) it.next(); parameters.put(theKey,
	 * queryParameters.getFirst(theKey)); } return parameters; }
	 * 
	 * public static String paramJson(String paramIn) { paramIn =
	 * paramIn.replaceAll("=", "\":\""); paramIn = paramIn.replaceAll("&",
	 * "\",\""); return "{\"" + paramIn + "\"}"; }
	 */

	@PUT
	@Path("{user_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateById(@PathParam("user_id") Integer user_id, @Context HttpServletRequest request) {
		User user = (User) findUser(user_id);
		User userTmp = null;
		Set<Project> projectSet = new HashSet<>(0);
		if (user != null) {
			try {
				String content = readPostBody(request);
				Project project = (Project) mapper.readValue(content, Project.class);
				projectSet = user.getProject();
				projectSet.add(project);
				user.setProject(projectSet);
				userTmp = dao.update(user, user_id);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (userTmp == null) {
				return Response.status(500).entity("User could not be updated!!").build();
			} else {
				return Response.status(200).entity(user.toString()).build();
			}
		}
		return Response.status(404).entity("User could not be found!!").build();
	}

	@POST
	@Path("/search/{userId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Object findUser(@FormParam("userId") Integer userId) {
		User user = new User();
		user = dao.getById(userId);
		if (user == null) {
			return null;
		} else {
			return user;
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
