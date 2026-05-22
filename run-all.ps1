# Script to run all Quantity Measurement microservices and the frontend locally.

# 1. Load environment variables from .env if present
Write-Host "Loading environment variables from .env file..." -ForegroundColor Cyan
if (Test-Path .env) {
    Get-Content .env | ForEach-Object {
        if ($_ -match '^(?<name>[^#=]+)=(?<value>.*)$') {
            $name = $Matches.name.Trim()
            $value = $Matches.value.Trim()
            [System.Environment]::SetEnvironmentVariable($name, $value, [System.EnvironmentVariableTarget]::Process)
            Write-Host "  Loaded variable: $name" -ForegroundColor Gray
        }
    }
} else {
    Write-Warning "No .env file found in the root directory!"
}

# 2. Start Eureka Discovery Server (Port 8761)
Write-Host "`nStarting Eureka Discovery Server..." -ForegroundColor Green
Start-Process powershell -ArgumentList "-NoExit", "-Command", "Title Eureka-Server; .\mvnw spring-boot:run -pl infrastructure/eureka-server"
Write-Host "Waiting 10 seconds for Eureka Server to initialize..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

# 3. Start Identity Service (Port 8082)
Write-Host "Starting Identity Service..." -ForegroundColor Green
Start-Process powershell -ArgumentList "-NoExit", "-Command", "Title Identity-Service; .\mvnw spring-boot:run -pl services/identity-service"
Start-Sleep -Seconds 3

# 4. Start Measurement Service (Port 8081)
Write-Host "Starting Measurement Service..." -ForegroundColor Green
Start-Process powershell -ArgumentList "-NoExit", "-Command", "Title Measurement-Service; .\mvnw spring-boot:run -pl services/measurement-service"
Start-Sleep -Seconds 3

# 5. Start API Gateway (Port 8080)
Write-Host "Starting API Gateway..." -ForegroundColor Green
Start-Process powershell -ArgumentList "-NoExit", "-Command", "Title API-Gateway; .\mvnw spring-boot:run -pl infrastructure/api-gateway"
Start-Sleep -Seconds 3

# 6. Start React Frontend (Port 5173)
if (Test-Path "d:\Ashutosh\quantity-measurement-ui") {
    Write-Host "Starting React Frontend UI..." -ForegroundColor Green
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "Title React-Frontend; cd d:\Ashutosh\quantity-measurement-ui; npm install; npm run dev"
} else {
    Write-Warning "React Frontend directory not found at d:\Ashutosh\quantity-measurement-ui!"
}

Write-Host "`nAll services started successfully in separate terminal windows!" -ForegroundColor Green
Write-Host "Access the frontend at http://localhost:5173" -ForegroundColor Cyan
Write-Host "Access the Eureka dashboard at http://localhost:8761" -ForegroundColor Cyan
