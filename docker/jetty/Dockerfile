# https://hub.docker.com/_/jetty/
# /usr/local/jetty - etc, bin, lib
# /var/lib/jetty - lib, webapps
FROM jetty:9.4-jre8

RUN apt-get update && apt-get -y install wget && apt-get -y install nano && apt-get clean

#ADD jetty-deploy.xml /usr/local/jetty/etc/mysql/conf.d/

WORKDIR /usr/local/bin
RUN wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
&& chmod +x wait-for-it.sh
ENTRYPOINT ["/bin/bash", "/usr/local/bin/wait-for-it.sh"]

WORKDIR $JETTY_BASE
