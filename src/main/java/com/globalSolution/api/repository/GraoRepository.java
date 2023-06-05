package com.globalSolution.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.globalSolution.api.models.Grao;

public interface GraoRepository extends JpaRepository<Grao, Long>{
    Page<Grao> findByNameContaining(String grao, Pageable pageable);

    public Iterable<Grao> findByLike(String grao) {
        String jpql = "SELECT d FROM TB_GRAO d WHERE d.grao LIKE :grao";
        var query = entityManager.createQuery(jpql, Grao.class)
                .setParameter("grao", "%" + grao + "%")
                .setHint("jakarta.persistence.query.timeout", 60000);
        var grao = query.getResultList();
        return grao;
    }
}
