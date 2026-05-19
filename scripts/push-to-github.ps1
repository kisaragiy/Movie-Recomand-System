# Push local main branch to https://github.com/kisaragiy/Movie-Recomand-System
$ErrorActionPreference = "Stop"
$Root = Split-Path $PSScriptRoot -Parent
if (-not (Test-Path (Join-Path $Root ".git"))) {
    throw "Run from repo root after git is initialized."
}
Set-Location $Root
git remote get-url origin 2>$null
if ($LASTEXITCODE -ne 0) {
    git remote add origin https://github.com/kisaragiy/Movie-Recomand-System.git
}
Write-Host "Pushing to origin main ..."
git push -u origin main
if ($LASTEXITCODE -eq 0) {
    Write-Host "Done: https://github.com/kisaragiy/Movie-Recomand-System"
}
