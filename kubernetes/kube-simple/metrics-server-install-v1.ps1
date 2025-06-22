
# Install metrics-server manualy (for Docker Desktop Kubernetes)

if (kubectl get deployment metrics-server -n kube-system -o name --ignore-not-found) {
    Write-Host "metrics-server already installed. Skipping setup."
    return
}

Write-Host "Installing metrics-server..."
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
Start-Sleep -Seconds 10

# Define desired args and get current args from the deployment.
$deployment = kubectl get deployment metrics-server -n kube-system -o json | ConvertFrom-Json
$currentArgs = $deployment.spec.template.spec.containers[0].args
$desiredArgs = @("--kubelet-insecure-tls", "--kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname")
$patchOperations = @()

# Build a JSON patch for any missing arguments.
if (-not $currentArgs) {
    $patchOperations = @( @{ op="add"; path="/spec/template/spec/containers/0/args"; value=$desiredArgs } )
} else {
    foreach ($arg in $desiredArgs) {
        $argName = $arg.Split('=')[0]
        $isMissing = -not ($currentArgs | Where-Object { $_.StartsWith($argName) })
        if ($isMissing) {
            $patchOperations += @{ op="add"; path="/spec/template/spec/containers/0/args/-"; value=$arg }
        }
    }
}

# If any patch operations were created, apply them and restart the deployment.
if ($patchOperations) {
    Write-Host "Patching and restarting metrics-server..."
    $tempFile = [System.IO.Path]::GetTempFileName()
    try {
        $jsonPatch = ConvertTo-Json -InputObject $patchOperations -Depth 5 -Compress
        Set-Content -Path $tempFile -Value $jsonPatch -NoNewline
        kubectl patch deployment metrics-server -n kube-system --type json --patch-file=$tempFile
    } finally {
        Remove-Item $tempFile -Force -ErrorAction SilentlyContinue
    }
    kubectl rollout restart deployment metrics-server -n kube-system
    kubectl rollout status deployment metrics-server -n kube-system --timeout=5m
} else {
    Write-Host "Metrics-server is already configured correctly."
}

Write-Host "Done installing metrics-server."
