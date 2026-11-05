#!/usr/bin/env sh
# Compile, lance les tests puis produit mastermind.jar (JDK 17+)
set -e
rm -rf build && mkdir -p build/classes build/test
javac -encoding UTF-8 --release 17 -d build/classes src/mastermind/*.java
javac -encoding UTF-8 --release 17 -cp build/classes -d build/test test/mastermind/*.java
java -cp build/classes:build/test mastermind.EvaluationTest
jar --create --file mastermind.jar --main-class mastermind.MastermindGame -C build/classes .
echo "OK : lancer avec  java -jar mastermind.jar"
