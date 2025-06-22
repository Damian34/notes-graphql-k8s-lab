
# Run Manifests with Docker Desktop Kubernetes, this way is closer to production environment than Minikube

Write-Host "Set docker-desktop context..."
kubectl config use-context docker-desktop

Write-Host "Build notes-api image locally..."
docker build -t notes-api:latest ..\..

# Delete
& ".\delete-all-files.ps1"


# Normally metrics/HPA server is not necessary, but it is kinda nice function
# for horizontal scale our Appliation based on usage overload

# For metrics-server can use own manifests for metrics-server or look at script: metrics-server-install-v1.ps1

#Write-Host "Check if metrics-server is running and install, configure..."
#& ".\metrics-server-install-v1.ps1" # with CLI only
& ".\metrics-server-install-v2.ps1" # with own manifests

Write-Host "Apply new manifest files..."
kubectl apply -f .\manifest
kubectl apply -f .\manifest\postgres-db
kubectl apply -f .\manifest\notes-api

# kubectl get pods
# kubectl get service

# kubectl get hpa
# kubectl describe hpa notes-api-hpa

# without Minikube, i can acces to NodePort normally(witchout tunneling that like in Minikube),
# and localy with localhost, but at production sytuation is smillar however there is DNS address or the server ip
# http://localhost:30081/actuator/health
# http://localhost:30081/test/cpu-overload


#kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
#kubectl delete -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
#kubectl get deployment metrics-server -n kube-system --ignore-not-found

# kubectl get pods -n kube-system | findstr metrics-server
# kubectl logs -n kube-system metrics-server-6767585cfb-wjm8p
# kubectl logs -n kube-system metrics-server
# kubectl get service metrics-server -n kube-system


