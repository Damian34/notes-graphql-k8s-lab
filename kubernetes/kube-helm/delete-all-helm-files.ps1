
$releaseName  = "my-lab"
$namespace = "notes-lab"

Write-Host "Delete previous helm files..."
helm uninstall $releaseName --namespace $namespace
kubectl delete deployments --all -n $namespace
kubectl delete pods --all -n $namespace
kubectl delete pvc --all -n $namespace
kubectl delete hpa --all -n $namespace

kubectl delete namespace $namespace

