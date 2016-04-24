package com.gmail.sebastian.pisarski.dto;

import org.modelmapper.ModelMapper;

public abstract class BaseDto<T> {

	private Class<T> clazz;

	protected BaseDto(Class<T> clazz) {
		this.clazz = clazz;
	}

	public BaseDto(T entity, Class<T> clazz) {
		this.clazz = clazz;
		new ModelMapper().map(entity, this);
	}

	public T getEntity() {
		return new ModelMapper().map(this, clazz);
	}

}
