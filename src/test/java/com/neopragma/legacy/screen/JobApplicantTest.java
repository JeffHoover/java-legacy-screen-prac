package com.neopragma.legacy.screen;

import static com.neopragma.legacy.screen.ErrorCode.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class JobApplicantTest {

	private JobApplicant jobApplicant;

	@Before
	public void beforeEach() {
		jobApplicant = new JobApplicant();
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
	public void ssnFormattingTest() {
		jobApplicant.setSsn("123456789");
		assertEquals("123-45-6789", jobApplicant.formatSsn());
	}

	@Test
	public void validSsnWithNoDashes() {
		jobApplicant.setSsn("123456789");
		assertEquals(SUCCESS, jobApplicant.validateSsn());
	}

	@Test
	public void ssnWithDashesInWrongPlaces() {
		jobApplicant.setSsn("12-3456-789");
		assertEquals(SSN_REGEX_FAIL, jobApplicant.validateSsn());
	}

	@Test
	public void validSsnWithDashes() {
		jobApplicant.setSsn("123-45-6789");
		assertEquals(SUCCESS, jobApplicant.validateSsn());
	}

	@Test
	public void ssnIsTooShort() {
		jobApplicant.setSsn("12345678");
		assertEquals(SSN_REGEX_FAIL, jobApplicant.validateSsn());
	}

	@Test
	public void ssnIsTooLong() {
		jobApplicant.setSsn("1234567890");
		assertEquals(SSN_REGEX_FAIL, jobApplicant.validateSsn());
	}

	@Test
	public void ssnAreaNumberIs000() {
		jobApplicant.setSsn("000223333");
		assertEquals(SSN_BAD_AREA_NAME, jobApplicant.validateSsn());
	}

	@Test
	public void ssnAreaNumberIs666() {
		jobApplicant.setSsn("666223333");
		assertEquals(SSN_BAD_AREA_NAME, jobApplicant.validateSsn());
	}

	@Test
	public void ssnAreaNumberStartsWith9() {
		jobApplicant.setSsn("900223333");
		assertEquals(SSN_BAD_AREA_NAME, jobApplicant.validateSsn());
	}

	@Test
	public void ssnSerialNumberIs0000() {
		jobApplicant.setSsn("111220000");
		assertEquals(SSN_BAD_SERIAL_NUMBER, jobApplicant.validateSsn());
	}

	@Test
	public void itRejectsSsn078051120() {
		jobApplicant.setSsn("078051120");
		assertEquals(SSN_SPECIAL_CASE, jobApplicant.validateSsn());
	}

	@Test
	public void itRejectsSsn219099999() {
		jobApplicant.setSsn("219099999");
		assertEquals(SSN_SPECIAL_CASE, jobApplicant.validateSsn());
	}

}
