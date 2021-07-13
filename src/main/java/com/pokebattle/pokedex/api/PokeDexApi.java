package com.pokebattle.pokedex.api;

import com.pokebattle.pokedex.data.dto.PaginationResultDTO;
import com.pokebattle.pokedex.data.dto.PokemonDTO;
import com.pokebattle.pokedex.data.dto.PokemonSearchDTO;
import com.pokebattle.pokedex.service.PokeDexService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/v1/pokedex/")
@RequiredArgsConstructor
@Validated
public class PokeDexApi {

    private final PokeDexService service;

    @GetMapping("{id}")
    public PokemonDTO getPokemon(@PathVariable
                                 @Max(value = 898, message = "{pokedex.range}")
                                 @Min(value = 1, message = "{pokedex.range}")
                                         Long id) {
        return service.getPokemon(id);
    }

    @GetMapping
    public PaginationResultDTO<PokemonSearchDTO> getPokemonList(@RequestParam(required = false) Integer pageNumber,
                                                                @RequestParam(required = false) Integer pageSize,
                                                                @RequestParam(required = false) String sortBy,
                                                                @RequestParam(required = false) String name) {
        return service.getPokemonList(pageNumber, pageSize, sortBy, name);
    }
}
