package com.adapt.workers;

import java.io.IOException;

import com.adapt.http.RebootQmaticHub;

public class RebootThread extends Thread {
	private String URL;

	public RebootThread(String URL,String threadName) {
		// TODO Auto-generated constructor stub
		this.URL = URL;
		this.setName(threadName);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			RebootQmaticHub reboot = new RebootQmaticHub();
			reboot.setHubName(getName());
			reboot.setURL(URL);
			reboot.execute();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
