package com.globalSolution.api.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.globalSolution.api.models.TipoClima;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public interface TipoClimaRepository extends JpaRepository<TipoClima, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    Page<TipoClima> findAll(org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable pageable);


    public TipoClimaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public default void createJPQL(TipoClima tipoClima) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tipoClima);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    Page<TipoClima> findByNameContaining(TipoClima tipoClima, org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable pageable);

    public default Iterable<TipoClima> findByLikeJPQL(String tipoClima) {
        String jpql = "SELECT d FROM TB_TIPO_CLIMA d WHERE d.TIPO_CLIMA LIKE :tipoClima";
        var query = entityManager.createQuery(jpql, TipoClima.class)
            .setParameter("tipoClima", "%" + tipoClima + "%")
            .setHint("jakarta.persistence.query.timeout", 60000);
        var tiposClima = query.getResultList();
        return tiposClima;
    }

    public default Iterable<TipoClima> read() {

        String jpql = "SELECT g FROM TipoClima g";

        var query = entityManager.createQuery(jpql, TipoClima.class)
            .setHint("jakarta.persistence.query.timeout", 60000);
        var tipoClima = query.getResultList();
        return tipoClima;
    }

    public default void updateJPQL(TipoClima tipoClima) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tipoClima);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public default void deleteJPQL(TipoClima tipoClima) {

        entityManager.getTransaction().begin();

        try {

            entityManager.remove(tipoClima);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public default TipoClima findByIdJPQL(int id) {
        TipoClima tipoClima = entityManager.find(TipoClima.class, id);
        if (tipoClima == null) {
            return null;
        }
        return tipoClima;
    }

    public default void deleteByIdJPQL(int id) {
        entityManager.getTransaction().begin();
        try {
            TipoClima tipoClima = entityManager.find(TipoClima.class, id);

            if (tipoClima != null) {
                entityManager.remove(tipoClima);
            }

            entityManager.getTransaction().commit();

        } catch (Exception e) {

            entityManager.getTransaction().rollback();

            throw e;

        }

    }

}