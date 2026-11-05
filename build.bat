@echo off
REM Compile, lance les tests puis produit mastermind.jar (JDK 17+)
if exist build rmdir /s /q build
mkdir build\classes build\test
javac -encoding UTF-8 --release 17 -d build\classes src\mastermind\*.java || exit /b 1
javac -encoding UTF-8 --release 17 -cp build\classes -d build\test test\mastermind\*.java || exit /b 1
java -cp build\classes;build\test mastermind.EvaluationTest || exit /b 1
jar --create --file mastermind.jar --main-class mastermind.MastermindGame -C build\classes .
echo OK : lancer avec  java -jar mastermind.jar
