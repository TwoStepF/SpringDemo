package com.example.opentalk.controller;

import com.example.opentalk.dto.CreateOpentalkRequest;
import com.example.opentalk.dto.KeyDTO;
import com.example.opentalk.model.Status;
import com.example.opentalk.repository.KeyRepository;
import com.example.opentalk.service.KeyService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/manage/key")
@AllArgsConstructor
public class ManageKey {
    private final Gson gson;
    private final KeyService keyService;
    private final PasswordEncoder passwordEncoder;

    private final KeyRepository keyRepository;

    @PostMapping()
    private ResponseEntity<?> InputKeyToDB(@RequestBody KeyDTO keyDTO){
        try {
            Status status = keyService.InputKeyToDB(keyDTO.getKey());
            return ResponseEntity.status(status.getStatus()).body(gson.toJson(status));
        } catch (Status e){
            return ResponseEntity.status(e.getStatus()).body(gson.toJson(e));
        }
    }

    @PostMapping("/check")
    private ResponseEntity<?> CheckKeyToStart(@RequestBody KeyDTO keyDTO){
        try {
            keyService.setKeyRepository(keyRepository);
            keyService.setPasswordEncoder(passwordEncoder);
            Status status = keyService.checkKeyToStart(keyDTO.getKey());
            return ResponseEntity.status(status.getStatus()).body(gson.toJson(status));
        } catch (Status e){
            System.out.println(e);
            return ResponseEntity.status(e.getStatus()).body(gson.toJson(e));
        }
    }
}
