from re import L
import scriptUtils

#19

f = open('V4__pokemon_types.sql', 'w')

for id in range(1, 899):
    pokemon = scriptUtils.sendRequest("https://pokeapi.co/api/v2/pokemon/{}".format(id))
    typeArray = pokemon['types']
    print("INSERTING LINE FOR POKEMON #{}".format(id))
    for type in typeArray:
        name = type['type']['name'].upper()
        line = scriptUtils.generateScriptLineForType(name, id)
        print("TYPE : {}".format(name))
        f.write(line)

f.write("COMMIT;")