package com.pokebattle.pokedex.api;

import com.pokebattle.pokedex.data.domain.Pokemon;
import com.pokebattle.pokedex.data.dto.PaginationResultDTO;
import com.pokebattle.pokedex.data.dto.PokemonDTO;
import com.pokebattle.pokedex.data.dto.PokemonSearchDTO;
import com.pokebattle.pokedex.service.PokeDexService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/v1/pokedex/")
@RequiredArgsConstructor
@Validated
@ExposesResourceFor(Pokemon.class)
public class PokeDexApi {

    private final PokeDexService service;
    private final int MAX_POKEDEX_SIZE = 898;
    private final int MIN_POKEDEX_SIZE = 1;

    @GetMapping("{id}")
    public EntityModel<PokemonDTO> getPokemon(@PathVariable
                                 @Max(value = MAX_POKEDEX_SIZE, message = "{pokedex.range}")
                                 @Min(value = MIN_POKEDEX_SIZE, message = "{pokedex.range}")
                                         final Long id) {
        return service.getPokemon(id);
    }

    @GetMapping
    public PaginationResultDTO<PokemonSearchDTO> getPokemonList(@RequestParam(required = false) final Integer pageNumber,
                                                                @RequestParam(required = false) final Integer pageSize,
                                                                @RequestParam(required = false) final String sortBy,
                                                                @RequestParam(required = false) final String name) {



        return service.getPokemonList(pageNumber, pageSize, sortBy, name);
    }
}
