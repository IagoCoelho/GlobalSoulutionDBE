package com.globalSolution.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
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
    
    protected TipoSolo(){

    }

    public TipoSolo(Long id_tipo_solo, String nm_tipo_cor_solo, String nm_tipo_solo){
        this.id_tipo_solo = id_tipo_solo;
        this.nm_tipo_cor_solo = nm_tipo_cor_solo;
        this.nm_tipo_solo = nm_tipo_solo;
    }

    public Long getId_tipo_solo() {
        return id_tipo_solo;
    }
    public void setId_tipo_solo(Long id_tipo_solo) {
        this.id_tipo_solo = id_tipo_solo;
    }
    public String getNm_tipo_cor_solo() {
        return nm_tipo_cor_solo;
    }
    public void setNm_tipo_cor_solo(String nm_tipo_cor_solo) {
        this.nm_tipo_cor_solo = nm_tipo_cor_solo;
    }
    public String getNm_tipo_solo() {
        return nm_tipo_solo;
    }
    public void setNm_tipo_solo(String nm_tipo_solo) {
        this.nm_tipo_solo = nm_tipo_solo;
    }
}
