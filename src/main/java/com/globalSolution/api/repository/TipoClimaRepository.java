package com.globalSolution.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.globalSolution.api.models.TipoClima;

public interface TipoClimaRepository extends JpaRepository<TipoClima, Long>{
    Page<TipoClima> findByNameContaining(String clima, Pageable pageable);    

    public Iterable<TipoClima> findByLike(String tipoClima) {
        String jpql = "SELECT d FROM TB_TIPO_CLIMA d WHERE d.tipoClima LIKE :tipoClima";
        var query = entityManager.createQuery(jpql, TipoClima.class)
                .setParameter("tipoClima", "%" + tipoClima + "%")
                .setHint("jakarta.persistence.query.timeout", 60000);
        var tipoClima = query.getResultList();
        return tipoClima;
    }
}
