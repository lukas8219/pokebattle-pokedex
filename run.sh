#!/bin/bash

./gradlew bootBuildImage --imageName=pokebattle/pokedex

docker-compose -f ./deploy/docker-compose.yaml up