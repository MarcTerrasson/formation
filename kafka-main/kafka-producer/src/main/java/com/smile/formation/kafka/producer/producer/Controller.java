package com.smile.formation.kafka.producer.producer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "Producer", description = "API pour générer des message sur Kafka")
@RequestMapping("/producer")
@AllArgsConstructor
public class Controller {

	private ProducerService service;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reservation")
    @Operation(summary = "Post une reservation dans kafka", tags = {"Reservation"})
	public void create(@RequestBody ReservationDTO dto) {
		service.create(dto);
	}

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/abonnement")
    @Operation(summary = "Post un abonnement dans kafka", tags = {"Abonnement"})
	public void create(@RequestBody AbonnementDTO dto) {
		service.create(dto);
	}

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/abonnement/{idAbo}")
    @Operation(summary = "Put un abonnement dans kafka", tags = {"Abonnement"})
	public void update(@RequestParam String idAbo, @RequestBody ReservationDTO dto) {
		service.update(dto);
	}

}
