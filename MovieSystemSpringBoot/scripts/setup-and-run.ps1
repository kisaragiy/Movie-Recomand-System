$ErrorActionPreference = "Stop"
$ScriptDir = $PSScriptRoot

Write-Host "=== Step 1/4 Maven ==="
& (Join-Path $ScriptDir "setup-maven.ps1")

Write-Host "=== Step 2/4 Infra ==="
& (Join-Path $ScriptDir "start-infra.ps1")

Write-Host "=== Step 3/4 Services ==="
& (Join-Path $ScriptDir "start-services.ps1")

Write-Host "Waiting for services ..."
Start-Sleep -Seconds 20

Write-Host "=== Step 4/4 API tests ==="
& (Join-Path $ScriptDir "run-api-tests.ps1")
