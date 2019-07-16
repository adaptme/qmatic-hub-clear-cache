package com.adapt.workers;

import com.adapt.rest.OkhttpUnsafe;

public class HubWorker extends Thread {
	private String hubIp;
	private boolean rebootEnnabled;

	public HubWorker(String hubIp, boolean rebootEnabled) {
		// TODO Auto-generated constructor stub
		this.hubIp = hubIp;
		this.rebootEnnabled = rebootEnabled;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			RebootWorker rebootWorker = null;
			LoginWorker loginWorker;
			CachClearWorker cachClearWorker;
			loginWorker = new LoginWorker("https://" + hubIp + "/api/system/login", hubIp);
			cachClearWorker = new CachClearWorker("https://" + hubIp + "/api/media_fetcher?Host=" + hubIp
					+ "&Connection= keep-alive&Accept= application/json, text/plain, */*&Origin= https://192.168.1.64&User-Agent= Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36&Accept-Encoding= gzip, deflate, br&Accept-Language= en-US,en;q=0.9&"
					+ OkhttpUnsafe.getCookie(), hubIp);
			if (rebootEnnabled)
				rebootWorker = new RebootWorker("https://" + hubIp + "/api/system/reboot", hubIp);
			loginWorker.start();
			loginWorker.join();
			cachClearWorker.start();
			cachClearWorker.join();
			if (rebootEnnabled) {
				rebootWorker.start();
				rebootWorker.join();
			}
			
			OkhttpUnsafe.removeOkHttpClientFromCache(hubIp);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
