package com.ict.traveljoy.pushalarm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PushAlarmSendRepository extends JpaRepository<PushAlarmSend, Long>{

	List<PushAlarmSend> findAllByReceiver_Id(Long id);


}
