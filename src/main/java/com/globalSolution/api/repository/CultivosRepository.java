package com.globalSolution.api.repository;

public class CultivosRepository {
    Page<Cultivos> findById(Int id, Pageable pageable);

}
