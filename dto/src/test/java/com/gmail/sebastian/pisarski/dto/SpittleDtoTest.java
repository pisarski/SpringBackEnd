package com.gmail.sebastian.pisarski.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gmail.sebastian.pisarski.builder.SpittleBuilder;
import com.gmail.sebastian.pisarski.entity.Spittle;

public class SpittleDtoTest {

	@Test
	public void shouldProperlyMapSpittle() {
		// given
		Spittle spittle = new SpittleBuilder().withAllValuesInitialized().build();
		spittle.getCreateUser().setId(5432L);
		spittle.getEditUser().setId(8765L);

		// when
		SpittleDto dto = new SpittleDto(spittle);

		// then
		assertEquals(spittle.getId(), dto.getId());
		assertEquals(spittle.getMessage(), dto.getMessage());
		assertEquals(spittle.getTime(), dto.getTime());
		assertEquals(spittle.getTitle(), dto.getTitle());
		assertEquals(spittle.getUpdateTime(), dto.getUpdateTime());
		assertEquals(spittle.getCreateUser().getId(), dto.getCreateUserId());
		assertEquals(spittle.getEditUser().getId(), dto.getEditUserId());
	}
}
