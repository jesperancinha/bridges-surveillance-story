#!/bin/bash
touch /etc/ld.so.conf.d/cassandra.conf

#systemctl enable cassandra.service
#sed -i 's/listen_address: localhost/listen_address: '$(hostname -I | xargs)'/g' /etc/cassandra/conf/cassandra.yaml
sed -i 's/rpc_address: localhost/rpc_address: '$(hostname -I | xargs)'/g' /etc/cassandra/conf/cassandra.yaml
cassandra
nodetool enablebinary

tail -f /dev/null
