package com.adapt.workers;

import java.io.IOException;

import com.adapt.rest.PUTCall;

public class RebootWorker extends Thread {
	private String URL;

	public RebootWorker(String URL,String threadName) {
		// TODO Auto-generated constructor stub
		this.URL = URL;
		this.setName(threadName);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			PUTCall reboot = new PUTCall();
			reboot.setHubName(getName());
			reboot.setURL(URL);
			reboot.execute();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
