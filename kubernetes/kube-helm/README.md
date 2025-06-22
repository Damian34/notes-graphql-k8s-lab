# Kubernetes & helm charts examples

Helm is a tool managing packages for Kubernetes that contains:
- charts - packages made up of Chart.yaml, values.yaml, templates/
- helm notation `{{ .Value.?? }}` - is used to refer to values from the `values.yaml` file, 
including both global and local scopes, where global values have higher priority and override the local ones.
- templates/ - contains _helpers.tpl for local reusable functions and manifest files

#### At charts here me use:
- releaseName = "my-lab"
- namespace = "notes-lab"

#### verify code
helm lint ./kubernetes/kube-helm/my-lab

#### generate template without helm notation cmd 
helm template my-lab ./kubernetes/kube-helm/my-lab --debug

#### install
helm install my-lab ./kubernetes/kube-helm/my-lab

#### install + debug
helm install my-lab ./kubernetes/kube-helm/my-lab --dry-run --debug

#### install + create namespace
helm install my-lab ./kubernetes/kube-helm/my-lab --namespace notes-lab --create-namespace

#### create namespace only
kubectl create namespace notes-lab

#### all namespaces
helm list --all-namespaces

#### all pods per namespaces
kubectl get pods -n notes-lab

#### read pod details
kubectl describe pod <pod_name> -n notes-lab

#### get service details
kubectl get service -n notes-lab
http://localhost:30081/actuator/health

#### get hpa service
kubectl get hpa -n notes-lab

#### Added powershell Script examples
- option1 - add helm run for Minikube.
- option2 - add helm run for Docker Desktop Kubernetes.
