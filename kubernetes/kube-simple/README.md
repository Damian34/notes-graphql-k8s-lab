# Minikube k8s Simple examples

Minikube is a tool and environment that allows to run a local Kubernetes cluster on docker container.

here can use 2 basic commands to work with kubernetes:
- `minikube ...` - to work with minikube environment
- `kubectl ...` - to work with eg. kubernetes manifests, pods etc..

Some commands:

- `minikube start` - Starts a local Kubernetes cluster
- `minikube stop` - Stops a running local Kubernetes cluster
- `minikube delete` - Delete local Kubernetes Clusters
- `minikube status` - Gets the status of a local Kubernetes cluster
- `minikube dashboard` - Access to Kubernetes browser dashboard
- `minikube ssh` - Login into minikube environment, then can work with docker images eg. `docker ps -a`
- `minikube ip` - Retrieves the IP address of the specified node

- `minikube addons enable metrics-server` - Allow to use Autoscaling and add dashboard metrics
- `minikube service <service name>` - Let temporary tunnel api from minikube environment to localhost
- `kubectl port-forward service/notes-api-service 8081:8081` - Eventually temporary tunnel api & `127.0.0.1:8081/actuator/health`
- `kubectl exec -it <pod> -- sh` - Let get inside pod's container

Summary:

Minikube runs inside its own container with a built-in Docker environment.
By default, it doesn’t expose access to services like NodePort, so you need to use tunneling to reach them.

BUT

if you want a more "production-like" Kubernetes environment without tunneling,
it’s better to use something like Kubernetes from Docker Desktop.

Added powershell Script examples
- option1 - add manifests for Minikube line by line, a bit flawed for re-run
- option2 - add manifests for Minikube, better for re-run
- option3 - add manifests for Docker Desktop Kubernetes, with re-run
