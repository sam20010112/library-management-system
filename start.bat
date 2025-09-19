@echo off
echo Starting Library Management System...


echo.
echo Starting Backend Server...
cd backend
start "Backend Server" cmd /k "mvn spring-boot:run"

echo.
echo Waiting for backend to start...
timeout /t 15 /nobreak > nul

echo.
echo Starting Frontend Server...
cd ..\frontend
start "Frontend Server" cmd /k "npm run serve"

echo.
echo Both servers are starting...
echo Backend: http://localhost:8080
echo Frontend: http://localhost:3000
echo.
echo If you see connection refused errors:
echo 1. Make sure MySQL is running
echo 2. Create database 'library_system'
echo 3. Execute DB/DDL.sql and DB/DML.sql
echo.
echo Press any key to exit...
pause > nul

