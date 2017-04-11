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
@Table(name = "User")
public class User {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String firstName;
	private String lastName;
	private String role;
	private Date dob;
	private int wwid;
	private int wiproEmpId;
	private Boolean isAdmin;
	private Boolean isManager;
	private Boolean isEnabled;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Project projectId;
	

}
