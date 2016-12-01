#
# CSP - Backtracking search with forward checking and heuristics.
# Copyright (C) 2007. Piero Dalle Pezze, -
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License along
# with this program; if not, write to the Free Software Foundation, Inc.,
# 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
#
#
# Created on: 	16/06/2007
# Modifies:
#		v.1.0 (16/06/2007): Documentation and codify of the class.
# Description : This is the makefile to build the software CSP.
#				It runs on UNIX/Linux systems.


install:
	rm -f csp.jar *~
	javac ./src/csp/*.java -d ./ -classpath ./
	javac ./src/heuristic/*.java -d ./ -classpath ./
	javac ./src/algorithm/*.java -d ./ -classpath ./
	javac ./src/generator/*.java -d ./ -classpath ./
	javac ./src/statistic/*.java -d ./ -classpath ./	
	javac ./src/main/*.java -d ./ -classpath ./
	jar cfm csp.jar manifest.mf ./csp ./heuristic ./algorithm ./generator ./statistic ./main
	rm -rf ./csp ./heuristic ./algorithm ./generator ./statistic ./main
	@echo
	@echo "To execute CSP, run ./csp.sh"

doc:
	javadoc -subpackages csp heuristic algorithm generator statistic -sourcepath ./src -d ./doc -classpath ./ -windowtitle "CSP - Source code documentation"

clean:
	rm -f csp.jar *~
	rm -rf doc/