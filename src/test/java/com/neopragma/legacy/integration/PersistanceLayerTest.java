package com.neopragma.legacy.integration;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.neopragma.legacy.screen.JobApplicant;

public class PersistanceLayerTest {
	
	@Test
	public void saveApplicant() throws URISyntaxException, IOException {
		JobApplicant jobApplicant = new JobApplicant();
		String ssn = "123456789";
		jobApplicant.add("Jeff", "", "Hoover", ssn, "02134");
		
		PersistanceLayer persistance = new PersistanceLayer();
		persistance.save(jobApplicant);
		
		assertEquals("NY", persistance.get(ssn).getState());
	}
}
