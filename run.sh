#!/bin/bash
source gradle.properties
echo $version
sed -ie "s/{{VERSION}}/$version/g" ./deploy/deployment.yaml