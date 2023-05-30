package com.globalSolution.api.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.globalSolution.api.models.TipoSolo;

public interface TipoSoloRepository extends JpaRepository<TipoSolo, Long>{
    Page<TipoSolo> findByNameContaining(String solo, Pageable pageable);
}
