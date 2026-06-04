param(
    [string]$Source = "",
    [string]$Destination = ""
)

$ErrorActionPreference = "Stop"

function Convert-ResourceName([string]$Name) {
    $base = [System.IO.Path]::GetFileNameWithoutExtension($Name)
    $base = $base -replace '@2x$', '' -replace '@3x$', ''
    $base = $base.ToLowerInvariant() -replace '[^a-z0-9_]+', '_'
    $base = $base.Trim('_')
    if ($base -match '^[0-9]') {
        $base = "img_$base"
    }
    return $base
}

$repoRoot = Resolve-Path (Join-Path $PSScriptRoot "..\..")
if ([string]::IsNullOrWhiteSpace($Source)) {
    $sourcePath = Get-ChildItem -Path $repoRoot -Recurse -Directory -Filter "image.xcassets" |
        Where-Object { $_.FullName -like "*Resource*Images*" } |
        Select-Object -First 1 -ExpandProperty FullName
    if (-not $sourcePath) {
        throw "Could not find image.xcassets under the repository root."
    }
} else {
    $sourcePath = (Resolve-Path $Source).Path
}

if ([string]::IsNullOrWhiteSpace($Destination)) {
    $destinationPath = (Resolve-Path (Join-Path $PSScriptRoot "..\app\src\main\res")).Path
} else {
    $destinationPath = (Resolve-Path $Destination).Path
}
$files = Get-ChildItem -Path $sourcePath -Recurse -Filter *.png

foreach ($file in $files) {
    $folder = if ($file.Name -match '@3x') {
        "drawable-xxhdpi"
    } elseif ($file.Name -match '@2x') {
        "drawable-xhdpi"
    } else {
        "drawable-nodpi"
    }

    $targetDir = Join-Path $destinationPath $folder
    New-Item -ItemType Directory -Force $targetDir | Out-Null
    $resourceName = Convert-ResourceName $file.Name
    $target = Join-Path $targetDir "$resourceName.png"

    if (-not (Test-Path $target)) {
        Copy-Item -LiteralPath $file.FullName -Destination $target
    }
}

Write-Host "Migrated $($files.Count) PNG files from $sourcePath to $destinationPath"
