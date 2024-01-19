package com.smile.formation.kafka.consumer.logger;

import org.springframework.transaction.annotation.Transactional;

import com.smile.formation.kafka.schema.OrdreAbonnementAvro;
import com.smile.formation.kafka.schema.OrdreReservationAvro;

public interface OrdreService {

	@Transactional
	void create(OrdreAbonnementAvro ordre);
	
	@Transactional
	void update(OrdreAbonnementAvro ordre);

	@Transactional
	void delete(OrdreAbonnementAvro ordre);
	
	@Transactional
	void create(OrdreReservationAvro ordre);

	@Transactional
	void update(OrdreReservationAvro ordre);

	@Transactional
	void delete(OrdreReservationAvro ordre);
}
