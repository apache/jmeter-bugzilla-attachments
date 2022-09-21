#!/bin/sh

. choose-java.sh

JPAR="-JHOSTSM=xxx -JHOST=xxx -JPROTOCOL=https -JNUM_USERS=50 -JNUM_COUNTS=1"

../bin/jmeter $JPAR -t ../../plans/xx.jmx
