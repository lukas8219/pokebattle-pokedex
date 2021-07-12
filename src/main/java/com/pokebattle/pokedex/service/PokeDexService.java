package com.pokebattle.pokedex.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokebattle.pokedex.data.dto.PaginationResultDTO;
import com.pokebattle.pokedex.data.dto.PokemonDTO;
import com.pokebattle.pokedex.data.dto.PokemonSearchDTO;
import com.pokebattle.pokedex.decorator.PaginationDecorator;
import com.pokebattle.pokedex.mapper.PokemonMapper;
import com.pokebattle.pokedex.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PokeDexService {

    private final PokemonRepository pokemonRepository;
    private final PaginationDecorator paginationDecorator;
    private final PokemonMapper pokemonMapper;
    private final ObjectMapper objectMapper;

    public PokemonDTO getPokemon(Long id) {
        var pokemon = pokemonRepository.findById(id);
        return pokemonMapper.toDTO(pokemon);
    }

    public PaginationResultDTO<PokemonSearchDTO> getPokemonList(Integer pageNumber, Integer pageSize, String sortBy, String name) {
        List<String> list = name != null ? List.of(name) : Collections.emptyList();
        var pagination = paginationDecorator.from(pageNumber, pageSize, sortBy, list);
        var pokemonList = pokemonRepository.searchPokemonList(pagination);
        return paginationDecorator.toPaginationResult(pagination, pokemonList);
    }
}
