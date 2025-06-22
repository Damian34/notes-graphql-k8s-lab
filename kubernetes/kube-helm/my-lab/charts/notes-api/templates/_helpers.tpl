{{/*
Create short chart name
*/}}
{{- define "notes-api.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Create fullname used by the chart label. shorten to allow add suffix.
*/}}
{{- define "notes-api.fullname" -}}
{{- if contains .Chart.Name .Release.Name }}
{{- .Release.Name | trunc 50 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name .Chart.Name | trunc 50 | trimSuffix "-" }}
{{- end }}
{{- end }}

{{/*
Function generating standard labels for K8s resources
*/}}
{{- define "notes-api.labels" -}}
helm.sh/chart: {{ include "notes-api.fullname" . }}
{{ include "notes-api.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "notes-api.selectorLabels" -}}
app.kubernetes.io/name: {{ include "notes-api.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the host address for the postgres service
*/}}
{{- define "notes-api.postgres-host" -}}
{{- printf "%s-postgres-db-service.%s.svc.cluster.local" .Release.Name .Values.namespace -}}
{{- end -}}

