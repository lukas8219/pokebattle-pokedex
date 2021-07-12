package com.pokebattle.pokedex.data.domain;

import com.pokebattle.pokedex.data.enumeration.PokemonTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pokemons")
public class Pokemon {

    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "height")
    private Integer height;
    @Column(name = "health_points")
    private Integer healthPoints;
    @Column(name = "attack")
    private Integer attack;
    @Column(name = "defense")
    private Integer defense;
    @Column(name = "special_attack")
    private Integer specialAttack;
    @Column(name = "special_defense")
    private Integer specialDefense;
    @Column(name = "speed")
    private Integer speed;
    @Column(name = "back_sprite")
    private String backSprite;
    @Column(name = "front_sprite")
    private String frontSprite;

    @ManyToMany
    @JoinTable(name = "evolutions",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "evolves_to"))
    private Set<Pokemon> evolvesTo;

    @ElementCollection
    @Column(name = "type")
    @CollectionTable(name = "pokemon_types",
            joinColumns = @JoinColumn(name = "pokemon_id"))
    @Enumerated(EnumType.STRING)
    private Set<PokemonTypeEnum> types;

}
