package com.neopragma.legacy.screen;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CityStateLookupTest {
	@Test
	public void lookupCityState() throws Exception {
		CityStateLookup cityStateLookup = new CityStateLookup();
		cityStateLookup.lookup("02134");
		
		assertEquals("Allston", cityStateLookup.getCity());
		assertEquals("MA", cityStateLookup.getState());
	}
}
