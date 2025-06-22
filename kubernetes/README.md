# About

Here I made some example packets how can be used Kubernetes.
Package structure depends on application. My api here is simple so structure too.
Here is also a lot of comments, but I was testing this k8s & helm tools. so I let it be.
Scripts here work mostly like delete everything each time. that is for local use.
For production deletion/upgrading manifests/components should be better controlled.

## How to install Minikube

can install with instruction
https://minikube.sigs.k8s.io/docs/start/

### Or install Minikube this way with choco by powershell

install choco:

`Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))`

check:

`choco --version`

install minikube:

`choco install minikube -y`

check:

`minikube version`

## Docker Desktop & Kubernetes

Can also work with Kubernetes at **Docker Desktop** instead **Minikube**,
using option: Docker Desktop -> settings -> Kubernetes -> Enable Kubernetes(it gonna install k8s containers)

## How to install Helm

here is info how to install Helm on different systems(on windows by powershell)
https://helm.sh/docs/intro/install/


