package com.gmail.sebastian.pisarski.builder;

import java.util.Date;

import com.gmail.sebastian.pisarski.entity.Spittle;
import com.gmail.sebastian.pisarski.entity.User;

public class SpittleBuilder {

	private Long id;
	private String message;
	private String title;
	private Date time;
	private User createUser;
	private User editUser;
	private Date updateTime;

	public SpittleBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public SpittleBuilder withMessage(String message) {
		this.message = message;
		return this;
	}

	public SpittleBuilder withTitle(String title) {
		this.title = title;
		return this;
	}

	public SpittleBuilder withTime(Date time) {
		this.time = time;
		return this;
	}

	public SpittleBuilder withCreateUser(User createUser) {
		this.createUser = createUser;
		return this;
	}

	public SpittleBuilder withEditUser(User editUser) {
		this.editUser = editUser;
		return this;
	}

	public SpittleBuilder withUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	public SpittleBuilder withAllValuesInitialized() {
		return withId(654L)
				.withMessage("message")
				.withTime(new Date())
				.withTitle("title")
				.withUpdateTime(new Date())
				.withCreateUser(new UserBuilder().withAnyValues().build())
				.withEditUser(new UserBuilder().withAnyValues().build());
	}

	public Spittle build() {
		Spittle spittle = new Spittle();
		spittle.setId(id);
		spittle.setCreateUser(createUser);
		spittle.setEditUser(editUser);
		spittle.setMessage(message);
		spittle.setTime(time);
		spittle.setTitle(title);
		spittle.setUpdateTime(updateTime);
		return spittle;
	}
}