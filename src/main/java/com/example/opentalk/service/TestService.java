package com.example.opentalk.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledFuture;

@Service

public class TestService {
    @Autowired
    private TaskScheduler taskScheduler;


    private ScheduledFuture<?> scheduledFuture;


    public void startTask(int time) {
        scheduledFuture = taskScheduler.schedule(() -> {
            System.out.println("schedule" + time);
        }, new PeriodicTrigger(time));
//        new CronTrigger("0 0 0 ? * THU *");
    }

    public void stopTask() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }
}
