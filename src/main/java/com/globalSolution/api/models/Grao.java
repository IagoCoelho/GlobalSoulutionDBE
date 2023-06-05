package com.globalSolution.api.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public Grao(Long id_grao, String nm_grao, String ds_grao){
        this.id_grao = id_grao;
        this.nm_grao = nm_grao;
        this.ds_grao = ds_grao;
    }
    
    public Long getId_grao() {
        return id_grao;
    }
    public void setId_grao(Long id_grao) {
        this.id_grao = id_grao;
    }
    public String getNm_grao() {
        return nm_grao;
    }
    public void setNm_grao(String nm_grao) {
        this.nm_grao = nm_grao;
    }
    public String getDs_grao() {
        return ds_grao;
    }
    public void setDs_grao(String ds_grao) {
        this.ds_grao = ds_grao;
    }
}
