/**
 * 
 */
package com.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.google.gson.Gson;

/**
 * @author Amrit
 *
 */
@Entity
@Table(name = "Project")
public class Project {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String projectName;

	private String billingModel;

	private int budget;

	private Boolean isEnabled;

	private String intelManagerName;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Set<TaskCategory> category = new HashSet<>(0);
	
	public Project() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param projectName
	 * @param billingModel
	 * @param budget
	 * @param isEnabled
	 * @param intelManagerName
	 * @param category
	 */
	public Project(String projectName, String billingModel, int budget, Boolean isEnabled, String intelManagerName,
			Set<TaskCategory> category) {
		this.projectName = projectName;
		this.billingModel = billingModel;
		this.budget = budget;
		this.isEnabled = isEnabled;
		this.intelManagerName = intelManagerName;
		this.category = category;
	}

	/**
	 * @return the projectId
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the billingModel
	 */
	public String getBillingModel() {
		return billingModel;
	}

	/**
	 * @param billingModel
	 *            the billingModel to set
	 */
	public void setBillingModel(String billingModel) {
		this.billingModel = billingModel;
	}

	/**
	 * @return the budget
	 */
	public int getBudget() {
		return budget;
	}

	/**
	 * @param budget
	 *            the budget to set
	 */
	public void setBudget(int budget) {
		this.budget = budget;
	}

	/**
	 * @return the isEnabled
	 */
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the intelManagerName
	 */
	public String getIntelManagerName() {
		return intelManagerName;
	}

	/**
	 * @param intelManagerName
	 *            the intelManagerName to set
	 */
	public void setIntelManagerName(String intelManagerName) {
		this.intelManagerName = intelManagerName;
	}

	/**
	 * @return the category
	 */
	public Set<TaskCategory> getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Set<TaskCategory> category) {
		this.category = category;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
