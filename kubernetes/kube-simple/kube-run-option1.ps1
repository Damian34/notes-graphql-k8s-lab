
# Run manifests with Minikube's k8s cluster
# Before use "minikube start"

#& minikube -p minikube docker-env | Invoke-Expression

Write-Host "Build notes-api image directly in Minikube..."
minikube image build -t notes-api:latest ..\..

# eventually load created docker image to minikube
# Write-Host "Build notes-api in docker and push to minikube..."
# docker build -t notes-api:latest ..\..\..
# minikube image load notes-api:latest

Write-Host "Delete previous manifest files..."
kubectl delete -f .\manifest
kubectl delete pods --all

Write-Host "Create ConfigMap..."
kubectl apply -f .\manifest\configmap.yaml

Write-Host "Create Secret..."
kubectl apply -f .\manifest\secret.yaml

# postgres db

Write-Host "Create PersistentVolumeClaim..."
kubectl apply -f .\manifest\postgres-db\postgres-pvc.yaml

Write-Host "Deploy postgres-db to Kubernetes cluster..."
kubectl apply -f .\manifest\postgres-db\postgres-deployment.yaml

Write-Host "Expose postgres-db as a service..."
kubectl apply -f .\manifest\postgres-db\postgres-service.yaml

# notes api

Write-Host "Deploy notes-api to Kubernetes cluster..."
kubectl apply -f .\manifest\notes-api\notes-api-deployment.yaml

Write-Host "Create notes-api-hpa..."
kubectl apply -f .\manifest\notes-api\notes-api-hpa.yaml

Write-Host "Expose notes-api as a service..."
kubectl apply -f .\manifest\notes-api\notes-api-service.yaml

Write-Host "Pods list:"
kubectl get pods

Write-Host "Wait 10 sec:"
Start-Sleep -Seconds 10

# kubectl get pods
# kubectl get pods -o wide
# kubectl describe pod notes-api-6686c6495-b62x8
# kubectl logs notes-api-56d4fd8d9-6kh76

# minikube ssh
# minikube ip
# docker ps -a

# kubectl get replicaset
# kubectl get deployment
# kubectl get service
# kubectl get pvc

# kubectl expose deployment notes-api --type=NodePort --port=8080
