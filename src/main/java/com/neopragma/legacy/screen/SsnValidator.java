package com.neopragma.legacy.screen;

import static com.neopragma.legacy.ErrorCode.SSN_BAD_SERIAL_NUMBER;
import static com.neopragma.legacy.ErrorCode.SSN_REGEX_FAIL;
import static com.neopragma.legacy.ErrorCode.SSN_SPECIAL_CASE;
import static com.neopragma.legacy.ErrorCode.SUCCESS;

import com.neopragma.legacy.ErrorCode;

public class SsnValidator {
	
	private String[] specialCases = new String[] {
	    "219099999", "078051120"
	};
	
	public ErrorCode validate(String ssn) {
		if (ssnFailsRegex(ssn)) {
			return SSN_REGEX_FAIL;
		}
		if (ssnHasBadAreaName(ssn) ) {
			return ErrorCode.SSN_BAD_AREA_NAME;
		}
		if (ssnHasBadSerialNumber(ssn) ) {
			return SSN_BAD_SERIAL_NUMBER;
		}
		if (ssnMatchesSpecialCase(ssn)) {
			return SSN_SPECIAL_CASE;
		}

		return SUCCESS;
	}

	private boolean ssnFailsRegex(String ssn) {
		return !ssn.matches("\\d{9}");
	}

	private boolean ssnMatchesSpecialCase(String ssn) {
		for (int i = 0 ; i < specialCases.length ; i++ ) {
			if ( ssn.equals(specialCases[i]) ) {
				return true;
			}
		}
		return false;
	}

	private boolean ssnHasBadSerialNumber(String ssn) {
		return "0000".equals(ssn.substring(5));
	}

	private boolean ssnHasBadAreaName(String ssn) {
		return "000".equals(ssn.substring(0,3)) || 
			 "666".equals(ssn.substring(0,3)) ||
			 "9".equals(ssn.substring(0,1));
	}
}
