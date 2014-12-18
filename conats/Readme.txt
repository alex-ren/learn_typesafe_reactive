Author: Zhiqiang Ren

Prepare the running environment for the web server:

1. Install ATS-Postiats (0.1.3)


2. Install mono
>> sudo apt-get install mono-complete

3. Install PAT


# ********* ********* ********* ********* #

Run the web server:

1. Make sure the environment variables PATSHOME, PATSHOMERELOC, PATH 
are set correctly. The web server needs a special environment variable
for PAT indicating the path for the main program.
$ PAT3=~/programs/tempPAT/PAT3.Console.exe; export PAT3

2. To start the web server, we need sudo to open port 80. "-E" and "env" are
for retaining the environment variables.
$ sudo -E env PATH=$PATH ./activator
[conats] $ run 80

Testing the web server

Visit the following web address:
http://54.149.186.200

# ********* ********* ********* ********* #

