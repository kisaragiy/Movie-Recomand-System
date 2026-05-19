$Root = Split-Path $PSScriptRoot -Parent
$PidDir = Join-Path $Root "pids"
if (Test-Path $PidDir) {
    Get-ChildItem $PidDir -Filter "*.pid" | ForEach-Object {
        $procId = Get-Content $_.FullName -ErrorAction SilentlyContinue
        if ($procId) {
            Stop-Process -Id $procId -Force -ErrorAction SilentlyContinue
            Write-Host "Stopped PID $procId ($($_.BaseName))"
        }
    }
}
foreach ($p in 8761, 8082, 8083, 8084, 9999) {
    Get-NetTCPConnection -LocalPort $p -ErrorAction SilentlyContinue | ForEach-Object {
        Stop-Process -Id $_.OwningProcess -Force -ErrorAction SilentlyContinue
    }
}
Write-Host "微服务已停止。基础设施请执行: docker compose down"
