package org.apache.storm;

import org.apache.storm.multicast.core.SchedulingTopologyBuilder;
import org.apache.storm.report.ThroughputReportBolt;
import org.apache.storm.utils.Utils;

import static org.apache.storm.Constraints.*;

/**
 * locate org.apache.storm.starter
 * Created by mastertj on 2019/5/21.
 * RandomDataRDMABenchTopology
 * storm jar didiOrderMatchWhale-2.0.0-SNAPSHOT.jar org.apache.storm.RandomDataRDMABenchTopology randomDataRDMABenchTopology 19 1 1 rdmabench 800 4096
 * storm jar didiOrderMatchWhale-2.0.0-SNAPSHOT.jar org.apache.storm.RandomDataRDMABenchTopology randomDataRDMABenchTopology 19 6 6 rdmabench 800 4096 8192 8192
 */
public class RandomDataRDMABenchTopology {

    public static void main(String[] args) throws Exception{
        String topologyName=args[0];
        Integer numworkers=Integer.valueOf(args[1]);
        Integer spoutInstancesNum=Integer.valueOf(args[2]);
        Integer boltInstancesNum=Integer.valueOf(args[3]);
        String transport = args[4];
        Integer sendTimeLimit=Integer.valueOf(args[5]);
        Integer messageBatchSize=Integer.valueOf(args[6]);
        Integer executroSendBufferSize=Integer.valueOf(args[7]);
        Integer transferBufferSize=Integer.valueOf(args[8]);
        SchedulingTopologyBuilder builder=new SchedulingTopologyBuilder();

        builder.setSpout(RDNAOM_SPOUT_ID, new RandomDataSpout(), spoutInstancesNum);
        builder.setBolt(GATHER_BOLT_ID,new RandomDataGatherBolt(),boltInstancesNum).allGrouping(RDNAOM_SPOUT_ID,SPOUT_STREAM_ID);
        builder.setBolt(THROUGHPUT_BOLT_ID, new ThroughputReportBolt(),1).shuffleGrouping(RDNAOM_SPOUT_ID,THROUGHPUT_STREAM_ID);
        Config config=new Config();
        //config.setDebug(true);
        config.setNumAckers(0);
        config.put(Config.STORM_MESSAGING_TRANSPORT,"org.apache.storm.messaging."+transport+".Context");
        config.put(Config.STORM_MESSAGING_RDMA_SEND_LIMIT_TIME,sendTimeLimit);
        config.put(Config.STORM_RDMA_MESSAGE_BATCH_SIZE,messageBatchSize);
        config.put(Config.TOPOLOGY_EXECUTOR_SEND_BUFFER_SIZE,executroSendBufferSize);
        config.put(Config.TOPOLOGY_TRANSFER_BUFFER_SIZE,transferBufferSize);
        if(args!=null && args.length <= 0){
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(topologyName, config, builder.createTopology());
            Utils.sleep(1000 * 50);
            cluster.killTopology(topologyName);
            cluster.shutdown();
        }else {
            config.setNumWorkers(numworkers);//每一个Worker 进程默认都会对应一个Acker 线程
            StormSubmitter.submitTopology(topologyName,config,builder.createTopology());
        }
    }

}
