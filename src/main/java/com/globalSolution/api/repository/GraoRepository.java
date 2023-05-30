package com.globalSolution.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.globalSolution.api.models.Grao;

public interface GraoRepository extends JpaRepository<Grao, Long>{
    Page<Grao> findByNameContaining(String grao, Pageable pageable);
}
