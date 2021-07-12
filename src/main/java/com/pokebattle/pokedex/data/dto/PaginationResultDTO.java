package com.pokebattle.pokedex.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginationResultDTO<T> {

    private List<T> data;
    private Integer page;
    private Integer pageSize;
    private String sortedBy;

}
