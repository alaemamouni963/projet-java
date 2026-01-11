@echo off
echo ========================================
echo  COMPILATION PROJET E-LEARNING
echo ========================================

if not exist bin mkdir bin

echo Compilation des enums...
javac -d bin model\enums\*.java

echo Compilation du modele...
javac -cp bin -d bin model\*.java

echo Compilation de utils...
javac -cp bin -d bin utils\*.java

echo Compilation de dao...
javac -cp bin -d bin dao\*.java

echo Compilation de patterns...
javac -cp bin -d bin patterns\*.java

echo Compilation de service...
javac -cp bin -d bin service\*.java

echo Compilation de Main...
javac -cp bin -d bin Main.java

if %ERRORLEVEL% equ 0 (
    echo.
    echo ✅ COMPILATION REUSSIE !
    echo.
    echo Pour executer : java -cp bin Main
    echo.
    pause
) else (
    echo.
    echo ❌ ERREUR DE COMPILATION
    pause
)