package com.example.opentalk.repository;

import com.example.opentalk.entity.KeyStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<KeyStorage, Long> {
    @Query(value = "select hash_key from key_storage", nativeQuery = true)
    String get();
}
