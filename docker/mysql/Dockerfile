# https://hub.docker.com/_/mysql/
FROM mysql:5.6
RUN apt-get update && apt-get -y install nano

ADD mysql.cnf /etc/mysql/conf.d/
ADD knigoed.sql /docker-entrypoint-initdb.d