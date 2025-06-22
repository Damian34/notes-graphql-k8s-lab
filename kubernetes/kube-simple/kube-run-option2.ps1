
# Run manifests with Minikube's k8s cluster
# Before use "minikube start"

Write-Host "Build notes-api image directly in Minikube..."
minikube image build -t notes-api:latest ..\..

# Delete
& ".\delete-all-files.ps1"

Write-Host "Allow metrics in Minikube..."
minikube addons enable metrics-server

Write-Host "Apply new manifest files..."
kubectl apply -f .\manifest
kubectl apply -f .\manifest\postgres-db
kubectl apply -f .\manifest\notes-api

# kubectl delete -f .\kubernetes\kube-cli\manifest --ignore-not-found
# kubectl get deployments
# kubectl delete deployment notes-api
# kubectl delete deployment postgres-api
# kubectl delete service notes-api

# kubectl port-forward service/notes-api-service 8081:8081
# 127.0.0.1:8081/actuator/health
# 127.0.0.1:8081/test/cpu-overload
# kubectl get hpa
# kubectl describe hpa notes-api-hpa
