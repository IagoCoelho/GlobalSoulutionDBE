package com.globalSolution.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.globalSolution.api.models.Grao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public interface GraoRepository extends JpaRepository<Grao, Long>{

    private EntityManager entityManager;

    public CustomerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    Page<Grao> findAll(Pageable pageable);

    public default void createJPQL(Grao gra) {

        try {

            entityManager.getTransaction().begin();

            entityManager.persist(gra);

            entityManager.getTransaction().commit();

        } catch (Exception e) {

            entityManager.getTransaction().rollback();

            throw e;

        }

    }

    Page<Grao> findByNameContaining(Grao grao, Pageable pageable);

    public default List<Grao> findByLikeJPQL(String grao) {
        String jpql = "SELECT d FROM TB_GRAO d WHERE d.grao LIKE :grao";
        TypedQuery<Grao> query = entityManager.createQuery(jpql, Grao.class)
                .setParameter("grao", "%" + grao + "%")
                .setHint("jakarta.persistence.query.timeout", 60000);
        List<Grao> graos = query.getResultList();
        return graos;
    }

    public default void updateJPQL(Grao gra) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(gra);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public default void deleteJPQL(Grao gra) {
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(gra);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public default Grao findByIdJPQL(int id) {
        Grao gra = entityManager.find(Grao.class, id);
        if (gra == null) {
            return null;
        }
        return gra;
    }

    public default void deleteByIdJPQL(int id) {
        entityManager.getTransaction().begin();
        try {
            Grao gra = entityManager.find(Grao.class, id);
            if (gra != null) {
                entityManager.remove(gra);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}
