package com.pokebattle.pokedex.mapper;

import com.pokebattle.pokedex.data.domain.Pokemon;
import com.pokebattle.pokedex.data.dto.PokemonDTO;
import com.pokebattle.pokedex.data.dto.PokemonEvolutionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PokemonMapper {

    @Mappings({
            @Mapping(target = "evolutions", source = "evolvesTo")
    })
    PokemonDTO toDTO(Pokemon source);

    PokemonEvolutionDTO toEvolutionDTO(Pokemon source);
}
