package com.neopragma.legacy.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import com.neopragma.legacy.screen.JobApplicant;

public class JobApplicantIntegrationTest {

	private JobApplicant jobApplicant;
	
	@Before
	public void beforeEach() {
		jobApplicant = new JobApplicant();
	}
	
	@Test
	public void itFindsAddisonTexasBy5DigitZipCode() throws URISyntaxException, IOException {
		jobApplicant.lookupCityState("75001");
		assertEquals("Addison", jobApplicant.getCity());
		assertEquals("TX", jobApplicant.getState());
	}
	
	@Test
	public void itFindsMaranaArizonaBy9DigitZipCode() throws URISyntaxException, IOException {
		jobApplicant.lookupCityState("856585578");
		assertEquals("Marana", jobApplicant.getCity());
		assertEquals("AZ", jobApplicant.getState());
	}
}
