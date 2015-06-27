package com.lumesse.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import net.sf.oval.constraint.Length;

@Entity
public class Spittle extends BaseEntity {

	private static final long serialVersionUID = -3382093166703764496L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 1024)
	@NotNull
	@Length(min = 1, max = 1024)
	private String message;

	@Column(nullable = false, length = 70)
	@NotNull
	@Length(min = 1, max = 1024)
	private String title;

	@Column(nullable = false)
	private Date time;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
