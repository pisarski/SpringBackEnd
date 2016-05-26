package com.gmail.sebastian.pisarski.dto.spittle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.gmail.sebastian.pisarski.dto.BaseDto;
import com.gmail.sebastian.pisarski.entity.Spittle;

@ApiModel(description = "Contains all data used for adding/editing spittles")
public class BasicSpittleDto extends BaseDto<Spittle> {

	private String message;

	private String title;

	public BasicSpittleDto(Spittle entity) {
		super(entity, Spittle.class);
	}

	public BasicSpittleDto() {
		super(Spittle.class);
	}

	@ApiModelProperty(required = true)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@ApiModelProperty(required = true)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
