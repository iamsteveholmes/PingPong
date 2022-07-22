#!/bin/bash

./gradlew jibDockerBuild  &&
docker push iamsteveholmes/pingpong:0.1 &&
kubectl delete -f k8s.yml &&
kubectl apply -f k8s.yml &&
kubectl get pods -n pp
