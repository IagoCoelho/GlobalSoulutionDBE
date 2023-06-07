package com.globalSolution.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.globalSolution.api.models.Cultivos;
import com.globalSolution.api.models.Grao;
import com.globalSolution.api.models.TipoClima;
import com.globalSolution.api.models.TipoSolo;
import com.globalSolution.api.repository.CultivosRepository;
import com.globalSolution.api.repository.GraoRepository;
import com.globalSolution.api.repository.TipoClimaRepository;
import com.globalSolution.api.repository.TipoSoloRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {
    
    @Autowired
    TipoClimaRepository tipoClimaRepository;
    
    @Autowired
    TipoSoloRepository tipoSoloRepository;
    
    @Autowired
    GraoRepository graoRepository;
    
    @Autowired
    CultivosRepository cultivosRepository;
    
    @Override
    public void run(String... args) throws Exception {
        Grao g1 = new Grao(1L, " ", " ", null);
        Grao g2 = new Grao(2L, " ", " ", null);
        Grao g3 = new Grao(3L, " ", " ", null);
        graoRepository.saveAll(List.of(g1,g2,g3));
        
        TipoSolo ts1 = new TipoSolo(1L, "vermelho", "Solo com pH ácido", null);
        TipoSolo ts2 = new TipoSolo(2L, "vermelho", "Solo com pH ácido", null);
        TipoSolo ts3 = new TipoSolo(3L, "vermelho", "Solo com pH ácido", null);
        tipoSoloRepository.saveAll(List.of(ts1,ts2,ts3));
        
        TipoClima tc1 = new TipoClima(1L, "Clima tropical úmido", null);
        TipoClima tc2 = new TipoClima(1L, "Clima tropical úmido", null);
        TipoClima tc3 = new TipoClima(2L, "Clima seco e quente", null);
        tipoClimaRepository.saveAll(List.of(tc1,tc2,tc3));
        
        Cultivos.builder().grao(g1).tipoClima(tc1).tipoSolo(ts1);
        Cultivos.builder().grao(g2).tipoClima(tc2).tipoSolo(ts2);
        Cultivos.builder().grao(g3).tipoClima(tc3).tipoSolo(ts3);
        cultivosRepository.saveAll(List.of());
        
    }

}
