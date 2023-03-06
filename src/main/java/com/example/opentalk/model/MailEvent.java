package com.example.opentalk.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class MailEvent extends ApplicationEvent {
    private String name;
    private String content;
    private String mailName;

    public MailEvent(Object source, String name, String content, String mailName) {
        super(source);
        this.name = name;
        this.content = content;
        this.mailName = mailName;
    }
}