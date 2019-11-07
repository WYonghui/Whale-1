#!/bin/bash
# DiDiOrderSequentialMatchThroughputTopology
storm jar didiOrderMatch-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderSequentialMatchThroughputTopology DiDiOrderMatchThroughputTopology ordersTopic6 26 1 25 rdma 50
storm jar didiOrderMatch-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderSequentialMatchThroughputTopology DiDiOrderMatchThroughputTopology ordersTopic6 26 1 480 rdma 50

# DiDiOrderBinomialTreeMatchThroughputTopology
storm jar didiOrderMatch-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderBinomialTreeMatchThroughputTopology DiDiOrderMatchThroughputTopology ordersTopic6 26 1 25 rdma 1
storm jar didiOrderMatch-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderBinomialTreeMatchThroughputTopology DiDiOrderMatchThroughputTopology ordersTopic6 26 1 480 rdma 1

# DiDiOrderBalancedParitaMatchThroughputTopology
storm jar didiOrderMatch-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderBalancedParitaMatchThroughputTopology DiDiOrderMatchThroughputTopology ordersTopic 26 1 25 3 rdma 1
storm jar didiOrderMatch-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderBalancedParitaMatchThroughputTopology DiDiOrderMatchThroughputTopology ordersTopic 26 1 400 3 rdma 1

# Mysql Command
delete from t_throughput;
delete from t_throughput where throughput < 20000;
select avg(throughput) from t_throughput;
