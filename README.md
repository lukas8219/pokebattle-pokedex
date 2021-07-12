# PokeBattle - PokeDex Project



This projects mimics a PokeDex backed by PokeApi.co data.

### Technologies used:
- SpringBoot Web
- Lombok 
- MapStruct
- Bean Validation
- Flyway Migrations
- Hibernate/JPA
- MySQL
- Python Scripts to fetch PokeApi data
- Docker


To run the docker-compose image, be sure that you have Docker and Docker-Compose installed and all permissions set:

`` docker -v `` 
`` docker-compose -v ``


After ensuring you have Docker installed, run :

`` docker-compose ./deploy/docker-compose.yaml up ``

# Endpoints

**v1/pokedex/{id}** -> Returns PokeData, containing id, name, weight, height, stats, base64 encrypted sprites, evolutions and types.
**v1/pokedex/?** -> Returns a pagination of Pokemon, containing ID and name. **Query parameters(name, pageNumber, pageSize and sortBy)**.

