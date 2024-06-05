package com.tt.scheldule;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class ScheduleAccount {

    @Scheduled(cron = "2/5 * 8 * * *")
    public void testSchedule() {
        System.out.println(new Date() + "Run");
    }
}
