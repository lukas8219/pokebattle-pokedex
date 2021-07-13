package com.pokebattle.pokedex.repository;

import com.pokebattle.pokedex.data.domain.Pokemon;
import com.pokebattle.pokedex.data.domain.Pokemon_;
import com.pokebattle.pokedex.data.dto.PokemonSearchDTO;
import com.pokebattle.pokedex.data.dto.SearchParametersDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PokemonRepository {

    private final EntityManager entityManager;

    public Pokemon findById(Long id) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Pokemon.class);
        var from = query.from(Pokemon.class);
        query.select(from);
        query.where(
                criteriaBuilder.equal(from.get(Pokemon_.id), id)
        );
        return entityManager.createQuery(query).getSingleResult();
    }

    public List<PokemonSearchDTO> searchPokemonList(SearchParametersDTO parametersDTO) {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(PokemonSearchDTO.class);
        var from = query.from(Pokemon.class);

        query.multiselect(
                from.get(Pokemon_.ID),
                from.get(Pokemon_.NAME)
        );

        var paginationParameters = parametersDTO.getParameters();
        paginationParameters.forEach(param -> {
            if (param != null) {
                var namePath = from.get(Pokemon_.NAME);
                var parameter = String.format("%s%%", param);
                var predicate = cb.like(namePath.as(String.class), parameter);
                query.where(predicate);
            }
        });

        var sortBy = parametersDTO.getSortBy();
        if (containsField(sortBy)) {
            query.orderBy(List.of(cb.asc(from.get(sortBy))));
        }

        var size = parametersDTO.getPageSize();
        var firstResult = parametersDTO.getPageNumber() * size;

        return entityManager.createQuery(query)
                .setMaxResults(size)
                .setFirstResult(firstResult)
                .getResultList();
    }

    private boolean containsField(String field) {
        return Arrays.stream(Pokemon_.class.getDeclaredFields()).anyMatch(f -> f.getName().equals(field));
    }
}
