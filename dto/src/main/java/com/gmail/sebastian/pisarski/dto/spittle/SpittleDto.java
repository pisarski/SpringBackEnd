package com.gmail.sebastian.pisarski.dto.spittle;

import java.util.Date;

import com.gmail.sebastian.pisarski.entity.Spittle;

public class SpittleDto extends BasicSpittleDto {

	private Long id;

	private Date time;

	private Long createUserId;

	private Long editUserId;

	private Date updateTime;

	public SpittleDto(Spittle entity) {
		super(entity);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(Long editUserId) {
		this.editUserId = editUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
