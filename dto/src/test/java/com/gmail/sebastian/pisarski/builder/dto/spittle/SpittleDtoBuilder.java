package com.gmail.sebastian.pisarski.builder.dto.spittle;

import java.util.Date;

import com.gmail.sebastian.pisarski.dto.spittle.SpittleDto;

public class SpittleDtoBuilder extends BasicSpittleDtoBuilder {

	private Long id;
	private Date time;
	private Long createUserId;
	private Long editUserId;
	private Date updateTime;

	public SpittleDtoBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public SpittleDtoBuilder withTime(Date time) {
		this.time = time;
		return this;
	}

	public SpittleDtoBuilder withCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
		return this;
	}

	public SpittleDtoBuilder withEditUserId(Long editUserId) {
		this.editUserId = editUserId;
		return this;
	}

	public SpittleDtoBuilder withUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	@Override
	public SpittleDtoBuilder withAllValues() {
		super.withAllValues();
		return withId(7643L).withCreateUserId(432L).withEditUserId(908L).withTime(new Date())
				.withUpdateTime(new Date());
	}

	@Override
	public SpittleDto build() {
		SpittleDto dto = new SpittleDto();
		setFields(dto);
		dto.setId(id);
		dto.setCreateUserId(createUserId);
		dto.setEditUserId(editUserId);
		dto.setTime(time);
		dto.setUpdateTime(updateTime);
		return dto;
	}

}
