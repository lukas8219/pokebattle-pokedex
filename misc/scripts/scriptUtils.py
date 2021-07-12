import re
import requests
import base64

def getIdFromUrl(url):
    id = re.search('([\/]{1}[\d]+[\/]{0,1})', url)[0].replace("/", '')
    return id

def formatName(name):
    char = name[0:1].upper()
    leftOver = name[1:]
    return "{}{}".format(char, leftOver)

def sendRequest(url):
    response = requests.get(url=url)

    if(response.status_code == 200):
        return response.json()
    else :
        raise Exception("Could not reach URL : "+url)

def getAbilitiesByPokemonId(pokemonId):
    pokemonApiUrl = "https://pokeapi.co/api/v2/pokemon/{}".format(pokemonId)
    abilityObject = sendRequest(pokemonApiUrl)['abilities']

    abilityArray = []

    for ability in abilityObject:
        result = getAbility(getIdFromUrl(ability['ability']['url']))
        abilityArray.append(result)

    return abilityArray


def getAbility(abilityId):
    url = "https://pokeapi.co/api/v2/ability/{}".format(abilityId)
    abilitiesJson = sendRequest(url)
    abiltyName = abilitiesJson['name']
    pokemonUsing = []

    for pokemon in abilitiesJson['pokemon']:
        pokemonId = getIdFromUrl(pokemon['pokemon']['url'])
        pokemonUsing.append(pokemonId)

    return {
        "id": abilityId,
        "name" : abiltyName,
        "pokemons" : pokemonUsing
    }

def getTypeById(typeId):
    pokemonApiUrl = "https://pokeapi.co/api/v2/type/{}".format(typeId)
    response = sendRequest(pokemonApiUrl)
    pokemons = response['pokemon']
    typeName = response['name']
    pokemonsUsing = []

    for users in pokemons:
        url = users['pokemon']['url']
        pokemonId = getIdFromUrl(url)
        pokemonsUsing.append(pokemonId)

    return {
        "id":typeId,
        "name":typeName,
        "pokemons":pokemonsUsing
    }


def getTypesByPokemonId(pokemonId):
    pokemonApiUrl = "https://pokeapi.co/api/v2/pokemon/{}".format(pokemonId)
    typesObject = sendRequest(pokemonApiUrl)['types']

    typeArray = []

    for type in typesObject:
        url = type['type']['url']
        typeId = getIdFromUrl(url)
        result = getTypeById(typeId)
        typeArray.append(result)

    return typeArray

def getStats(stats):
    statsObject = {
        "hp": 0,
        "attack": 0,
        "special-attack": 0,
        "defense": 0,
        "special-defense": 0,
        "speed": 0
    }
    for stat in stats:
        label = stat['stat']['name']
        value = stat['base_stat']
        statsObject[label] = value

    return statsObject

def getPokemonById(id):
    url = "https://pokeapi.co/api/v2/pokemon/{}".format(id)
    pokemon = sendRequest(url)
    height = pokemon['height']
    stats = getStats(pokemon['stats'])
    weight = pokemon['weight']
    name = formatName(pokemon['name'])
    backSprite = pokemon['sprites']['back_default']
    frontSprite = pokemon['sprites']['front_default']
    return {
        "id":id,
        "name": name,
        "height": height,
        "weight": weight,
        "stats": stats,
        "sprites": {
            "back": backSprite,
            "front": frontSprite
        }
    }


def generateScriptLine(pokemon):
    
    line = "\n"
    line += "INSERT INTO pokedex.pokemons(id, height, weight, name, health_points, attack, special_attack, defense, special_defense, speed, front_sprite, back_sprite)"
    line += " VALUES({},".format(pokemon['id'])
    line += "{},".format(pokemon['height'])
    line += "{},".format(pokemon['weight'])
    line += "\"{}\",".format(pokemon['name'])
    line += "{},".format(pokemon['stats']['hp'])
    line += "{},".format(pokemon['stats']['attack'])
    line += "{},".format(pokemon['stats']['special-attack'])
    line += "{},".format(pokemon['stats']['defense'])
    line += "{},".format(pokemon['stats']['speed'])
    line += "{},".format(pokemon['stats']['special-defense'])
    base64 = getSpringToBase64(pokemon['sprites']['front'])
    line += "{},".format(base64)
    base64 = getSpringToBase64(pokemon['sprites']['back'])
    line += "{});".format(base64)
    line += "\n"

    return line


def getSpringToBase64(spriteUrl):
    
    if(spriteUrl):
        response = requests.get(url=spriteUrl)

        f = open("image.png", "wb")
        f.write(response.content)

        f = open("image.png", "rb")
        f = f.read()

        encoded = base64.encodebytes(f)

        return "\"{}\"".format(encoded.decode("ascii").replace('\n', ''))
    
    return "NULL"

def getSpeciesIdFromObject(species):
    return getIdFromUrl(species['url'])

def getAllEvolutions(chainId, pokemonId):
    url = "https://pokeapi.co/api/v2/evolution-chain/{}".format(chainId)
    chainPayload = sendRequest(url)
    chainObject = chainPayload['chain']
    result = []
    if(int(getSpeciesIdFromObject(chainObject['species'])) != int(pokemonId)):
        result.append(getSpeciesIdFromObject(chainObject['species']))
    
    chainObject = chainObject['evolves_to']

    while(chainObject):
        for curr in chainObject:
            if(int(getSpeciesIdFromObject(curr['species'])) != int(pokemonId)):
                result.append(getSpeciesIdFromObject(curr['species']))
        chainObject = chainObject[0]['evolves_to']
                
    return result


def getPokemonSpeciesById(id):
    url = "https://pokeapi.co/api/v2/pokemon-species/{}".format(id)
    speciesPayload = sendRequest(url)
    chainId = getIdFromUrl(speciesPayload['evolution_chain']['url'])
    evolutions = getAllEvolutions(chainId, id)
    return evolutions


def generateScriptLineForEvolutionChain(pokeId, to):
    line = "\n"
    line += "INSERT INTO pokedex.evolutions(pokemon_id, evolves_to) VALUES("
    line += "{}, ".format(pokeId)
    line += "{});".format(to)
    line += "\n"
    return line

def generateScriptLineForType(name, id):
    line = "\n"
    line += "INSERT INTO pokedex.pokemon_types(pokemon_id, type) VALUE("
    line += "{},".format(id)
    line += "\"{}\");".format(name)
    line += "\n"
    return line