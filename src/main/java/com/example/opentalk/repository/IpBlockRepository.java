package com.example.opentalk.repository;

import com.example.opentalk.entity.IpBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpBlockRepository extends JpaRepository<IpBlock, String> {
    @Query(value = "select * from Ip_block", nativeQuery = true)
    IpBlock findIpBlockByID(String id);
}
