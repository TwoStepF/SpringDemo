package com.example.opentalk.service;

import com.example.opentalk.entity.KeyStorage;
import com.example.opentalk.model.KeyModel;
import com.example.opentalk.model.Status;
import com.example.opentalk.repository.KeyRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Session;
import javax.persistence.Cacheable;
import java.security.Key;



@Service
public class KeyService {

     private PasswordEncoder passwordEncoder;

     private KeyRepository keyRepository;

     public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
          this.passwordEncoder = passwordEncoder;
     }

     public void setKeyRepository(KeyRepository keyRepository) {
          this.keyRepository = keyRepository;
     }

     public Status InputKeyToDB(String k) throws Status {
          try {
               KeyStorage keyStorage = new KeyStorage();
               keyStorage.setHashKey(passwordEncoder.encode(k));
               keyRepository.deleteAll();
               keyRepository.save(keyStorage);
               return new Status(HttpStatus.OK, "oke");
          }catch (Exception e){
               throw new Status(HttpStatus.INTERNAL_SERVER_ERROR, "lỗi");
          }
     }

     public Status checkKeyToStart(String k) throws Status {
          try {
               String hashKey = keyRepository.get();
               if(passwordEncoder.matches(k, hashKey)){
                    KeyModel.key = k;
                    KeyModel.keyBit = new SecretKeySpec(KeyModel.key.getBytes(), "AES");
                    KeyModel.cipher = Cipher.getInstance("AES");
                    return new Status(HttpStatus.OK, "oke");
               }
               return new Status(HttpStatus.BAD_REQUEST, "Error");
          }catch (Exception e){
               System.out.println(e);
               return new Status(HttpStatus.INTERNAL_SERVER_ERROR, "Error");
          }
     }
}
