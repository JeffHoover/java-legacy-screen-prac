package com.neopragma.legacy;

public enum ErrorCode {
	SUCCESS(0),
	SSN_REGEX_FAIL(1),
	SSN_BAD_AREA_NAME(2),
	SSN_BAD_SERIAL_NUMBER(3),
	SSN_SPECIAL_CASE(4),
	INVALID_NAME(6);
	
	Integer value;
	ErrorCode(Integer val) { this.value = val;}
}
