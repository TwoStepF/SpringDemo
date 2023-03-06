package com.example.opentalk.controller;

import com.example.opentalk.dto.CreateOpentalkRequest;
import com.example.opentalk.dto.LessonDTO;
import com.example.opentalk.dto.OpentalkDTo;
import com.example.opentalk.model.Status;
import com.example.opentalk.service.OpentalkService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/opentalk")
@AllArgsConstructor
public class OpentalkController {

    private final Gson gson;
    private final OpentalkService opentalkService;


    @PostMapping()
    private ResponseEntity<?> SubscribeOpenTalk(@RequestBody CreateOpentalkRequest createOpentalkRequest){
        Status status = opentalkService.signupOpentalk(createOpentalkRequest);
        return ResponseEntity.status(status.getStatus()).body(gson.toJson(status));
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping()
    private ResponseEntity<?> UpdateOpentalk(@RequestBody CreateOpentalkRequest createOpentalkRequest){
        Status status = opentalkService.updateOpentalk(createOpentalkRequest);
        return ResponseEntity.status(status.getStatus()).body(gson.toJson(status));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> DeleteOpentalk(@PathVariable Long id){
        Status status = opentalkService.deleteOpentalk(id);
        return ResponseEntity.status(status.getStatus()).body(gson.toJson(status));
    }

    @GetMapping("/search")
    private ResponseEntity<List<OpentalkDTo>> GetAllOpentalkOfUser(@PathParam("statusID") Long statusID,
                                                   @PathParam("owner") String owner,
                                                   @PathParam("time_start") @DateTimeFormat(pattern="yyyy-MM-dd") Date time_start,
                                                   @PathParam("time_last") @DateTimeFormat(pattern="yyyy-MM-dd") Date time_last,
                                                   @PathParam("company") String company,
                                                   @PathParam("page") int page,
                                                   @PathParam("limit") int limit){
        OpentalkDTo opentalkDTo = new OpentalkDTo(1L, "god of war", "KhanhTran", "2012-04-23", "hello", "hà nội");
        return ResponseEntity.status(HttpStatus.OK).body(opentalkService.getAllOpentalk(page, limit, statusID, owner, time_start, time_last, company));
    }

    @GetMapping("/all")
    private ResponseEntity<List<OpentalkDTo>> GetAllOpentalkOfUser(){
        return ResponseEntity.status(HttpStatus.OK).body(opentalkService.getAll());
    }

}
