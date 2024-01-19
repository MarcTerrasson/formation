package com.smile.formation.kafka.producer.producer;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class ReservationDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Clé primaire de l'objet dans le SMA")
    private Long idReservation;

    private String noCommande;

    private Float tarifPaye;

    private String statutReservation;

    private OffsetDateTime dateDebutReservation;

    private OffsetDateTime dateFinReservation;

    private OffsetDateTime dateDebutPayee;

    private OffsetDateTime dateFinPayee;

    private String optionReservation;

    @Hidden
    private Long idClient;

    @Hidden
    private Long idConducteur;

    private String codePoche;
}
