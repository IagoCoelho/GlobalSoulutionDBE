package com.globalSolution.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TESTEGS-PU");
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            var graoRepository = new GraoRepository(entityManager);
            var tipoSoloRepository = new TipoSoloRepository(entityManager);
            var tipoClimaRepository = new TipoClimaRepository(entityManager);
            var cultivosRepository = new CultivosRepository(entityManager);

            var novoGrao = new Grao("Teste", "RuaTeste", 43434, 434334);
            graoRepository.create(novoGrao);
            var grao = graoRepository.findById(1);

            novoGrao.setName("Teste2");
            graoRepository.update(novoGrao);
            var graoUpdate = graoRepository.findById(1);

            graoRepository.deleteById(1);
            var graoDeleted = graoRepository.findById(1);

            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception e) {
            throw e;
        }
	}

}
