/**
 * 
 */
package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Amrit
 *
 */
@Entity
@Table(name = "TaskCategory")
public class TaskCategory {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private Boolean isEnabled;

	private int cost;
	
	public TaskCategory(){
		// ToDo
	}

	/**
	 * @param name
	 * @param isEnabled
	 * @param cost
	 */
	public TaskCategory(String name, Boolean isEnabled, int cost) {
		this.name = name;
		this.isEnabled = isEnabled;
		this.cost = cost;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * @return the categoryId
	 */
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaskCategory [categoryId=" + id + ", name=" + name + ", isEnabled=" + isEnabled + ", cost="
				+ cost + "]";
	}

}
