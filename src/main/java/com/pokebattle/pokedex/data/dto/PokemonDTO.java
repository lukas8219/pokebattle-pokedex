package com.pokebattle.pokedex.data.dto;

import com.pokebattle.pokedex.data.enumeration.PokemonTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PokemonDTO {

    private Long id;
    private String name;
    private Integer healthPoints;
    private Integer attack;
    private Integer defense;
    private Integer specialAttack;
    private Integer specialDefense;
    private Integer speed;
    private String frontSprite;
    private String backSprite;
    private List<PokemonEvolutionDTO> evolutions;
    private List<PokemonTypeEnum> types;

}
