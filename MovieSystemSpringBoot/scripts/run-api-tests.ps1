$ErrorActionPreference = "Continue"
$BaseUser = "http://localhost:8082"
$BaseMovie = "http://localhost:8083"
$BaseRec = "http://localhost:8084"
$SessionFile = Join-Path $env:TEMP "movie-session.cookies"

function Test-Api {
    param([string]$Name, [scriptblock]$Block)
    try {
        $result = & $Block
        Write-Host "[PASS] $Name" -ForegroundColor Green
        return $true
    } catch {
        Write-Host "[FAIL] $Name - $_" -ForegroundColor Red
        return $false
    }
}

$passed = 0
$total = 0

$total++
if (Test-Api "Eureka health" { curl.exe -sf "http://localhost:8761/actuator/health" | Out-Null }) { $passed++ }

$total++
if (Test-Api "Recommend health" { curl.exe -sf "$BaseRec/microservice/api/recommend/health" | Out-Null }) { $passed++ }

$total++
if (Test-Api "Movie health" { curl.exe -sf "$BaseMovie/microservice/api/movie/health" | Out-Null }) { $passed++ }

$total++
if (Test-Api "User health" { curl.exe -sf "$BaseUser/microservice/api/user/health" | Out-Null }) { $passed++ }

$total++
if (Test-Api "Check username" { curl.exe -sf "${BaseUser}/microservice/api/user/check-username?uname=H011" | Out-Null }) { $passed++ }

$total++
if (Test-Api "Login" {
    curl.exe -sf -c $SessionFile -X POST "${BaseUser}/microservice/api/user/login" -H "Content-Type: application/json" -d "{\"uname\":\"H011\",\"upass\":\"123\"}" | Out-Null
}) { $passed++ }

$total++
if (Test-Api "Session check" { curl.exe -sf -b $SessionFile "${BaseUser}/microservice/api/user/check" | Out-Null }) { $passed++ }

$movieListUrl = "${BaseMovie}/microservice/api/movie/admin/list?pageNum=1&pageSize=5"
$total++
if (Test-Api "Movie list" { curl.exe -sf $movieListUrl | Out-Null }) { $passed++ }

$hybridUrl = "${BaseRec}/microservice/api/recommend/hybrid?user_id=34"
$total++
if (Test-Api "Hybrid recommend" {
    $r = curl.exe -s $hybridUrl
    if ($r -match '"status":500' -or $r -notmatch '^\[') { throw "bad response: $r" }
}) { $passed++ }

$loveUrl = "${BaseRec}/microservice/api/recommend/love?user_id=34"
$total++
if (Test-Api "Love recommend" { curl.exe -sf $loveUrl | Out-Null }) { $passed++ }

$detailUrl = "${BaseRec}/microservice/api/recommend/hybrid/detail?user_id=34"
$total++
if (Test-Api "Hybrid detail" { curl.exe -sf $detailUrl | Out-Null }) { $passed++ }

$total++
if (Test-Api "Forgot password" {
    curl.exe -sf -X POST "${BaseUser}/microservice/api/user/forgot-password" -H "Content-Type: application/json" -d "{\"uname\":\"H011\",\"tel\":\"15523658485\"}" | Out-Null
}) { $passed++ }

Write-Host ""
Write-Host "Result: $passed / $total passed"
if ($passed -lt $total) { exit 1 }
