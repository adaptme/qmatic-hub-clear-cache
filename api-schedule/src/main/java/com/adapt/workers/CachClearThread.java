package com.adapt.workers;

import java.io.IOException;

import com.adapt.http.ClearQmaticHubCache;
import com.adapt.http.OkhttpUnsafe;

/**
 * 2019 Adapt Middle East LLC. Dubai UAE.
 * 
 * @author Akhil Jayakumar
 * @version 1.0
 * @since 2019-07-17
 * 
 *        CacheClearThread to execute ClearQmaticHubs
 */
public class CachClearThread extends Thread {
	// Hub Clear URL
	private String URL;

	/*
	 * Hub Cache CLear url and name for this thread.
	 * 
	 */

	public CachClearThread(String URL, String threadName) {
		// TODO Auto-generated constructor stub
		this.URL = URL;
		this.setName(threadName);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ClearQmaticHubCache clear = new ClearQmaticHubCache();
		clear.setHubName(getName());
		/*
		 * Appending cookie with request URL.
		 */
		clear.setURL(URL + "&" + OkhttpUnsafe.getCookie());
		try {
			clear.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
