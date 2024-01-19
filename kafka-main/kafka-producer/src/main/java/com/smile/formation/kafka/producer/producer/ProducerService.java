package com.smile.formation.kafka.producer.producer;

import org.springframework.transaction.annotation.Transactional;

public interface ProducerService {

	@Transactional
	void create(ReservationDTO dto);

	@Transactional
	void update(ReservationDTO dto);

	@Transactional
	void create(AbonnementDTO dto);
}
