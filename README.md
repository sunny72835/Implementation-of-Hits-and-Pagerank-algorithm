# Implementation-of-Hits-and-Pagerank-algorithm
Execution instructions:
1. The program should be executed like: java pgrk0957 iterations initialvalue file.txt
2. Instructions for graph file:
	1. The file should contain list of edges. It will have only integer data. It should be a text (".txt") file. 
	2. Each line of the file will have exactly 2 integer numbers separated by "single" space. First line will always contain no of vertices and no. of edges followed.
	3. There should be no any other extra characters (visible/invisible) in file (e.g. newline, space etc.)
	4. If there is a self link,then it should be listed like "3 3" in the graph.
	If any of the above condition fails, program will throw an error.
3. Here it is assumed that the name of vertices starts with 0 and are continues i.e. (0,1,2,3,...). There should not be any gaps (i.e. a graph can't have vertices like 1,2,3,... or 0,1,4,5).
4. Multiple links between two nodes are considered as a single link.
5. The values, printed as output have 7 decimal precision however, in calculation, precision of double is used. So values can vary a little bit if it is not the case in test cases.  
