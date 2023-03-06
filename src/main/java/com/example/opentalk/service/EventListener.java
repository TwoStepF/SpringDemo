package com.example.opentalk.service;

import com.example.opentalk.model.MailEvent;
import com.example.opentalk.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventListener {

    private final MailService mailService;

    @org.springframework.context.event.EventListener
    public void onApplicationEvent(MailEvent mailEvent) throws Status {
        try{
            mailService.sendMailWelcome(mailEvent.getMailName(), mailEvent.getContent(), mailEvent.getName());
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
