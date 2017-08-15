FROM phusion/baseimage

RUN apt-get update && apt-get -y install software-properties-common \
&& apt-get update \
&& add-apt-repository -y ppa:builds/sphinxsearch-beta \
&& apt-get update \
&& apt-get -y install nano \
&& apt-get -y install sphinxsearch nano wget \
&& mkdir /var/lib/sphinx \
&& mkdir /var/lib/sphinx/data \
&& mkdir /var/lib/sphinx/data/knigoed \
&& mkdir /usr/local/sphinx \
&& mkdir /var/log/sphinx \
&& mkdir /var/run/sphinx \
&& apt-get clean

ADD sphinx.conf /etc/sphinxsearch
ADD wordforms.txt /usr/local/sphinx
ADD init.sh /root
ADD indexer.sh /root
ADD restart.sh /root
ADD searchd.sh /root

RUN chmod a+x /root/init.sh \
&& chmod a+x /root/indexer.sh \
&& chmod a+x /root/searchd.sh \
&& chmod a+x /root/restart.sh \
&& ln -s /root/init.sh /etc/my_init.d/init.sh \
&& PATH=$PATH:/root

WORKDIR /usr/local/bin
RUN wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
&& chmod a+x wait-for-it.sh
ENTRYPOINT ["/bin/bash", "/usr/local/bin/wait-for-it.sh"]

#CMD ["/sbin/my_init"]