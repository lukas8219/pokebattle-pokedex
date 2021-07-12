package com.pokebattle.pokedex.api;

import com.pokebattle.pokedex.data.dto.PaginationResultDTO;
import com.pokebattle.pokedex.data.dto.PokemonSearchDTO;
import com.pokebattle.pokedex.data.dto.PokemonDTO;
import com.pokebattle.pokedex.service.PokeDexService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/pokedex/")
@RequiredArgsConstructor
public class PokeDexApi {

    private final PokeDexService service;

    @GetMapping("{id}")
    public PokemonDTO getPokemon(@PathVariable Long id){
        return service.getPokemon(id);
    }

    @GetMapping
    public PaginationResultDTO<PokemonSearchDTO> getPokemonList(@RequestParam(required = false) Integer pageNumber,
                                                                @RequestParam(required = false) Integer pageSize,
                                                                @RequestParam(required = false) String sortBy,
                                                                @RequestParam(required = false) String name){
        return service.getPokemonList(pageNumber, pageSize, sortBy, name);
    }
}
