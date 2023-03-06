package com.example.opentalk.service;

import com.example.opentalk.model.KeyModel;
import org.springframework.stereotype.Service;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class AttributeEncryptor implements AttributeConverter<String, String> {



    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            KeyModel.cipher.init(Cipher.ENCRYPT_MODE, KeyModel.keyBit);
            return Base64.getEncoder().encodeToString(KeyModel.cipher.doFinal(attribute.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            KeyModel.cipher.init(Cipher.DECRYPT_MODE, KeyModel.keyBit);
            return new String(KeyModel.cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalStateException(e);
        }
    }
}