package com.gmail.sebastian.pisarski.builder.dto.spittle;

import com.gmail.sebastian.pisarski.dto.spittle.BasicSpittleDto;

public class BasicSpittleDtoBuilder {

	private String message;
	private String title;

	public BasicSpittleDtoBuilder withMessage(String message) {
		this.message = message;
		return this;
	}

	public BasicSpittleDtoBuilder withTitle(String title) {
		this.title = title;
		return this;
	}

	public BasicSpittleDtoBuilder withAllValues() {
		return withMessage("message").withTitle("title");
	}

	public BasicSpittleDto build() {
		BasicSpittleDto dto = new BasicSpittleDto();
		setFields(dto);
		return dto;
	}

	protected void setFields(BasicSpittleDto dto) {
		dto.setMessage(message);
		dto.setTitle(title);
	}
}
