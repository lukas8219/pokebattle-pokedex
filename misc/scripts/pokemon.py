from re import L
import scriptUtils

url = 'https://pokeapi.co/api/v2/pokemon/'

number = scriptUtils.sendRequest(url)

f = open("V2__pokemons.sql", "w")

for n in range(1, number['count']+1):
    pokemon = scriptUtils.getPokemonById(n)
    line = scriptUtils.generateScriptLine(pokemon)
    print("INSERTING LINE FOR POKEMON #{} {}".format(pokemon['id'], pokemon['name']))
    f.write(line)

f.write("COMMIT;")
