package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl {

    @PersistenceContext
    private EntityManager manager;

    public List<Restaurante> find(String nome,
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

}
