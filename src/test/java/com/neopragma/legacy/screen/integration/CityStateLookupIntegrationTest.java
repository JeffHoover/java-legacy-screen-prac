package com.neopragma.legacy.screen.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.neopragma.legacy.screen.CityStateLookup;

public class CityStateLookupIntegrationTest {

	@Test
	public void doesLookupCity() throws ClientProtocolException, URISyntaxException, IOException  {
		CityStateLookup lookup = new CityStateLookup();
		
		lookup.lookupCityAndState("02134");
		assertEquals("Allston", lookup.getCity());
	}
	
	@Test
	public void doesLookupState() throws ClientProtocolException, URISyntaxException, IOException  {
		CityStateLookup lookup = new CityStateLookup();
		
		lookup.lookupCityAndState("02134");
		assertEquals("MA", lookup.getState());
	}
}
