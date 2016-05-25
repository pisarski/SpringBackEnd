package com.gmail.sebastian.pisarski.dto;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.modelmapper.ModelMapper;

import io.swagger.annotations.ApiModelProperty;

public abstract class BaseDto<T> {

	private Class<T> clazz;

	protected BaseDto(Class<T> clazz) {
		this.clazz = clazz;
	}

	public BaseDto(T entity, Class<T> clazz) {
		this.clazz = clazz;
		new ModelMapper().map(entity, this);
	}

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	public T getEntity() {
		return new ModelMapper().map(this, clazz);
	}

}
