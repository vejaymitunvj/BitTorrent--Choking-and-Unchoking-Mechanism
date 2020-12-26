#!/bin/bash

echo "Compiling the java source code .."
javac -d ./ *.java
#scp -r ./* lin114-00.cise.ufl.edu:~/project
echo "Starting peer 1001 @ lin114-00.cise.ufl.edu..."
ssh -t lin114-00.cise.ufl.edu 'cd project/; java peerProcess 1001 &'
echo "Starting peer 1002 @ lin114-00.cise.ufl.edu..."
ssh -t lin114-01.cise.ufl.edu 'cd project/; java peerProcess 1002 &'
echo "Starting peer 1003 @ lin114-00.cise.ufl.edu..."
ssh -t lin114-02.cise.ufl.edu 'cd project/; java peerProcess 1003 &'
echo "Starting peer 1004 @ lin114-00.cise.ufl.edu..."
ssh -t lin114-03.cise.ufl.edu 'cd project/; java peerProcess 1004 &'
echo "Starting peer 1005 @ lin114-00.cise.ufl.edu..."
ssh -t lin114-04.cise.ufl.edu 'cd project/; java peerProcess 1005 &'
echo "Starting peer 1006 @ lin114-00.cise.ufl.edu..."
ssh -t lin114-05.cise.ufl.edu 'cd project/; java peerProcess 1006 &'
echo "Starting peer 1007 @ lin114-00.cise.ufl.edu..."
ssh -t lin114-06.cise.ufl.edu 'cd project/; java peerProcess 1007 &'
echo "Starting peer 1008 @ lin114-00.cise.ufl.edu..."
ssh -t lin114-07.cise.ufl.edu 'cd project/; java peerProcess 1008 &'
echo "Starting peer 1009 @ lin114-00.cise.ufl.edu..."
ssh -t lin114-08.cise.ufl.edu 'cd project/; java peerProcess 1009 &'
