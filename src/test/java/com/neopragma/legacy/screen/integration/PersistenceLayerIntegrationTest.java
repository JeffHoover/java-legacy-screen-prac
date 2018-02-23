package com.neopragma.legacy.screen.integration;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.neopragma.legacy.screen.JobApplicant;
import com.neopragma.legacy.screen.PersistenceLayer;

public class PersistenceLayerIntegrationTest {
	
	@Test
	public void saveApplicant() throws URISyntaxException, IOException {
		JobApplicant jobApplicant = new JobApplicant();
		String ssn = "123456789";
		jobApplicant.add("Jeff", "", "Hoover", ssn, "02134");
		
		PersistenceLayer persistance = new PersistenceLayer();
		persistance.save(jobApplicant);
		
		assertEquals("NY", persistance.get(ssn).getState());
	}
}
