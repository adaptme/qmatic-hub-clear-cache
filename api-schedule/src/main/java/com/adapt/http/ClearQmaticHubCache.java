package com.adapt.http;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 2019 Adapt Middle East LLC. Dubai UAE.
 * 
 * @author Akhil Jayakumar
 * @version 1.0
 * @since 2019-07-17
 * 
 *        Class to clear Qmatic Hub Cache with http Request
 */
public class ClearQmaticHubCache implements RESTRequest {
	private final Logger logger = LoggerFactory.getLogger(ClearQmaticHubCache.class);
	// Url to login to Hub
	private String URL;
	// Hub IP
	private String hubName;

//Getters and setters
	public String getHubName() {
		return hubName;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	@Override
	public void execute() throws IOException {
		OkHttpClient client = OkhttpUnsafe.getUnsafeOkHttpClient(getHubName());
		Request request = new Request.Builder().url(URL).delete(null).build();
		Response response = client.newCall(request).execute();
		logger.info("DELETE Request To Clear Response" + response.code());
		logger.info("URL:" + URL);
		response.close();
	}

}
