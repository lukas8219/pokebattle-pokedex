package com.pokebattle.pokedex.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class PokemonSearchDTO {

    private Long id;
    private String name;

}
