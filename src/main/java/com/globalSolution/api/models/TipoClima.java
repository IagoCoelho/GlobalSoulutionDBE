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
@Entity
@Table(name = "TB_TIPO_CLIMA")
public class TipoClima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id_clima;

    @NotBlank
    @Size(max = 255)
    private String ds_clima;

    @OneToMany(mappedBy = "tipoClima")
    private List<Cultivos> cultivos;

    protected TipoClima(){
    }

    public TipoClima(Long id_clima, String ds_clima){
        this.id_clima = id_clima;
        this.ds_clima = ds_clima;
    }
    
    public Long getId_clima() {
        return id_clima;
    }
    public void setId_clima(Long id_clima) {
        this.id_clima = id_clima;
    }
    public String getDs_clima() {
        return ds_clima;
    }
    public void setDs_clima(String ds_clima) {
        this.ds_clima = ds_clima;
    }
}
