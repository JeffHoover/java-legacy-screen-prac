package com.neopragma.legacy.screen;

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

	public void lookupCityAndState(String zipCode) throws URISyntaxException, IOException {

		// Use a service to look up the city and state based on zip code.
		// Save the returned city and state if content length is greater than zero.
		URI uri = new URIBuilder()
            .setScheme("http")
            .setHost("www.zip-codes.com")
            .setPath("/search.asp")
            .setParameter("fld-zip", zipCode)
            .setParameter("selectTab", "0")
            .setParameter("srch-type", "city")
            .build();
        HttpGet request = new HttpGet(uri);

        // TODO - Add dependency injection and mock CloseableHttpClient and CloseableHttpResponse
        // so that we can test what happens should the lookup service fail

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpclient.execute(request);
            if (response.getEntity() != null) {
              	StringBuilder result = extractResultFromResponse(response);
                extractCityAndStateFromResult(result);
            } else {
            	city = ""; // TODO - Not covered by a test - see above
            	state = "";
            }

        }
	}

	private StringBuilder extractResultFromResponse(CloseableHttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));
		StringBuilder result = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result;
	}

	private void extractCityAndStateFromResult(StringBuilder result) {
		int metaOffset = result.indexOf("<meta ");
		int contentOffset = result.indexOf(" content=\"Zip Code ", metaOffset);
		contentOffset += 19;
		contentOffset = result.indexOf(" - ", contentOffset);
		contentOffset += 3;
		int stateOffset = result.indexOf(" ", contentOffset);
		city = result.substring(contentOffset, stateOffset);
		stateOffset += 1;
		state = result.substring(stateOffset, stateOffset+2);
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	
}
