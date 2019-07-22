package com.adapt.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adapt.workers.HubThreadsOrchestration;

/**
 * 2019 Adapt Middle East LLC. Dubai UAE.
 * 
 * @author Akhil Jayakumar
 * @version 1.0
 * @since 2019-07-17 RestController class to request cache clear and reboot for
 *        all devices.
 */
@RestController
@RequestMapping("hub")
public class ClearCacheController {

	private static final Logger logger = LoggerFactory.getLogger(ClearCacheController.class);
	// get All IP address mentioned in application.properties.
	@Value("#{'${hub.ip}'.split(',')}")
	private String[] hubIP;

	// Reboot all available devices
	@RequestMapping(value = "/reboot/all", method = RequestMethod.GET)
	public void clearAndReboot() {
		logger.info("Requesting to reboot all hub.");
		for (String ip : hubIP) {
			/*
			 * Thread to handle Login,clear and reboot hubs. true to reboot hub
			 */
			HubThreadsOrchestration hubWorker = new HubThreadsOrchestration(ip, true);
			/*
			 * Setting IP as hub name. using to store cookies in map.
			 */
			hubWorker.setName(ip);
			/*
			 * Starting Thread.
			 */
			hubWorker.start();
		}
	}
}
