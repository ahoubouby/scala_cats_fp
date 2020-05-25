#!/usr/bin/env bash

portDefault=2554
hostDefault=127.0.0.1
echo "running master node at port ${1:-$portDefault} and host ${2:-$hostDefault}"
set -x
sbt "run -DPORT=${1:-$portDefault} -DHOST=${2:-$hostDefault}-Dconfig.resource=master.conf"
