@echo off
rem CSP - Backtracking search with forward checking and heuristics.
rem Copyright (C) 2007. Piero Dalle Pezze, -
rem
rem This program is free software; you can redistribute it and/or modify
rem it under the terms of the GNU General Public License as published by
rem the Free Software Foundation; either version 2 of the License, or
rem (at your option) any later version.
rem
rem This program is distributed in the hope that it will be useful,
rem but WITHOUT ANY WARRANTY; without even the implied warranty of
rem MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
rem GNU General Public License for more details.
rem
rem You should have received a copy of the GNU General Public License along
rem with this program; if not, write to the Free Software Foundation, Inc.,
rem 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
rem
rem
rem File        : compile-on-win.bat
rem Created on  : 16/06/2007
rem Author      : Piero Dalle Pezze
rem Version     : 1.0
rem Description : This is a compile file to build the software csp.
rem		  It runs in Windows system.

@echo on
@echo on
@echo Compilation of csp.
@echo on

del rainbow.jar
javac src\csp\*.java -d .\ -classpath .\
javac src\heuristic\*.java -d .\ -classpath .\
javac src\algorithm\*.java -d .\ -classpath .\
javac src\generator\*.java -d .\ -classpath .\
javac src\statistic\*.java -d .\ -classpath .\
javac src\main\*.java -d .\ -classpath .\
jar cfm csp.jar manifest.mf .\csp .\heuristic .\algorithm .\generator .\statistic .\main

@echo Compilation completed.
@echo Clean of temporary files and .class .

del csp\*.class
rd  csp
del heuristic\*.class
rd  heuristic
del algorithm\*.class
rd  algorithm
del generator\*.class
rd  generator
del statistic\*.class
rd  statistic
del main\*.class
rd  main

@echo Script completed.
@echo To execute csp, run csp.bat .
@echo off