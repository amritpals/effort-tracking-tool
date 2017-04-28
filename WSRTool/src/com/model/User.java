/**
 * 
 */
package com.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.Gson;


/**
 * @author Amrit
 *
 */
@Entity
@Table(name = "User")
public class User {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private String role;
	private Date dob;
	private int wwid;
	private int wiproEmpId;
	private Boolean isAdmin;
	private Boolean isManager;
	private Boolean isEnabled;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Project project;
	
	public User(){
		// ToDo
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param password
	 * @param role
	 * @param dob
	 * @param wwid
	 * @param wiproEmpId
	 * @param isAdmin
	 * @param isManager
	 * @param isEnabled
	 * @param project
	 */
	public User(String firstName, String lastName, String emailId, String password, String role, Date dob, int wwid,
			int wiproEmpId, Boolean isAdmin, Boolean isManager, Boolean isEnabled, Project project) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.role = role;
		this.dob = dob;
		this.wwid = wwid;
		this.wiproEmpId = wiproEmpId;
		this.isAdmin = isAdmin;
		this.isManager = isManager;
		this.isEnabled = isEnabled;
		this.project = project;
	}



	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the wwid
	 */
	public int getWwid() {
		return wwid;
	}

	/**
	 * @param wwid the wwid to set
	 */
	public void setWwid(int wwid) {
		this.wwid = wwid;
	}

	/**
	 * @return the wiproEmpId
	 */
	public int getWiproEmpId() {
		return wiproEmpId;
	}

	/**
	 * @param wiproEmpId the wiproEmpId to set
	 */
	public void setWiproEmpId(int wiproEmpId) {
		this.wiproEmpId = wiproEmpId;
	}

	/**
	 * @return the isAdmin
	 */
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the isManager
	 */
	public Boolean getIsManager() {
		return isManager;
	}

	/**
	 * @param isManager the isManager to set
	 */
	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}

	/**
	 * @return the isEnabled
	 */
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the projectId
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the userId
	 */
	public int getId() {
		return id;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
