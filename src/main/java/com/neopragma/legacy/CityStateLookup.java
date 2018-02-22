package com.neopragma.legacy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CityStateLookup {

	private String city;
	private String state;
	
	public void lookup(String zipCode) throws URISyntaxException, IOException {
		// Save the returned city and state if content length is greater than zero.
		URI cityStateLookupUri = buildCityStateLookupUri(zipCode);
        HttpGet cityStateLookupRequest = new HttpGet(cityStateLookupUri);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            CloseableHttpResponse cityStateLookupResponse = httpclient.execute(cityStateLookupRequest);

            HttpEntity entity = cityStateLookupResponse.getEntity();
            if (entity != null) {
              	extractCityAndStateFromHtml(cityStateLookupResponse);
            } else {
            	city = "";
            	state = "";
            }
        }
	}

	private void extractCityAndStateFromHtml(CloseableHttpResponse cityStateLookupResponse)
			throws IOException {
		StringBuilder htmlFromLookupResponse = extractHtml(cityStateLookupResponse);
		int metaOffset = htmlFromLookupResponse.indexOf("<meta ");
		int contentOffset = htmlFromLookupResponse.indexOf(" content=\"Zip Code ", metaOffset);
		contentOffset += 19;
		contentOffset = htmlFromLookupResponse.indexOf(" - ", contentOffset);
		contentOffset += 3;
		int stateOffset = htmlFromLookupResponse.indexOf(" ", contentOffset);
		city = htmlFromLookupResponse.substring(contentOffset, stateOffset);
		stateOffset += 1;
		state = htmlFromLookupResponse.substring(stateOffset, stateOffset+2);
	}

	private StringBuilder extractHtml(CloseableHttpResponse cityStateLookupResponse)
			throws IOException, UnsupportedOperationException {
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(cityStateLookupResponse.getEntity().getContent()));
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
	
	private URI buildCityStateLookupUri(String zipCode) throws URISyntaxException {
		return new URIBuilder()
            .setScheme("http")
            .setHost("www.zip-codes.com")
            .setPath("/search.asp")
            .setParameter("fld-zip", zipCode)
            .setParameter("selectTab", "0")
            .setParameter("srch-type", "city")
            .build();
	}
}
