package com.globalSolution.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeeder implements CommandLineRunner{
    
    @Autowired
    TiposClimaRepository tipoClimaRepository;
    TiposSoloRepository tipoSoloRepository;
    GraoRepository graoRepository;
    CultivosRepository cultivosRepository;

    @Override
    public void run(String... args) throws Exception {
        documentosRepository.saveAll(List.of(
            new Documentos(1L, 543345543345, "x", "Registro geral", "Emitido,dia,:,8"),
            new Documentos(1L, 768876678876, "y", "Registro geral", "Emitido,dia,:,9")

        ))
        categoriasRepository.saveAll(List.of(
            new Categorias(1L, 1, "white", "Certificados", getDocId(1L,2L)),
            new Categorias(1L, 1, "white", "Certificados", getDocId(1L,2L)),
            new Categorias(1L, 1, "white", "Certificados", getDocId(1L,2L))
        ));
        
    }
}
