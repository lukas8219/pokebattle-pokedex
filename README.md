# PokeBattle - PokeDex Project

This projects mimics a PokeDex backed by PokeApi.co data.

To run this image with docker use:

```bash
docker run lukas8219/pokedex:latest
```

### Technologies used:
- SpringBoot Web
- Lombok 
- MapStruct
- HATEOAS
- Annotation-based Bean Validation
- Flyway Migrations
- Hibernate/JPA
- In Memory Database with H2 for faster queries.
- Python Scripts to fetch PokeApi data
- Google JIB for containerization and Github Actions

# Endpoints

**GET** -> **v1/pokedex/{id}** 

Response:

```javascript
{
    "id": number,
    "name": string
    "healthPoints": number,
    "attack": number,
    "defense": number,
    "specialAttack": number,
    "specialDefense": number,
    "speed": numb,
    "frontSprite": --base64 encoded sprite--,
    "backSprite": --base64 encoded sprite--,
    "evolutions": array of PokemonMinimal
    "types": array of Type,
    "_links": {
        "self": {
            "href": "http://localhost:8080/v1/pokedex/{id}"
        }
    }
}
```

**GET** -> **v1/pokedex/?**

Response:
```javascript
{
    "data": array of PokemonMinimal,
    "page": number,
    "pageSize": number,
    "sortedBy": string | accepted parameters = [any of Pokemon resource except Evolution, Types and Sprites]
}
```


RESOURCES: 

PokemonMinimal:
```javascript
{
    "id": number,
    "name": string,
    "links": [
    {
      "rel": "self",
      "href": "http://localhost:8080/v1/pokedex/{id}"
    }
  ]
}
```


Types:

    GRASS,
    POISON,
    FIRE,
    FLYING,
    WATER,
    BUG,
    NORMAL,
    ELECTRIC,
    GROUND,
    FAIRY,
    FIGHTING,
    PSYCHIC,
    ROCK,
    STEEL,
    ICE,
    GHOST,
    DRAGON,
    DARK
