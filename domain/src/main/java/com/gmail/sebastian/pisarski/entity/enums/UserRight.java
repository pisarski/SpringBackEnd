package com.gmail.sebastian.pisarski.entity.enums;

public enum UserRight {
	
	ADD_SPITTLE,
	EDIT_OWN_SPITTLE,
	EDIT_ALL_SPITTLES,
	REMOVE_OWN_SPITTLE,
	REMOVE_ALL_SPITTLES,
	USER_MANAGEMENT;

	public String getCode() {
		return UserRight.class.getSimpleName() + "." + name();
	}
	
}
