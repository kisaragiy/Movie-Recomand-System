$ErrorActionPreference = "Stop"
$Root = Split-Path $PSScriptRoot -Parent
Set-Location $Root

. (Join-Path $PSScriptRoot "setup-maven.ps1")

$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "123456"

$LogDir = Join-Path $Root "logs"
$PidDir = Join-Path $Root "pids"
New-Item -ItemType Directory -Force -Path $LogDir, $PidDir | Out-Null

Write-Host "Maven build ..."
& "$env:MAVEN_HOME\bin\mvn.cmd" -f pom-microservices.xml clean package -DskipTests -q `
    -pl eureka-server,user-service,movie-service,recommendation-service -am
if ($LASTEXITCODE -ne 0) { throw "Maven build failed" }

function Start-Microservice {
    param([string]$Name, [string]$Module, [int]$WaitSec = 25)
    $targetDir = Join-Path (Join-Path $Root $Module) "target"
    $jar = Get-ChildItem $targetDir -Filter "*.jar" |
        Where-Object { $_.Name -notmatch "sources|javadoc|original" } |
        Sort-Object Length -Descending |
        Select-Object -First 1
    if (-not $jar) { throw "No jar in $Module/target" }
    $logOut = Join-Path $LogDir "$Name-out.log"
    $logErr = Join-Path $LogDir "$Name-err.log"
    $pidFile = Join-Path $PidDir "$Name.pid"
    $proc = Start-Process -FilePath "$env:JAVA_HOME\bin\java.exe" -ArgumentList "-jar", $jar.FullName `
        -WorkingDirectory (Join-Path $Root $Module) `
        -RedirectStandardOutput $logOut -RedirectStandardError $logErr `
        -WindowStyle Hidden -PassThru
    $proc.Id | Out-File $pidFile -Encoding ascii
    Write-Host "Started $Name PID $($proc.Id) log=$logFile"
    Start-Sleep -Seconds $WaitSec
}

Start-Microservice -Name "eureka" -Module "eureka-server" -WaitSec 35
Start-Microservice -Name "user" -Module "user-service" -WaitSec 25
Start-Microservice -Name "movie" -Module "movie-service" -WaitSec 25
Start-Microservice -Name "recommendation" -Module "recommendation-service" -WaitSec 25

Write-Host "URLs:"
Write-Host "  http://localhost:8761"
Write-Host "  http://localhost:8082/microservice/api/user/health"
Write-Host "  http://localhost:8083/microservice/api/movie/health"
Write-Host "  http://localhost:8084/microservice/api/recommend/health"
