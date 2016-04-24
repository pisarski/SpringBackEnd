package com.gmail.sebastian.pisarski.dto;

import org.modelmapper.ModelMapper;

public abstract class BaseDto<T> {

	public BaseDto(T entity) {
		new ModelMapper().map(entity, this);
	}

}
