package com.lumesse.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Spittle implements Serializable {

	private static final long serialVersionUID = -4284405847520660459L;

	private Long id;
	private String message;
	private Date time;

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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Spittle == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		final Spittle that = (Spittle) obj;

		return new EqualsBuilder().append(this.id, that.id)
				.append(this.time, that.time).append(message, that.message)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(time).append(message)
				.hashCode();
	}

}
