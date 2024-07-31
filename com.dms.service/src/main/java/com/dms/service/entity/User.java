package com.dms.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
public class User implements Serializable {
	@Id
	@Column(name = "ID")
	private Long id;
	@Column(name = "USERNAME")
	private String username;
	@Column(name = "PASSWORD")
	private String password;
	@Email
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "ACTIVE")
	private Integer active;

	@Column(name = "ROLES")
	private String roles;

}