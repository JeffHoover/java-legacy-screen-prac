package com.neopragma.legacy.screen.integration;

import java.io.IOException;
import java.net.URISyntaxException;

import com.neopragma.legacy.screen.JobApplicant;

public class PersistanceLayer {

	public void save(JobApplicant jobApplicant) {

		System.out.println("Saving to database: " + jobApplicant.formatLastNameFirst());
	}

	public JobApplicant get(String ssn) throws URISyntaxException, IOException {
		
		JobApplicant temporaryUser = new JobApplicant();
		temporaryUser.add("Jeff", "", "Hoover", ssn, "02134");
		
		return temporaryUser;
	}
}
