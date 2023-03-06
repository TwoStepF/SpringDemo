package com.example.opentalk.repository;

import com.example.opentalk.entity.Status;
import com.example.opentalk.entity.TimeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeScheduleRepository extends JpaRepository<TimeSchedule, Long> {
}
