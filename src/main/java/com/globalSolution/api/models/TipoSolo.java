package com.globalSolution.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "TB_TIPO_SOLO")
public class TipoSolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id_tipo_solo;

    @NotBlank
    @Size(max = 255 )
    private String nm_tipo_cor_solo;

    @NotBlank
    @Size(max = 255)
    private String nm_tipo_solo;

    @OneToMany(mappedBy = "tipoSolo")
    private List<Cultivos> cultivos;
    
}
