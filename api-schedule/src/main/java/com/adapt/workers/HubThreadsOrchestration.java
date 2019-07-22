package com.adapt.workers;

import com.adapt.http.OkhttpUnsafe;
/**
 * 2019 Adapt Middle East LLC. Dubai UAE.
 * 
 * @author Akhil Jayakumar
 * @version 1.0
 * @since 2019-07-17
 * 
 *        Executing Login,Clear and reboot thread.
 */
public class HubThreadsOrchestration extends Thread {
	private String hubIp;
	private boolean rebootEnnabled;

	public HubThreadsOrchestration(String hubIp, boolean rebootEnabled) {
		// TODO Auto-generated constructor stub
		this.hubIp = hubIp;
		this.rebootEnnabled = rebootEnabled;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			RebootThread rebootThread = null;
			LoginThread loginThread;
			CachClearThread cachClearThread;
			loginThread = new LoginThread("https://" + hubIp + "/api/system/login", hubIp);
			cachClearThread = new CachClearThread("https://" + hubIp + "/api/media_fetcher?Host=" + hubIp
					+ "&Connection= keep-alive&Accept= application/json, text/plain, */*&Origin= https://192.168.1.64&User-Agent= Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36&Accept-Encoding= gzip, deflate, br&Accept-Language= en-US,en;q=0.9&"
					+ OkhttpUnsafe.getCookie(), hubIp);
			if (rebootEnnabled)
				rebootThread = new RebootThread("https://" + hubIp + "/api/system/reboot", hubIp);
			loginThread.start();
			loginThread.join();
			cachClearThread.start();
			cachClearThread.join();
			if (rebootEnnabled) {
				rebootThread.start();
				rebootThread.join();
			}

			OkhttpUnsafe.removeOkHttpClientFromCache(hubIp);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
