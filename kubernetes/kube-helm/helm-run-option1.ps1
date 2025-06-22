
# Run Helm with Minikube Kubernetes
# Before use "minikube start"

$releaseName  = "my-lab"
$namespace = "notes-lab"

# Delete previous Helm releases and resources
& ".\delete-all-helm-files.ps1" --releaseName=$releaseName --namespace=$namespace

Write-Host "Build notes-api image directly in Minikube..."
minikube image build -t notes-api:latest ..\..

Write-Host "Create namespace..."
kubectl create namespace notes-lab

Write-Host "Check if metrics-server is running and install..."
& ".\metrics-helm-install.ps1"

Write-Host "Apply charts..."
helm install $releaseName ./my-lab --namespace $namespace

# upgrade charts
# helm upgrade my-lab ./my-lab --namespace notes-lab

# Open
# kubectl get pods -n notes-lab
# kubectl get service -n notes-lab
# kubectl get hpa -n notes-lab

# tunneling api server ports for minikube:
# minikube service my-lab-notes-api-service -n notes-lab
# OR
# kubectl port-forward svc/my-lab-notes-api-service 8081:8081 -n notes-lab
# http://localhost:8081/actuator/health

