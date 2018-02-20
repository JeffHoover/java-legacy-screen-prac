package com.neopragma.legacy.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CityStateLookup {

	private String city;
	private String state;

	public void lookupCityAndState(String zipCode) throws URISyntaxException, IOException {

		URI cityStateLookupUri = buildCityStateSearchUri(zipCode);
        HttpGet cityStateLookupRequest = new HttpGet(cityStateLookupUri);

        // TODO:
        // Add dependency injection and mock CloseableHttpClient and CloseableHttpResponse
        // so that we can test what happens should the lookup service fail.
        // Assignments to "" below are not currently covered by tests.

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
        	
            CloseableHttpResponse cityStateLookupResponse = httpclient.execute(cityStateLookupRequest);
            
            if (cityStateLookupResponse.getEntity() != null) {
              	extractCityAndStateUsingSideEffect(cityStateLookupResponse);
            } else {
            	city = "";
            	state = "";
            }
        }
	}

	private URI buildCityStateSearchUri(String zipCode) throws URISyntaxException {
		return new URIBuilder()
            .setScheme("http")
            .setHost("www.zip-codes.com")
            .setPath("/search.asp")
            .setParameter("fld-zip", zipCode)
            .setParameter("selectTab", "0")
            .setParameter("srch-type", "city")
            .build();
	}

	private void extractCityAndStateUsingSideEffect(CloseableHttpResponse cityStateLookupResponse) throws IOException {
		StringBuilder cityStateLookupResult = extractHtml(cityStateLookupResponse);
		
        int metaOffset = cityStateLookupResult.indexOf("<meta ");
		int contentOffset = cityStateLookupResult.indexOf(" content=\"Zip Code ", metaOffset);
		contentOffset += 19;
		contentOffset = cityStateLookupResult.indexOf(" - ", contentOffset);
		contentOffset += 3;
		int stateOffset = cityStateLookupResult.indexOf(" ", contentOffset);
		city = cityStateLookupResult.substring(contentOffset, stateOffset);
		stateOffset += 1;
		state = cityStateLookupResult.substring(stateOffset, stateOffset+2);
	}

	private StringBuilder extractHtml(CloseableHttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));
		StringBuilder result = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}
	
}
