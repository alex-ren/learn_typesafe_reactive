Last Modified: 11/16/2014
Author: Zhiqiang Ren

This document describes how to start the web application using 
Amazon Web Service (AWS).

# ********* ********* ********* ********* #

Create EC2 instance. The instance is called server in the following document.
1. AMI used: Ubuntu Server 14.04 LTS (HVM), SSD Volume Type - ami-3d50120d


# ********* ********* ********* ********* #
Please refer to document from AWS for details about login onto the server.

Server Configuration :

1. Install Java
$ sudo apt-get update
$ sudo apt-get install default-jre
$ sudo apt-get install default-jdk
$ java -version  # The default is OpenJDK 7

2. Install MongoDB
Download MongoDB Production Release (2.6.5) to
~/Download/mongodb-linux-x86_64-2.6.5.tgz

Decompress the package to
~/Program/mongodb-linux-x86_64-2.6.5

Create directory ~/data/db

Run the MongoDB by executing the following command.
$ ~/Program/mongodb-linux-x86_64-2.6.5/bin/mongod --dbpath ~/data/db 

3. Install Activator
Download Activator to
~/Download/typesafe-activator-1.2.10.zip

Decompress the package to
~/Program/activator-1.2.10

4. Get the project for the web application from Github. The repository
goes as follows:
https://github.com/alex-ren/learn_typesafe_reactive.git

In the following text, the local repository is referred to as WEBSERVER.
In my case, WEBSERVER is ~/workspace/learn_typesafe_reactive/modern-web-template

5. Run the web server
$ cd WEBSERVER
$ sudo ./activator  # Must have root privilege to bind port 80
[modern-web-template] $ start 80 -Dapplication.secret=abc

# ********* ********* ********* ********* #

Testing the web server

Visit the following web address:
http://54.148.139.136

# ********* ********* ********* ********* #










