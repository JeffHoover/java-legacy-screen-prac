package com.neopragma.legacy.screen.integration;

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
		jobApplicant.setZipCode("75001");
		assertEquals("Addison", jobApplicant.getCity());
		assertEquals("TX", jobApplicant.getState());
	}

	@Test
	public void itFindsMaranaArizonaBy9DigitZipCode() throws URISyntaxException, IOException {
		jobApplicant.setZipCode("856585578");
		assertEquals("Marana", jobApplicant.getCity());
		assertEquals("AZ", jobApplicant.getState());
	}
	
	@Test
	public void addsApplicant() throws Exception {
		jobApplicant.add("Jeff", "", "Hoover", "123456789", "02134");
		assertEquals(0, jobApplicant.validateName());
	}

	@Test
	public void addsSSN() throws Exception {
		jobApplicant.add("Jeff", "", "Hoover", "123456789", "02134");
		assertEquals(0, jobApplicant.validateSsn());
	}

	@Test
	public void addsZipCode() throws Exception {
		jobApplicant.add("Jeff", "", "Hoover", "123456789", "02134");
		assertEquals("Allston", jobApplicant.getCity());
	}
}
