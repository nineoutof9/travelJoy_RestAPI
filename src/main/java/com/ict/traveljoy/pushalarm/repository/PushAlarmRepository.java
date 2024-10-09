package com.ict.traveljoy.pushalarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PushAlarmRepository extends JpaRepository<PushAlarm, Long>{

}
