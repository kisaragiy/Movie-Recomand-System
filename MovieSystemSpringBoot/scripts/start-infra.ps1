$ErrorActionPreference = "Stop"
$Root = Split-Path $PSScriptRoot -Parent
Set-Location $Root

$env:DB_PASSWORD = "123456"
$env:DB_NAME = "moviesystemspringboot_db"

if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
    throw "Docker not found. Start Docker Desktop first."
}

Write-Host "Starting MySQL and Redis ..."
docker compose up mysql redis -d

Write-Host "Waiting for MySQL (up to 120s) ..."
$deadline = (Get-Date).AddSeconds(120)
while ((Get-Date) -lt $deadline) {
    $ping = cmd /c "docker exec movie-mysql mysqladmin ping -h localhost -uroot -p123456 2>nul"
    if ($ping -match "alive") {
        Write-Host "MySQL is ready."
        break
    }
    Start-Sleep -Seconds 3
}

Start-Sleep -Seconds 2
docker exec movie-redis redis-cli ping
Write-Host "Infra ready. DB=$env:DB_NAME"
