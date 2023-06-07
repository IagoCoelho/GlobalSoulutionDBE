package com.globalSolution.api.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.globalSolution.api.models.TipoSolo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public interface TipoSoloRepository extends JpaRepository<TipoSolo, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    Page<TipoSolo> findAll(org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable pageable);


    public TipoSoloRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public default void createJPQL(TipoSolo tipoSolo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tipoSolo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    Page<TipoSolo> findByNameContaining(TipoSolo tipoSolo, org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable pageable);

    public default Iterable<TipoSolo> findByLikeJPQL(String tipoSolo) {
        String jpql = "SELECT d FROM TB_TIPO_SOLO d WHERE d.TIPO_SOLO LIKE :tipoSolo";
        var query = entityManager.createQuery(jpql, TipoSolo.class)
            .setParameter("tipoSolo", "%" + tipoSolo + "%")
            .setHint("jakarta.persistence.query.timeout", 60000);
        var tiposSolo = query.getResultList();
        return tiposSolo;
    }

    public default Iterable<TipoSolo> read() {

        String jpql = "SELECT g FROM TipoSolo g";

        var query = entityManager.createQuery(jpql, TipoSolo.class)
            .setHint("jakarta.persistence.query.timeout", 60000);
        var tiposSolo = query.getResultList();
        return tiposSolo;
    }

    public default void updateJPQL(TipoSolo tipoSolo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tipoSolo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public default void deleteJPQL(TipoSolo tipoSolo) {

        entityManager.getTransaction().begin();

        try {

            entityManager.remove(tipoSolo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public default TipoSolo findByIdJPQL(int id) {
        TipoSolo tipoSolo = entityManager.find(TipoSolo.class, id);
        if (tipoSolo == null) {
            return null;
        }
        return tipoSolo;
    }

    public default void deleteByIdJPQL(int id) {
        entityManager.getTransaction().begin();
        try {
            TipoSolo tipoSolo = entityManager.find(TipoSolo.class, id);

            if (tipoSolo != null) {
                entityManager.remove(tipoSolo);
            }

            entityManager.getTransaction().commit();

        } catch (Exception e) {

            entityManager.getTransaction().rollback();

            throw e;

        }

    }

}