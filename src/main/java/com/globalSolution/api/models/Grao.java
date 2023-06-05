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
@Table(name = "TB_GRAO")
public class Grao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id_grao;
    @NotBlank
    @Size (max = 255)
    private String nm_grao;
    @NotBlank
    @Size (max = 255)
    private String ds_grao;
    @OneToMany(mappedBy = "grao")
    private List<Cultivos> cultivos;

}
