package com.pokebattle.pokedex.data.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.EntityMode;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@Getter
@Setter
public class PaginationResultDTO<T> {

    private List<EntityModel<T>> data;
    private Integer page;
    private Integer pageSize;
    private String sortedBy;

}
