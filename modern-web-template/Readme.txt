Author: Zhiqiang Ren

Prepare the running environment for the web server:

1. Install MongoDB
Download MongoDB Production Release (2.6.5) to
~/Download/mongodb-linux-x86_64-2.6.5.tgz

Decompress the package to
~/programs/mongodb-linux-x86_64-2.6.5

Create directory ~/data/db

Run the MongoDB by executing the following command.
$ ~/programs/mongodb-linux-x86_64-2.6.5/bin/mongod --dbpath ~/data/db 


# ********* ********* ********* ********* #

Run the web server:

$ sudo ./activator  # Must have root privilege to bind port 80
[modern-web-template] $ start 80 -Dapplication.secret=abc

# ********* ********* ********* ********* #

Testing the web server

Visit the following web address:
http://54.148.139.136

# ********* ********* ********* ********* #










