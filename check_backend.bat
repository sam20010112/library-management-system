@echo off
echo ========================================
echo Library Management System - Backend Status Check
echo ========================================
echo.

echo 1. Checking if port 8080 is in use...
netstat -ano | findstr :8080
if %errorlevel% equ 0 (
    echo ✓ Port 8080 is in use
) else (
    echo ✗ Port 8080 is not in use
)

echo.
echo 2. Checking Java processes...
tasklist | findstr java
if %errorlevel% equ 0 (
    echo ✓ Java process found
) else (
    echo ✗ No Java process found
)

echo.
echo 3. Testing backend API connection...
curl -s -o nul -w "HTTP Status: %%{http_code}\nResponse Time: %%{time_total}s\n" http://localhost:8080/api/health
if %errorlevel% equ 0 (
    echo ✓ API connection successful
) else (
    echo ✗ API connection failed
)

echo.
echo 4. Testing backend root path...
curl -s -o nul -w "HTTP Status: %%{http_code}\n" http://localhost:8080/api
if %errorlevel% equ 0 (
    echo ✓ Backend root path accessible
) else (
    echo ✗ Backend root path not accessible
)

echo.
echo ========================================
echo Check completed
echo ========================================
pause
