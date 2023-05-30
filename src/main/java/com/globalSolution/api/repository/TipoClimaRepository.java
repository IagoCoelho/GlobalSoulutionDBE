package com.globalSolution.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.globalSolution.api.models.TipoClima;

public interface TipoClimaRepository extends JpaRepository<TipoClima, Long>{
    Page<TipoClima> findByNameContaining(String clima, Pageable pageable);    
}
