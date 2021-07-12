from re import L
import scriptUtils

url = 'https://pokeapi.co/api/v2/pokemon/'

number = scriptUtils.sendRequest(url)

f = open("debug.sql", "w")

pokemon = scriptUtils.getPokemonById(652)
line = scriptUtils.generateScriptLine(pokemon)
print("INSERTING LINE FOR POKEMON #{} {}".format(pokemon['id'], pokemon['name']))
f.write(line)

f.write("COMMIT;")
