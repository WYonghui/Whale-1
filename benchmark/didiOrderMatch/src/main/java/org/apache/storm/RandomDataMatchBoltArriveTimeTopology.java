package org.apache.storm;

import org.apache.storm.report.ArriveTimeReportBolt;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

import static org.apache.storm.Constraints.*;

/**
 * locate org.apache.storm.starter
 * Created by mastertj on 2018/3/5.
 * DiDi滴滴打车订单匹配Topology
 * storm jar didiOrderMatch-2.0.0-SNAPSHOT.jar org.apache.storm.RandomDataMatchBoltArriveTimeTopology DiDiOrderMatchLatencyTopology ordersTopic 30 1 30 30 1000
 * storm jar didiOrderMatch-2.0.0-SNAPSHOT.jar org.apache.storm.RandomDataMatchBoltArriveTimeTopology DiDiOrderMatchLatencyTopology ordersTopic 7 1 120 1 1000
 */
public class RandomDataMatchBoltArriveTimeTopology {

    public static void main(String[] args) throws Exception{
        String topologyName=args[0];
        String topic=args[1];
        Integer numworkers=Integer.valueOf(args[2]);
        Integer spoutInstancesNum=Integer.valueOf(args[3]);
        Integer boltInstancesNum=Integer.valueOf(args[4]);
        Integer latencyInstancesNum=Integer.valueOf(args[5]);
        Long totalTuples=Long.valueOf(args[6]);
        TopologyBuilder builder=new TopologyBuilder();

        builder.setSpout(RANDOM_SPOTU_ID, new RandomDataSpout(totalTuples), spoutInstancesNum);
        builder.setBolt(DIDIMATCH_BOLT_ID, new DiDiMatchArriveTimeBolt(),boltInstancesNum).allGrouping(RANDOM_SPOTU_ID,SPOUT_STREAM_ID);
        builder.setBolt(DELAY_BOLT_ID,new ArriveTimeReportBolt(boltInstancesNum),latencyInstancesNum).fieldsGrouping(DIDIMATCH_BOLT_ID,new Fields("offset"));
        Config config=new Config();
        //config.setDebug(true);
        //config.setNumAckers(0);

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
