package com.pokebattle.pokedex.mapper;

import com.pokebattle.pokedex.data.domain.Pokemon;
import com.pokebattle.pokedex.data.dto.PokemonDTO;
import com.pokebattle.pokedex.data.dto.PokemonEvolutionDTO;
import com.pokebattle.pokedex.data.dto.PokemonSearchDTO;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class PokemonMapper {

    @Autowired
    private EntityLinks entityLinks;

    @Mappings({
            @Mapping(target = "evolutions", ignore = true),
    })
    public abstract PokemonDTO toDTO(Pokemon source);

    public abstract PokemonEvolutionDTO toEvolutionDTO(Pokemon source);

    public EntityModel<PokemonDTO> toModel(Pokemon pokemon){
        var result = toDTO(pokemon);
        var evolutions = pokemon.getEvolvesTo()
                .stream()
                .map(this::toEvolutionDTO)
                .map(EntityModel::of)
                .peek(evo -> addSelfRef(evo, evo.getContent().getId()))
                .collect(Collectors.toList());
        result.setEvolutions(evolutions);

        var model = EntityModel.of(result);
        addSelfRef(model, result.getId());

        return model;
    }

    private void addSelfRef(EntityModel<?> model, Number id){
        model.add(
                entityLinks.linkToItemResource(Pokemon.class, id).withSelfRel()
        );
    }

    public List<EntityModel<PokemonSearchDTO>> toModel(List<PokemonSearchDTO> pokemonList){
        return pokemonList.stream()
                .map(EntityModel::of)
                .peek(pokemon -> addSelfRef(pokemon, pokemon.getContent().getId()))
                .collect(Collectors.toList());
    }
}
