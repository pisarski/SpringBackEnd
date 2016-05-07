package com.gmail.sebastian.pisarski.dto.spittle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gmail.sebastian.pisarski.builder.SpittleBuilder;
import com.gmail.sebastian.pisarski.builder.dto.spittle.BasicSpittleDtoBuilder;
import com.gmail.sebastian.pisarski.entity.Spittle;

public class BasicSpittleDtoTest {

	@Test
	public void shouldProperlyMapSpittle() {
		// given
		Spittle spittle = new SpittleBuilder().withAllValuesInitialized()
				.build();
		spittle.getCreateUser().setId(5432L);
		spittle.getEditUser().setId(8765L);

		// when
		BasicSpittleDto dto = new BasicSpittleDto(spittle);

		// then
		assertEquals(spittle.getMessage(), dto.getMessage());
		assertEquals(spittle.getTitle(), dto.getTitle());
	}

	@Test
	public void shouldProperlyMapDtoToEntity() {
		// given
		BasicSpittleDto dto = new BasicSpittleDtoBuilder().withAllValues()
				.build();

		// when
		Spittle spittle = dto.getEntity();

		// then
		assertEquals(dto.getTitle(), spittle.getTitle());
		assertEquals(dto.getMessage(), spittle.getMessage());

	}
}
