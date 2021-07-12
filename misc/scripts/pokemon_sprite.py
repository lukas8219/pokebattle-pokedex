import scriptUtils

pokemon = scriptUtils.sendRequest("https://pokeapi.co/api/v2/pokemon/3")

spriteUrl = pokemon['sprites']['back_default']

base64 = scriptUtils.getSpringToBase64(spriteUrl)

print(base64)