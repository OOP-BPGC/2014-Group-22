javac -d bin/. -classpath lib/*:. src/*.java;
cd bin;
clear;
java -classpath ../lib/*:. src.Driver;
