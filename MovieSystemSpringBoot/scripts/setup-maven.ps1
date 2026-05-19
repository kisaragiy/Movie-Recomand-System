# Install Maven to user tools folder and set JAVA_HOME / MAVEN_HOME
$ErrorActionPreference = "Stop"
$MavenVersion = "3.9.9"
$ToolsDir = Join-Path $env:USERPROFILE "tools"
$MavenHome = Join-Path $ToolsDir "apache-maven-$MavenVersion"
$ZipPath = Join-Path $env:TEMP "apache-maven-$MavenVersion-bin.zip"
$DownloadUrl = "https://archive.apache.org/dist/maven/maven-3/$MavenVersion/binaries/apache-maven-$MavenVersion-bin.zip"

$JdkHome = "C:\Program Files\Java\jdk-21.0.10"
if (-not (Test-Path $JdkHome)) {
    $JdkHome = (Get-ChildItem "C:\Program Files\Java\jdk-*" -ErrorAction SilentlyContinue | Sort-Object Name -Descending | Select-Object -First 1).FullName
}
if (-not $JdkHome) { throw "JDK not found. Install JDK 21 first." }

New-Item -ItemType Directory -Force -Path $ToolsDir | Out-Null

if (-not (Test-Path (Join-Path $MavenHome "bin\mvn.cmd"))) {
    Write-Host "Downloading Maven $MavenVersion ..."
    Invoke-WebRequest -Uri $DownloadUrl -OutFile $ZipPath -UseBasicParsing
    Expand-Archive -Path $ZipPath -DestinationPath $ToolsDir -Force
    Remove-Item $ZipPath -Force
}

[Environment]::SetEnvironmentVariable("JAVA_HOME", $JdkHome, "User")
[Environment]::SetEnvironmentVariable("MAVEN_HOME", $MavenHome, "User")

$userPath = [Environment]::GetEnvironmentVariable("Path", "User")
$pathsToAdd = @("%JAVA_HOME%\bin", "%MAVEN_HOME%\bin")
foreach ($p in $pathsToAdd) {
    if ($userPath -notlike "*$p*") { $userPath = "$p;$userPath" }
}
[Environment]::SetEnvironmentVariable("Path", $userPath.TrimEnd(';'), "User")

$env:JAVA_HOME = $JdkHome
$env:MAVEN_HOME = $MavenHome
$env:Path = "$env:JAVA_HOME\bin;$env:MAVEN_HOME\bin;$env:Path"

Write-Host "JAVA_HOME = $env:JAVA_HOME"
& "$env:JAVA_HOME\bin\java.exe" -version
& "$env:MAVEN_HOME\bin\mvn.cmd" -version
Write-Host "Maven setup done."
