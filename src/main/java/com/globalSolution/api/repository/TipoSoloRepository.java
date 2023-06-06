package com.globalSolution.api.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.globalSolution.api.models.TipoSolo;

public interface TipoSoloRepository extends JpaRepository<TipoSolo, Long>{
    Page<TipoSolo> findByNameContaining(String solo, Pageable pageable);

    public Iterable<TipoSolo> findByLike(String tipoSolo) {
        String jpql = "SELECT d FROM TB_TIPO_SOLO d WHERE d.tipoSolo LIKE :tipoSolo";
        var query = entityManager.createQuery(jpql, TipoSolo.class)
                .setParameter("tipoSolo", "%" + tipoSolo + "%")
                .setHint("jakarta.persistence.query.timeout", 60000);
        var tipoSolo = query.getResultList();
        return tipoSolo;
    }

}
