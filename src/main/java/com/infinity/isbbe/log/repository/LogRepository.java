package com.infinity.isbbe.log.repository;

import com.infinity.isbbe.log.aggregate.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}
