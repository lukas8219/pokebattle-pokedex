package com.pokebattle.pokedex.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.EntityModel;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class PokemonSearchDTO {

    private Long id;
    private String name;

}
