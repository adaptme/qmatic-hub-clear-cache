package com.adapt.http;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 2019 Adapt Middle East LLC. Dubai UAE.
 * 
 * @author Akhil Jayakumar
 * @version 1.0
 * @since 2019-07-17
 * 
 *        Class using to Login to Qmatic Hub with HTTP Post Request. This class
 *        will login with default username and password InstallAdmin and ulan
 *        and store the cookie to cache.
 */
public class RebootQmaticHub implements RESTRequest {

	private final Logger logger = LoggerFactory.getLogger(RebootQmaticHub.class);
	// URL to reboot hub
	private String URL;
	// Hub IP
	private String hubName;

    //setters and getters
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
