package com.pokebattle.pokedex.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchParametersDTO {

    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private List<String> parameters;
}
