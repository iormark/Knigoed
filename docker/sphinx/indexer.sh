#!/bin/bash
#sleep 10
/usr/bin/indexer -c /etc/sphinxsearch/sphinx.conf --rotate --all
/root/restart.sh