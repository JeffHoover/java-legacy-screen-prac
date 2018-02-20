package com.neopragma.legacy.screen.integration;

import static com.neopragma.legacy.screen.ErrorCode.SUCCESS;
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
		jobApplicant.lookupCityAndState("75001");
		assertEquals("Addison", jobApplicant.getCity());
		assertEquals("TX", jobApplicant.getState());
	}

	@Test
	public void itFindsMaranaArizonaBy9DigitZipCode() throws URISyntaxException, IOException {
		jobApplicant.lookupCityAndState("856585578");
		assertEquals("Marana", jobApplicant.getCity());
		assertEquals("AZ", jobApplicant.getState());
	}
	
	@Test
	public void addsApplicant() throws Exception {
		jobApplicant.add("Jeff", "", "Hoover", "123456789", "02134");
		assertEquals(SUCCESS, jobApplicant.validateName());
	}

	@Test
	public void addsSSN() throws Exception {
		jobApplicant.add("Jeff", "", "Hoover", "123456789", "02134");
		assertEquals(SUCCESS, jobApplicant.validateSsn());
	}

	@Test
	public void addsZipCode() throws Exception {
		jobApplicant.add("Jeff", "", "Hoover", "123456789", "02134");
		assertEquals("Allston", jobApplicant.getCity());
	}
}
