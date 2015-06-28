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
import javax.persistence.Table;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;

import com.lumesse.entity.enums.UserRight;

@Entity
@Table(name = "SpittrUser")
public class User extends BaseEntity {

	private static final long serialVersionUID = 398585519823800766L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30)
	@Length(min = 3, max = 30)
	private String firstName;

	@Column(length = 30)
	@Length(min = 3, max = 30)
	private String lastName;

	@Column(length = 30, unique = true)
	@Length(min = 3, max = 30)
	private String username;

	@NotNull
	private String password;

	private boolean enabled = true;

	@ElementCollection(targetClass = UserRight.class)
	@JoinTable(name = "user_rights", joinColumns = @JoinColumn(name = "userId"))
	@Enumerated(EnumType.STRING)
	@MinSize(1)
	@NotNull
	private Set<UserRight> rights = new HashSet<>();

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

	public Set<UserRight> getRights() {
		return rights;
	}

	public void setRights(Set<UserRight> rights) {
		this.rights = rights;
	}

}
