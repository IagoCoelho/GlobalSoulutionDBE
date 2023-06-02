package com.globalSolution.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class Cultivos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private String Grao grao;

    @NotNull
    @ManyToOne
    private String TipoClima tipoClima;

    @NotNull
    @ManyToOne
    private String TipoSolo tipoSolo;
}
