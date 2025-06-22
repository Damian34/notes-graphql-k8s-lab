{{/*
Create short chart name
*/}}
{{- define "postgres-db.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Create fullname used by the chart label. shorten to allow add suffix.
*/}}
{{- define "postgres-db.fullname" -}}
{{- if .Values.fullnameOverride }}
{{ .Values.fullnameOverride }}
{{- else }}
{{- if contains .Chart.Name .Release.Name }}
{{- .Release.Name | trunc 50 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name .Chart.Name | trunc 50 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Function generating standard labels for K8s resources
*/}}
{{- define "postgres-db.labels" -}}
helm.sh/chart: {{ include "postgres-db.fullname" . }}
{{ include "postgres-db.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "postgres-db.selectorLabels" -}}
app.kubernetes.io/name: {{ include "postgres-db.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

