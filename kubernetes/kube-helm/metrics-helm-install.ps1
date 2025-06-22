$releaseName = "metrics-server"
$namespace = "kube-system"

# Before Installation need to be sure that metrics-server is not installed
# helm uninstall metrics-server -n $namespace
# kubectl delete -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

# Check if metrics-server is already installed in the target namespace
$exists = helm list -n $namespace -q | Where-Object { $_ -eq $releaseName }

if ($exists) {
    Write-Host "Metrics-server is already installed."
    return
}

# Add the metrics-server Helm repository
Write-Host "Adding metrics-server Helm repository..."
helm repo add metrics-server https://kubernetes-sigs.github.io/metrics-server/

# Update all Helm repositories
Write-Host "Updating Helm repositories..."
helm repo update

# Install metrics-server with insecure TLS args
Write-Host "Installing metrics-server..."
helm install metrics-server metrics-server/metrics-server `
    --namespace $namespace `
    --create-namespace `
    --set args="{--kubelet-insecure-tls}"

Write-Host "Done! Metrics-server have been installed."

# helm uninstall metrics-server -n kube-system
