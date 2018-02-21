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
	
	public void lookup(String zipCode) throws URISyntaxException, IOException {
		// Save the returned city and state if content length is greater than zero.
		URI cityStateLookupUri = buildCityStateLookupUri(zipCode);
        HttpGet cityStateLookupRequest = new HttpGet(cityStateLookupUri);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            CloseableHttpResponse cityStateLookupResponse = httpclient.execute(cityStateLookupRequest);

            HttpEntity entity = cityStateLookupResponse.getEntity();
            if (entity != null) {
              	BufferedReader rd = new BufferedReader(
                        new InputStreamReader(cityStateLookupResponse.getEntity().getContent()));
           		StringBuilder result = new StringBuilder();
           		String line = "";
           		while ((line = rd.readLine()) != null) {
           			result.append(line);
       		    }
                int metaOffset = result.indexOf("<meta ");
                int contentOffset = result.indexOf(" content=\"Zip Code ", metaOffset);
                contentOffset += 19;
                contentOffset = result.indexOf(" - ", contentOffset);
                contentOffset += 3;
                int stateOffset = result.indexOf(" ", contentOffset);
                city = result.substring(contentOffset, stateOffset);
                stateOffset += 1;
                state = result.substring(stateOffset, stateOffset+2);
            } else {
            	city = "";
            	state = "";
            }
        }
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

}
