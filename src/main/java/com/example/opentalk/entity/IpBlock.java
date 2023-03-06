package com.example.opentalk.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class IpBlock {
    @Id
    @Column(length = 100)
    private String Id;

    public IpBlock(String IpBlock, int count, Date startTimeSendRequest, boolean blocked) {
        this.Id = IpBlock;
        this.count = count;
        StartTimeSendRequest = startTimeSendRequest;
        this.blocked = blocked;
    }

    @Column
    private int count;

    @Column
    private Date StartTimeSendRequest;

    @Column
    private boolean blocked;

    @Column
    private Date timeBlock;
}
