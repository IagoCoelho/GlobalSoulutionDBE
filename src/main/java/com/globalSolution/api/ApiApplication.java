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
            EntityManagerFactory entityManagerFactory = Persistence.
                    createEntityManagerFactory("TESTEGS-PU");
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            var customerRepository = new CustomerRepository(entityManager);
            var tellerRepository = new TellerRepository(entityManager);

            var novoCustomer = new Customer("Teste", "RuaTeste", 43434, 434334);
            customerRepository.create(novoCustomer);
            var customer = customerRepository.findById(1);

            novoCustomer.setName("Teste2");
            customerRepository.update(novoCustomer);
            var customerUpdate = customerRepository.findById(1);

            customerRepository.deleteById(1);
            var customerDeleted = customerRepository.findById(1);

            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception e) {
            throw e;
        }
	}

}
