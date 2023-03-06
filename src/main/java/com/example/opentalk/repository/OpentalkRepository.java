package com.example.opentalk.repository;


import com.example.opentalk.entity.Opentalk_topic;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OpentalkRepository extends JpaRepository<Opentalk_topic, Long> {
    //fix
    @Query(value = "select * from opentalk_topic where ((:id is null) or (id = :id)) " +
            "and ((:time is null) or (time = :time))" +
            "and status_id != :id", nativeQuery = true)
    Opentalk_topic findOpentalk(Date time, Long id);

    @Query(value = "select o from Opentalk_topic as o where o.id = :id")
    Opentalk_topic findOpentalkByID(Long id);

    @Query(value = "select o from Opentalk_topic as o")
    List<Opentalk_topic> getAll();


    @CacheEvict(value="opentalk", allEntries=true)
    Opentalk_topic save(Opentalk_topic opentalk_topic);

    @Query(value = "select * from opentalk_topic as op join employee as e on op.employee_id = e.id " +
            "where ((:owner is null) or (e.name LIKE %:owner%))" +
            "and ((:statusID is null) or (status_id = :statusID))" +
            "and ((:time_start is null) or (time >= :time_start))" +
            "and ((:time_last is null) or (time <= :time_last))" +
            "and ((:company is null) or (e.company_id = :company))", nativeQuery = true)
    @Cacheable("opentalk")
    Page<Opentalk_topic> filterOpentalk(Pageable page, String owner, Long statusID, Date time_start, Date time_last, String company);
}
