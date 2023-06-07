package com.globalSolution.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.globalSolution.api.models.Cultivos;

public interface CultivosRepository extends JpaRepository<Cultivos, Long>{
    Page<Cultivos> findByIdPageable(String clima, Pageable pageable);    

}
