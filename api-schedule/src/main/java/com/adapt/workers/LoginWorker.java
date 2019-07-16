package com.adapt.workers;

import java.io.IOException;

import com.adapt.rest.POSTCall;

public class LoginWorker extends Thread {
	private String URL;

	public LoginWorker(String URL,String threadName) {
		this.URL = URL;
		this.setName(threadName);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		POSTCall login = new POSTCall();
		login.setHubName(getName());
		login.setURL(URL);
		try {
			login.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
