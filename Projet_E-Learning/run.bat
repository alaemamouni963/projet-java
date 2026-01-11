@echo off
echo ========================================
echo  LANCEMENT PLATEFORME E-LEARNING
echo ========================================

if not exist bin (
    echo Les classes ne sont pas compilees.
    echo Executez d'abord compile.bat
    pause
    exit /b 1
)

java -cp bin Main
pause