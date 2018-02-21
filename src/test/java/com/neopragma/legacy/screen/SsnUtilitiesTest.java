package com.neopragma.legacy.screen;

import static com.neopragma.legacy.ErrorCode.SSN_BAD_AREA_NAME;
import static com.neopragma.legacy.ErrorCode.SSN_BAD_SERIAL_NUMBER;
import static com.neopragma.legacy.ErrorCode.SSN_REGEX_FAIL;
import static com.neopragma.legacy.ErrorCode.SSN_SPECIAL_CASE;
import static com.neopragma.legacy.ErrorCode.SUCCESS;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SsnUtilitiesTest {

	SsnUtilities ssnUtilities = new SsnUtilities();
	
	@Test
	public void ssnFormattingTest() {
		assertEquals("123-45-6789", ssnUtilities.formatSsn("123456789"));
	}
	
	@Test
	public void itRejectsSsn219099999() {
		assertEquals(SSN_SPECIAL_CASE, ssnUtilities.validate("219099999"));
	}
	
	@Test
	public void validSsnWithNoDashes() {
		assertEquals(SUCCESS, ssnUtilities.validate("123456789"));
	}
	
	@Test
	public void ssnWithDashesInWrongPlaces() {
		assertEquals(SSN_REGEX_FAIL, ssnUtilities.validate("12-3456-789"));
	}

	@Test
	public void validSsnWithDashes() {
		assertEquals(SUCCESS, ssnUtilities.validate("123-45-6789"));
	}
	
	@Test
	public void ssnIsTooShort() {
		assertEquals(SSN_REGEX_FAIL, ssnUtilities.validate("12345678"));
	}
	
	@Test
	public void ssnIsTooLong() {
		assertEquals(SSN_REGEX_FAIL, ssnUtilities.validate("1234567890"));
	}
	
	@Test
	public void ssnAreaNumberIs000() {
		assertEquals(SSN_BAD_AREA_NAME, ssnUtilities.validate("000223333"));
	}
	
	@Test
	public void ssnAreaNumberIs666() {
		assertEquals(SSN_BAD_AREA_NAME, ssnUtilities.validate("666223333"));
	}
	
	@Test
	public void ssnAreaNumberStartsWith9() {
		assertEquals(SSN_BAD_AREA_NAME, ssnUtilities.validate("900223333"));
	}
	
	@Test
	public void ssnSerialNumberIs0000() {
		assertEquals(SSN_BAD_SERIAL_NUMBER, ssnUtilities.validate("111220000"));
	}
	
	@Test
	public void itRejectsSsn078051120() {
		assertEquals(SSN_SPECIAL_CASE, ssnUtilities.validate("078051120"));
	}
}
