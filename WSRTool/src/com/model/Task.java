/**
 * 
 */
package com.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Amrit
 *
 */
@Entity
@Table(name="Task")
public class Task {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String description;
	private int duration;
	private Date dateAssigned;
	private String assignedBy;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private User userId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private TaskCategory categoryId;
	
	public Task(){
		// ToDo
	}

	/**
	 * @param description
	 * @param duration
	 * @param dateAssigned
	 * @param assignedBy
	 * @param userId
	 * @param categoryId
	 */
	public Task(String description, int duration, Date dateAssigned, String assignedBy, User userId,
			TaskCategory categoryId) {
		super();
		this.description = description;
		this.duration = duration;
		this.dateAssigned = dateAssigned;
		this.assignedBy = assignedBy;
		this.userId = userId;
		this.categoryId = categoryId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the dateAssigned
	 */
	public Date getDateAssigned() {
		return dateAssigned;
	}

	/**
	 * @param dateAssigned the dateAssigned to set
	 */
	public void setDateAssigned(Date dateAssigned) {
		this.dateAssigned = dateAssigned;
	}

	/**
	 * @return the assignedBy
	 */
	public String getAssignedBy() {
		return assignedBy;
	}

	/**
	 * @param assignedBy the assignedBy to set
	 */
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	/**
	 * @return the userId
	 */
	public User getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(User userId) {
		this.userId = userId;
	}

	/**
	 * @return the categoryId
	 */
	public TaskCategory getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(TaskCategory categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the taskId
	 */
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Task [taskId=" + id + ", description=" + description + ", duration=" + duration + ", dateAssigned="
				+ dateAssigned + ", assignedBy=" + assignedBy + ", userId=" + userId + ", categoryId=" + categoryId
				+ "]";
	}

}
