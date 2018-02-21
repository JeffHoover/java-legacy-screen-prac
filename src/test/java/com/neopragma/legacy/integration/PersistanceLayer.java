package com.neopragma.legacy.integration;

import java.io.IOException;
import java.net.URISyntaxException;

import com.neopragma.legacy.screen.JobApplicant;

public class PersistanceLayer {

	public void save(JobApplicant jobApplicant) {
	}

	public JobApplicant get(String zip) throws URISyntaxException, IOException {
		return makeDummyApplicant(zip);
	}

	private JobApplicant makeDummyApplicant(String zipCode) throws URISyntaxException, IOException {
		JobApplicant dummyApplicant = new JobApplicant();
		dummyApplicant.add("dummy", "", "dummy", "123456789", zipCode);
		return dummyApplicant;
	}

}
