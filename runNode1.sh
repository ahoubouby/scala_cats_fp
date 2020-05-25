#!/usr/bin/env bash

portDefault=2551
hostDefault=127.0.0.1
echo "running seed node at port ${1:-$portDefault} and host $hostDefault"
set -x
sbt "run -DPORT=${1:-$portDefault} -DHOST=$hostDefault -Dconfig.resource=seed.conf"
