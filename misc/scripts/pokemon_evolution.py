import scriptUtils


f = open("V3__evolutions.sql", "w")

for id in range(1, 1119):
    list = scriptUtils.getPokemonSpeciesById(id)
    for evo in list:
        line = scriptUtils.generateScriptLineForEvolutionChain(id, evo)
        f.write(line)


f.write("COMMIT;")
