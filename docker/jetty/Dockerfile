# https://hub.docker.com/_/jetty/
FROM jetty:9.4-jre8

RUN apt-get update && apt-get -y install wget && apt-get clean

WORKDIR /usr/local/bin
RUN wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
&& chmod +x wait-for-it.sh
ENTRYPOINT ["/bin/bash", "/usr/local/bin/wait-for-it.sh"]

WORKDIR $JETTY_BASE
