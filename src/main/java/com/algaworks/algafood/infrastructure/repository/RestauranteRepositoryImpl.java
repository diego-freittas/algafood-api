package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    //@Lazy para não ter uma referencia circular na inicialização dos objetos pelo Spring
    private RestauranteRepository restauranteRepository;

    public List<Restaurante> findJPQL(String nome,
                                  BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        var jpql = new StringBuilder();
        jpql.append("from Restaurante where 0=0 ");
        var parametro = new HashMap<String, Object>();

        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametro.put("nome", "%" + nome + "%");
        }
        if (taxaFreteInicial != null) {
        jpql.append("and taxaFrete >= :taxaInicial ");
            parametro.put("taxaInicial", taxaFreteInicial);
        }
        if (taxaFreteInicial != null) {
        jpql.append("and taxaFrete <= :taxaFinal ");
            parametro.put("taxaFinal", taxaFreteFinal);
        }
        TypedQuery<Restaurante>  query = manager.createQuery(jpql.toString(), Restaurante.class);

        parametro.forEach((chave,valor)-> query.setParameter(chave,valor));

        return query.getResultList();
    }

    public List<Restaurante> find(String nome,
                                      BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder builder = null;
        CriteriaQuery <Restaurante> criteria = builder.createQuery(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        // From nome tabela
        Root<Restaurante> root = criteria.from(Restaurante.class); // equivale a "from Restaurante"

        if (StringUtils.hasText(nome)){
            // like por nome
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }
        if (StringUtils.hasText(nome)){
            //maior ou igual
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }
        if (StringUtils.hasText(nome)){
            //menor ou igual
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }
        criteria.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(criteria).getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis()
                .and(RestauranteSpecs.comNomeSemelhante(nome)));
    }


}
