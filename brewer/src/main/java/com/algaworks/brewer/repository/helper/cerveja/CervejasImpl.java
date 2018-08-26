package com.algaworks.brewer.repository.helper.cerveja;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CervejasImpl implements CervejasQueries {

    @PersistenceContext
    private EntityManager manager;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Cerveja> query = builder.createQuery(Cerveja.class);
        Root<Cerveja> cervejaEntity = query.from(Cerveja.class);

        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

        Predicate[] filtros = adicionarFiltro(filtro, cervejaEntity);

        query.select(cervejaEntity).where(filtros);

        TypedQuery<Cerveja> typedQuery =  manager.createQuery(query);

        typedQuery.setFirstResult(primeiroRegistro);
        typedQuery.setMaxResults(totalRegistrosPorPagina);

        return typedQuery.getResultList();
    }

    private Predicate[] adicionarFiltro(CervejaFilter filtro, Root<Cerveja> cervejaEntity) {
        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        if (filtro != null) {
            if (!StringUtils.isEmpty(filtro.getSku())) {
                predicateList.add(builder.equal(cervejaEntity.get("sku"), filtro.getSku()));
            }

            if (!StringUtils.isEmpty(filtro.getNome())) {
                predicateList.add(builder.like(cervejaEntity.get("nome"), "%" + filtro.getNome() + "%"));
            }

            if (isEstiloPresente(filtro)) {
                predicateList.add(builder.equal(cervejaEntity.get("estilo"), filtro.getEstilo()));
            }

            if (filtro.getSabor() != null) {
                predicateList.add(builder.equal(cervejaEntity.get("sabor"), filtro.getSabor()));
            }

            if (filtro.getOrigem() != null) {
                predicateList.add(builder.equal(cervejaEntity.get("origem"), filtro.getOrigem()));
            }

            if (filtro.getValorDe() != null) {
                predicateList.add(builder.ge(cervejaEntity.get("valor"), filtro.getValorDe()));
            }

            if (filtro.getValorAte() != null) {
                predicateList.add(builder.le(cervejaEntity.get("valor"), filtro.getValorAte()));
            }
        }

        Predicate[] predArray = new Predicate[predicateList.size()];
        return predicateList.toArray(predArray);
    }

    private boolean isEstiloPresente(CervejaFilter filtro) {
        return filtro.getEstilo() != null && filtro.getEstilo().getCodigo() != null;
    }
}
