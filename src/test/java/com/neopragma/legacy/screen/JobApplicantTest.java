package com.neopragma.legacy.screen;

import static com.neopragma.legacy.screen.ErrorCode.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

public class JobApplicantTest {
	
	private JobApplicant jobApplicant;
	
	@Before
	public void beforeEach() {
		jobApplicant = new JobApplicant();
	}
	
	@Test
	public void addsApplicantName() throws Exception {
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
	
	@Test
	public void completeNameProvided() {
		jobApplicant.setName("First", "Middle", "Last");
		assertEquals(SUCCESS, jobApplicant.validateName());
	}
	
	@Test
	public void firstAndLastNamesProvided() {
		jobApplicant.setName("First", null, "Last");
		assertEquals(SUCCESS, jobApplicant.validateName());
	}
	
	@Test
	public void missingFirstName() {
		jobApplicant.setName(null, null, "Last");
		assertEquals(INVALID_NAME, jobApplicant.validateName());
	}
	
	@Test
	public void missingLastName() {
		jobApplicant.setName("First", null, null);
		assertEquals(INVALID_NAME, jobApplicant.validateName());
	}
	
	@Test
	public void completeSpanishNameProvided() {
		jobApplicant.setSpanishName("PrimerNombre", "SegundoNombre", "PrimerApellido", "SegundoApellido");
		assertEquals(SUCCESS, jobApplicant.validateName());
	}
	
	@Test
	public void spanishNameWithOneFirstNameProvided() {
		jobApplicant.setSpanishName("PrimerNombre", null, "PrimerApellido", "SegundoApellido");
		assertEquals(SUCCESS, jobApplicant.validateName());
	}
	
	@Test
	public void spanishNameWithOneLastNameProvided() {
		jobApplicant.setSpanishName("PrimerNombre", null, "PrimerApellido", null);
		assertEquals(SUCCESS, jobApplicant.validateName());
	}
	
	@Test
	public void spanishNameWithNoFirstNameProvided() {
		jobApplicant.setSpanishName(null, null, "PrimerApellido", null);
		assertEquals(INVALID_NAME, jobApplicant.validateName());
	}
	
	@Test
	public void spanishNameWithNoLastNameProvided() {
		jobApplicant.setSpanishName("PrimerNombre", "SegundoNombre", null, null);
		assertEquals(INVALID_NAME, jobApplicant.validateName());
	}
	
	@Test
	public void formatEnglishNameLastNameFirst() {
		jobApplicant.setName("First", "Middle", "Last");
		assertEquals("Last, First Middle", jobApplicant.formatLastNameFirst());
	}

	@Test
	public void itCurrentlyGivesWeirdResultForInvalidZip() throws URISyntaxException, IOException {
		String invalidZipCode = "1";
		jobApplicant.add("Jeff", "", "Hoover", "123456789", invalidZipCode);
		assertEquals("Facts", jobApplicant.getCity());
		assertEquals("& ", jobApplicant.getState());
	}

}
