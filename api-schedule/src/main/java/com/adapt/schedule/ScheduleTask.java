package com.adapt.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.adapt.workers.HubThreadsOrchestration;

@Component
public class ScheduleTask {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);
	@Value("#{'${hub.ip}'.split(',')}")
	private String[] hubIP;
	@Value("#{'${hub.reboot}'}")
	private boolean reboot;

	@Scheduled(cron = "${cron.expression}")
	public void clearQHubCache() {
		
		for (String ip : hubIP) {
			HubThreadsOrchestration hubWorker = new HubThreadsOrchestration(ip,reboot);
			hubWorker.setName(ip);
			hubWorker.start();
		}
		
	}
}
