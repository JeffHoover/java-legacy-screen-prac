package com.neopragma.legacy;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.neopragma.legacy.PersistanceLayer;
import com.neopragma.legacy.screen.JobApplicant;

public class PersistanceLayerTest {

	@Test
	public void saveApplicant() throws URISyntaxException, IOException {
		JobApplicant jobApplicant = new JobApplicant();
		jobApplicant.add("Jeff", "", "Hoover", "123456789", "02134");
		
		PersistanceLayer persistance = new PersistanceLayer();
		persistance.save(jobApplicant);
		
		assertEquals("MA", persistance.get("123456789").getState());
	}
}
