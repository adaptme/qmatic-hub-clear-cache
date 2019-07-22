package com.adapt.workers;

import java.io.IOException;

import com.adapt.http.LoginIntoQmaticHub;

public class LoginThread extends Thread {
	private String URL;

	public LoginThread(String URL,String threadName) {
		this.URL = URL;
		this.setName(threadName);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		LoginIntoQmaticHub login = new LoginIntoQmaticHub();
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
