#!/usr/bin/env bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
JAR="$(basename $DIR)".jar

# lein uberjar && java -Djavax.net.ssl.trustStore=/Library/Java/JavaVirtualMachines/openjdk-11.jdk/Contents/Home/lib/security/cacerts -jar "target/$JAR"
lein uberjar && java -jar "target/$JAR"
