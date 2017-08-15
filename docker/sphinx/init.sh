#!/bin/bash
#sleep 10
/usr/bin/indexer -c /etc/sphinxsearch/sphinx.conf --all
/root/searchd.sh