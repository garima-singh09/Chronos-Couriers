@echo off
javac chronos\ChronosApp.java chronos\service\DispatchCenter.java chronos\model\*.java chronos\util\*.java
java -cp . chronos.ChronosApp