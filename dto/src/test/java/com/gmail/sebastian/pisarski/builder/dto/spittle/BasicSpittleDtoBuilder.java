package com.gmail.sebastian.pisarski.builder.dto.spittle;

import com.gmail.sebastian.pisarski.dto.spittle.BasicSpittleDto;

public class BasicSpittleDtoBuilder {

	private Long id;
	private String message;
	private String title;

	public BasicSpittleDtoBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public BasicSpittleDtoBuilder withMessage(String message) {
		this.message = message;
		return this;
	}

	public BasicSpittleDtoBuilder withTitle(String title) {
		this.title = title;
		return this;
	}

	public BasicSpittleDtoBuilder withAllValues() {
		return withId(654L).withMessage("message").withTitle("title");
	}

	public BasicSpittleDto build() {
		BasicSpittleDto dto = new BasicSpittleDto();
		dto.setId(id);
		dto.setMessage(message);
		dto.setTitle(title);
		return dto;
	}
}
