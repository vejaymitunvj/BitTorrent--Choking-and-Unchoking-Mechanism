#!/bin/bash
echo "Deleting compiled files..."
rm -rf *.class
echo "Cleaning log files...."
rm -rf log_peer_*
rm -rf .log_peer_*
echo "Deleting peer folders...."
rm -rf peer_100*
echo "Killing any stray processes that might conflict with our program..."
ssh -t lin114-00.cise.ufl.edu 'killall --user $USER java'
ssh -t lin114-01.cise.ufl.edu 'killall --user $USER java'
ssh -t lin114-02.cise.ufl.edu 'killall --user $USER java'
ssh -t lin114-03.cise.ufl.edu 'killall --user $USER java'
ssh -t lin114-04.cise.ufl.edu 'killall --user $USER java'
ssh -t lin114-05.cise.ufl.edu 'killall --user $USER java'
ssh -t lin114-06.cise.ufl.edu 'killall --user $USER java'
ssh -t lin114-07.cise.ufl.edu 'killall --user $USER java'
ssh -t lin114-08.cise.ufl.edu 'killall --user $USER java'
