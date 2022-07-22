# Ping Pong Application
The Ping Pong Application is a demonstration of how an application deployed to kubernetes can find and communicate with pods within it's namespace and also to consume and operate on Custom Resources.  This repository was created to build the "server" portion of the app that finds the other pods and communicates with them.  There is a replication operator at this location: [CRD Operator](https://github.com/iamsteveholmes/CRDOperator).  That application is a kubernetes Operator that uses a Custom Resource Definition (CRD) and consumes a Custom Resource that denotes the number of replicas of the pod.  The Operator then calls the Ping Pong server Deployment setting it to scale up or down to the number of replicas defined in the custom resource.

## Deploying the Application to Kubernetes
This app was tested using Docker Desktop and the kubernetes cluster.  Because of the experimental nature of the application, I suggest only installing it to a test cluster.

The two container images used by the application have already been deployed to my local registry and are noted in the configurations.  Therefore no additional assembly is required.

1. Either clone the repository or download the following files:
  - [auth.yml](https://raw.githubusercontent.com/iamsteveholmes/PingPong/master/auth.yml)
  - [k8s.yml](https://raw.githubusercontent.com/iamsteveholmes/PingPong/master/k8s.yml)
  - [instance.yml](https://github.com/iamsteveholmes/PingPong/blob/master/instance.yml)
  
2. Apply the configuration to the test cluster using kubectl:
```
kubectl apply -f auth.yml
kubectl apply -f k8s.yml
```
3. List all your pods in the create `pp` namespace:
```
steve$ kubectl get pods -n pp
NAME                            READY   STATUS    RESTARTS      AGE
crd-operator-696cf7c4cb-vr9s7   1/1     Running   6 (39m ago)   42m
pingpong-69c5dc8c74-7ssmz       1/1     Running   0             32m
pingpong-69c5dc8c74-d6z8k       1/1     Running   0             44m
pingpong-69c5dc8c74-g4mf6       1/1     Running   0             32m
```
4. Output the logs of one of the pingpong pods:
```
kubectl logs pingpong-69c5dc8c74-7ssmz -n pp --follow
```
You can watch the pings and pongs go by in the logs.  Notice that the pings happen when another pod is calling into this pod.  The pongs happen all at once because the requests are made all at once by this pod and the response is logged.

Also note that there will be one less `ping` and `pong` than there are pods.  The reason is that I've included logic for the pods not to make calls to themselves.

5. Apply the Custom Resource instance:
Notice that you may want to alter the `replicas` property in the instance.yml file and apply this multiple times.  You might try scaling up and down to test it.  Then run:
```
kubectl apply -f instance.yml
```
6. Check your pods again as you did in step 3.  Note that you may have to repeat step 4 if you killed the pod you were folling the logs from.
7. Notice the change in the number of messages being sent and received in the logs.
8. Repeat steps 5, 6 and 7 as often as you want.

---
## Technial Implementation
I used two different frameworks and a number of underlying libraries in my attempts.  I've included some of the details in this readme.

---

## Micronaut 3.5.3 Documentation

- [User Guide](https://docs.micronaut.io/3.5.3/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.5.3/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.5.3/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

- [Jib Gradle Plugin](https://plugins.gradle.org/plugin/com.google.cloud.tools.jib)
- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)

## Feature config-kubernetes documentation

- [Micronaut Kubernetes Distributed Configuration documentation](https://micronaut-projects.github.io/micronaut-kubernetes/latest/guide/#config-client)

## Feature kubernetes-informer documentation

- [Micronaut Kubernetes Informer Support documentation](https://micronaut-projects.github.io/micronaut-kubernetes/latest/guide/#kubernetes-informer)

- [https://github.com/kubernetes-client/java/wiki](https://github.com/kubernetes-client/java/wiki)

## Feature discovery-kubernetes documentation

- [Micronaut Kubernetes Service Discovery documentation](https://micronaut-projects.github.io/micronaut-kubernetes/latest/guide/#service-discovery)

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature kubernetes-reactor-client documentation

- [Micronaut Kubernetes Reactor Client documentation](https://micronaut-projects.github.io/micronaut-kubernetes/latest/guide/#kubernetes-client)

- [https://github.com/kubernetes-client/java/wiki](https://github.com/kubernetes-client/java/wiki)

## Feature kubernetes documentation

- [Micronaut Kubernetes Support documentation](https://micronaut-projects.github.io/micronaut-kubernetes/latest/guide/index.html)

- [https://kubernetes.io/docs/home/](https://kubernetes.io/docs/home/)

## Feature management documentation

- [Micronaut Management documentation](https://docs.micronaut.io/latest/guide/index.html#management)


