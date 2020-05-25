#!/usr/bin/env bash

set -x
sbt "run -DPORT=2551 -DHOST=127.0.0.1 -Dconfig.resource=/seed.conf"
