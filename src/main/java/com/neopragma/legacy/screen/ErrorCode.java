package com.neopragma.legacy.screen;

public enum ErrorCode {
	SUCCESS(0),
	SSN_REGEX_FAIL(1),
	SSN_BAD_START(2),
	SSN_ZEROS_IN_MIDDLE(3),
	SSN_SPECIAL_CASE(4),
	INVALID_NAME(6);
	
	Integer value;
	ErrorCode(Integer val) { this.value = val;}
}
