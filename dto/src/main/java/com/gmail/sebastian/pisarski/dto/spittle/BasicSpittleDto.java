package com.gmail.sebastian.pisarski.dto.spittle;

import com.gmail.sebastian.pisarski.dto.BaseDto;
import com.gmail.sebastian.pisarski.entity.Spittle;

public class BasicSpittleDto extends BaseDto<Spittle> {

	private Long id;

	private String message;

	private String title;

	public BasicSpittleDto(Spittle entity) {
		super(entity, Spittle.class);
	}

	public BasicSpittleDto() {
		super(Spittle.class);
	}

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

}
