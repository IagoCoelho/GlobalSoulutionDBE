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
public class DatabaseSeeder implements CommandLineRunner{
    
    @Autowired
    TipoClimaRepository tipoClimaRepository;
    TipoSoloRepository tipoSoloRepository;
    GraoRepository graoRepository;
    CultivosRepository cultivosRepository;

    @Override
    public void run(String... args) throws Exception {
        graoRepository.saveAll(List.of(
            new Grao(1L, "soja", "A soja se desenvolve melhor em climas quentes e temperaturas médias entre 20°C e 30°C. O solo ideal para a soja deve ser bem drenado, fértil e rico em matéria orgânica. A soja pode ser cultivada em uma variedade de tipos de solo.  "),
            new Grao(2L, "Feijao ", "O feijão prefere climas quentes e temperaturas entre 20°C e 30°C. O solo ideal para o plantio de feijão deve ser bem drenado, fértil e rico em matéria orgânica. O pH do solo ideal para o feijão varia entre 6,0 e 7,0. ")
        ));
        tipoSoloRepository.saveAll(List.of(
            new TipoSolo(1L, "vermelho", "Solo com pH ácido"),
            new TipoSolo(2L, "beje", "Solo alagado")
        ));
        tipoClimaRepository.saveAll(List.of(
            new TipoClima(1L, "Clima tropical úmido"),
            new TipoClima(2L, "Clima seco e quente")
        ));
        cultivosRepository.saveAll(List.of(
            new Cultivos(1L, getGraoId, 1L, 1L)
        ));
        
    }
}
