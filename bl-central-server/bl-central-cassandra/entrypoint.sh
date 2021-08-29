#!/bin/bash

cd apache-cassandra-4.0.0 || exit
cd conf
#sed -i 's/listen_address: localhost/listen_address: '$(hostname -I | xargs)'/g' cassandra.yaml
sed -i 's/rpc_address: localhost/rpc_address: '$(hostname -I | xargs)'/g' cassandra.yaml
cd ..
bin/cassandra -R && bin/nodetool enablebinary

tail -f /dev/null
