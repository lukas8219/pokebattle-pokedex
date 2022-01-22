package com.pokebattle.pokedex.decorator;

import com.pokebattle.pokedex.data.dto.PaginationResultDTO;
import com.pokebattle.pokedex.data.dto.PokemonSearchDTO;
import com.pokebattle.pokedex.data.dto.SearchParametersDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class PaginationDecorator {

    public SearchParametersDTO from(Integer pageNumber, Integer pageSize, String sortBy, List<String> parameters){
        var dto = new SearchParametersDTO();
        var number = pageNumber != null ? pageNumber : 0;
        var size = pageSize != null ? pageSize : 5;
        var sort = sortBy != null ? sortBy : "id";
        dto.setPageNumber(number);
        dto.setPageSize(size);
        dto.setSortBy(sort);
        List<String> param = parameters != null ? parameters : Collections.emptyList();
        dto.setParameters(param);
        return dto;
    }

    public <T> PaginationResultDTO<T> toPaginationResult(SearchParametersDTO pagination, List<EntityModel<T>> searchResult) {
        PaginationResultDTO<T> result = new PaginationResultDTO<>();
        result.setData(searchResult);
        result.setPageSize(searchResult.size());
        result.setPage(pagination.getPageNumber());
        result.setSortedBy(pagination.getSortBy());
        return result;
    }
}
