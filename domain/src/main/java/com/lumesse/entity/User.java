package com.lumesse.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lumesse.entity.enums.UserRole;

@Entity
public class User extends BaseEntity {

	private static final long serialVersionUID = 398585519823800766L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30)
	@Size(min = 3, max = 30)
	private String firstName;

	@Column(length = 30)
	@Size(min = 3, max = 30)
	private String lastName;

	@Column(length = 30, unique = true)
	@Size(min = 3, max = 30)
	private String username;

	@NotNull
	private String password;

	private boolean enabled = true;

	@ElementCollection(targetClass = UserRole.class)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "userId"))
	@Enumerated(EnumType.STRING)
	private Set<UserRole> roles = new HashSet<>();

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

}
