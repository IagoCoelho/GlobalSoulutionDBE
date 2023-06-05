package com.globalSolution.api.repository;

public class CultivosRepository {
    Page<Grao> findByNameContaining(String grao, Pageable pageable);

}
