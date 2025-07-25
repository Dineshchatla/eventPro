package com.springs.starter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springs.starter.entities.EventTable;

import java.util.List;

public interface EventRepository extends JpaRepository<EventTable, Integer> {
    List<EventTable> findByEventStatus(String status);
    EventTable findByOrganizationCode(String organizationCode);
    Long countByEventStatus(String status);

    List<EventTable> findByOrganizationCodeAndEventStatus(String organizationCode, String status);
}
