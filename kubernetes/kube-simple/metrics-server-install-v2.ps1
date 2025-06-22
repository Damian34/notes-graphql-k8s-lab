
# Installation metrics-server with own custom mainfests (for Docker Desktop Kubernetes)

Write-Host "Checking if metrics-server is installed..."
if (kubectl get deployment metrics-server -n kube-system -o name --ignore-not-found) {
    Write-Host "Removing existing metrics-server..."
    kubectl delete -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
    Start-Sleep -Seconds 5
}
kubectl delete -f .\manifest\metrics --ignore-not-found

Write-Host "Installing official metrics-server..."
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
Start-Sleep -Seconds 10

Write-Host "Applying custom metrics manifests..."
kubectl apply -f .\manifest\metrics

Write-Host "Done! Metrics-server and custom manifests have been installed." 