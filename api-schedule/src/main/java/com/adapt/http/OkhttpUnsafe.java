package com.adapt.http;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Interceptor;
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
 *        Qmatic Hub Using SSL Certificate in its server. Making http request
 *        from java is throwing a handshake error. to bypass this issue, we are
 *        building a custom okHttpObject.
 */
public class OkhttpUnsafe {
	// Variable to store cookie after login
	/*
	 * Hub Using authentication and once authenticated it using a session cookie for
	 * next request. like old http authentication.we are using this variable to
	 * store cookie info
	 */
	private static String cookie = null;
	private static final Logger logger = LoggerFactory.getLogger(OkhttpUnsafe.class);

	// gettes and setters
	public static void setCookie(String cookie) {
		OkhttpUnsafe.cookie = cookie;
	}

	public static String getCookie() {
		return OkhttpUnsafe.cookie;
	}

	/*
	 * a map to store cookies for corresponding hubs.it will work like a cache.
	 */
	private static Map<String, OkHttpClient> OkHttpClientCache = new HashMap<String, OkHttpClient>();

	/*
	 * add cookie to cache
	 */
	public static void addOkHttpClientToCache(String key, OkHttpClient value) {
		OkHttpClientCache.put(key, value);
		logger.info("HttpClient added to cache:" + "HUB :-" + key);
	}

	/*
	 * Retrieve cookie
	 */
	public static OkHttpClient getOkHttpClientFromCache(String key) {
		logger.info("HttpClient retrived from cache:" + "HUB :-" + key);
		return OkHttpClientCache.get(key);
	}

	/*
	 * remove cookie from cache.
	 */
	public static OkHttpClient removeOkHttpClientFromCache(String key) {
		logger.info("HttpClient removed from cache:" + "HUB :-" + key);
		return OkHttpClientCache.remove(key);
	}

	/*
	 * Qmatic Media Hub Using SSL Certificate(Self signed/outdated). to Prevent
	 * Handshake We are using a custom OKHttpClient Object.
	 */
	public static synchronized OkHttpClient getUnsafeOkHttpClient(String hubName) {
		try {
			// Create a trust manager that does not validate certificate chains
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[] {};
				}
			} };

			// Install the all-trusting trust manager
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
			// Build OKHttpClient
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			// Once the login request completed cookie string will get the data
			if (OkhttpUnsafe.cookie != null) {
				builder.addNetworkInterceptor(new Interceptor() {
					@Override
					public Response intercept(Chain chain) throws IOException {
						final Request original = chain.request();
						final Request authorized = original.newBuilder().addHeader("Cookie", OkhttpUnsafe.cookie)
								.build();
						logger.info("Cokie Added:   " + cookie);
						return chain.proceed(authorized);
					}
				});
			}

			builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
			// set Connect Timeout and Read Timeout 20 sec
			builder.connectTimeout(20, TimeUnit.SECONDS);
			builder.readTimeout(20, TimeUnit.SECONDS);
			builder.hostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			OkHttpClient okHttpClient = builder.build();
			if (getCookie() != null) {
				addOkHttpClientToCache(hubName, okHttpClient);
				return getOkHttpClientFromCache(hubName);
			}
			return okHttpClient;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
