package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.repository.CustomJpaReoisutiry;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustonJpaRepositoryImpl<T, Id> extends SimpleJpaRepository<T, Id>
        implements CustomJpaReoisutiry<T, Id> {

    private EntityManager manager;

    public CustonJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.manager = entityManager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
        var jpql = "from " + getDomainClass().getName();
        T entity = manager.createQuery(jpql, getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }
}