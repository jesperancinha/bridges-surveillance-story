#!/bin/bash
touch /etc/ld.so.conf.d/cassandra.conf

systemctl enable cassandra.service
/etc/init.d/cassandra start
rm -rf /var/lib/cassandra/data/system/*
#sed -i 's/listen_address: localhost/listen_address: '$(hostname -I | xargs)'/g' /etc/cassandra/conf/cassandra.yaml
sed -i 's/rpc_address: localhost/rpc_address: '$(hostname -I | xargs)'/g' /etc/cassandra/conf/cassandra.yaml
sed -i 's/cluster_name: ''Test Cluster''/cluster_name: ''dc1''/g' /etc/cassandra/conf/cassandra.yaml
nodetool enablebinary
sleep 5
cassandra &
nodetool enablebinary
nodetool upgradesstables
sleep 5
cassandra
tail -f /dev/null
