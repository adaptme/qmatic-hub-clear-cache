package com.adapt.workers;

import java.io.IOException;

import com.adapt.rest.DELETECall;
import com.adapt.rest.OkhttpUnsafe;

public class CachClearWorker extends Thread {
	private String URL;

	public CachClearWorker(String URL,String threadName) {
		// TODO Auto-generated constructor stub
		this.URL = URL;
		this.setName(threadName);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DELETECall clear = new DELETECall();
		clear.setHubName(getName());
		clear.setURL(URL + "&" + OkhttpUnsafe.getCookie());
		try {
			clear.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
