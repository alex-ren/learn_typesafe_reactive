Last Modified: 11/16/2014
Author: Zhiqiang Ren

This document describes how to start the web application using 
Amazon Web Service (AWS).

# ********* ********* ********* ********* #

Create EC2 instance. The instance is called server in the following document.
1. AMI used: Ubuntu Server 14.04 LTS (HVM), SSD Volume Type - ami-3d50120d

2. Logging:
Refer to ~/workspace/aws for further information.

# ********* ********* ********* ********* #

Please refer to document from AWS for details about login onto the server.

Server Configuration :

1. Install Java
$ sudo apt-get update
$ sudo apt-get install default-jre
$ sudo apt-get install default-jdk
$ java -version  # The default is OpenJDK 7

2. Install Activator
Download Activator to
~/Download/typesafe-activator-1.2.10.zip

Decompress the package to
~/Program/activator-1.2.10

2. Get the project for the web application from Github. The repository
goes as follows:
https://github.com/alex-ren/learn_typesafe_reactive.git

In the following text, the local repository is referred to as WEBSERVER.


# ********* ********* ********* ********* #

Examples:

1. Reactive woth Mongo

Please refer to 
$WEBSERVER/modern-web-template/Readme.txt
for details.

2. ConcurrentATS

Please refer to 
$WEBSERVER/conats/Readme.txt
for details.




