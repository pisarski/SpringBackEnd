package com.gmail.sebastian.pisarski.dto.spittle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import com.gmail.sebastian.pisarski.entity.Spittle;

@ApiModel(description = "Full information about spittle")
public class SpittleDto extends BasicSpittleDto {

	private Long id;

	private Date time;

	private Long createUserId;

	private Long editUserId;

	private Date updateTime;

	public SpittleDto() {
	}

	public SpittleDto(Spittle entity) {
		super(entity);
	}

	@ApiModelProperty(readOnly = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ApiModelProperty(readOnly = true)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@ApiModelProperty(readOnly = true)
	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	@ApiModelProperty(readOnly = true)
	public Long getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(Long editUserId) {
		this.editUserId = editUserId;
	}

	@ApiModelProperty(readOnly = true)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
