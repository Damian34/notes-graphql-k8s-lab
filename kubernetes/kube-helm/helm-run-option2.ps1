
# Run Helm with Docker Desktop Kubernetes

$releaseName  = "my-lab"
$namespace = "notes-lab"

# Delete
& ".\delete-all-helm-files.ps1" --releaseName=$releaseName --namespace=$namespace

#helm uninstall my-lab --namespace notes-lab

Write-Host "Create namespace..."
kubectl create namespace notes-lab

Write-Host "Check if metrics-server is running and install..."
& ".\metrics-helm-install-v1.ps1"

Write-Host "Apply charts..."
helm install $releaseName ./my-lab --namespace $namespace

# upgrade charts
# helm upgrade my-lab ./my-lab --namespace notes-lab

# Open
# kubectl get pods -n notes-lab
# kubectl get service -n notes-lab
# kubectl get hpa -n notes-lab
# http://localhost:30081/actuator/health
