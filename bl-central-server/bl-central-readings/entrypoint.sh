#!/usr/bin/env sh

java --add-exports=java.base/jdk.internal.ref=ALL-UNNAMED --add-exports=java.base/sun.nio.ch=ALL-UNNAMED -jar bl-passengers-reading.jar demo &

java --add-exports=java.base/jdk.internal.ref=ALL-UNNAMED --add-exports=java.base/sun.nio.ch=ALL-UNNAMED -jar bl-meters-readings-service.jar demo &

tail -f /dev/null
