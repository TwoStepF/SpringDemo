package com.example.opentalk.repository;

import com.example.opentalk.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status getStatusById(Long id);
}
