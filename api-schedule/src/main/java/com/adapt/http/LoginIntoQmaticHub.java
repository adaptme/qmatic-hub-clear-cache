package com.adapt.http;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.MediaType;
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
public class LoginIntoQmaticHub implements RESTRequest {
	private final Logger logger = LoggerFactory.getLogger(LoginIntoQmaticHub.class);
	/*
	 * Hub Login URL
	 */
	private String URL;
	/*
	 * Hub IP address
	 */
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
		String cookie;
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"username\":\"InstallAdmin\",\"password\":\"ulan\"}");
		Request request = new Request.Builder().url(URL).post(body).addHeader("content-type", "application/json")
				.addHeader("cache-control", "no-cache").build();
		Response response = client.newCall(request).execute();
		logger.info("Login to HUB Response" + response.code());
		logger.info("URL:" + URL);
		cookie = response.headers("Set-Cookie").toString();
		cookie = cookie.substring(cookie.indexOf("[") + 1, cookie.indexOf("]"));
		String[] cookieNameValue = cookie.split(";");
		if (cookieNameValue.length > 1) {
			if (cookieNameValue[0].startsWith("Qmatic_hub_session")) {
				OkhttpUnsafe.setCookie(cookieNameValue[0]);
			}
		}
		response.close();

	}

}
