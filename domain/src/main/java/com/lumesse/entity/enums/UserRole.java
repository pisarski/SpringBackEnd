package com.lumesse.entity.enums;

public enum UserRole {
	ADMIN, USER;

	public String getRole() {
		return "ROLE_" + name();
	}

	public String getMsgCode() {
		return getClass().getSimpleName() + "." + name();
	}
}
