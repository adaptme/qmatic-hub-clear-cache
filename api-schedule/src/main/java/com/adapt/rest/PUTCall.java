package com.adapt.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PUTCall implements RESTRequest {

	private final Logger logger = LoggerFactory.getLogger(PUTCall.class);

	private String URL;

	private String hubName;

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
		// TODO Auto-generated method stub
		OkHttpClient client = OkhttpUnsafe.getUnsafeOkHttpClient(getHubName());
		RequestBody reqbody = RequestBody.create(null, new byte[0]);
		Request request = new Request.Builder().url(URL).put(reqbody).build();
		Response response = client.newCall(request).execute();
		logger.info("PUT Request for HUB Rebooting, Response" + response.code());
		response.close();
	}

}
