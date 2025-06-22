
#Write-Host "Delete metrics-server..."
#kubectl delete -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

Write-Host "Delete previous manifest files..."
kubectl delete -f .\manifest --ignore-not-found
kubectl delete -f .\manifest\postgres-db --ignore-not-found
kubectl delete -f .\manifest\notes-api --ignore-not-found

Write-Host "Delete all previous pods, services, deployments, volumes, scalings..."
# kubectl delete all --all -n default

$services = kubectl get services -n default --no-headers | Where-Object { $_ -notmatch "^kubernetes\s" }
foreach ($line in $services) {
    $name = $line.Split()[0]
    kubectl delete services $name -n default
}

kubectl delete deployments --all -n default
kubectl delete pods --all -n default
kubectl delete pvc --all # Delete volumes is not necessery
kubectl delete hpa --all # Delete scalings
