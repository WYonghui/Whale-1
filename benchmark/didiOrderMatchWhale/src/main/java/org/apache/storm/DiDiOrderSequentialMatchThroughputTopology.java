package org.apache.storm;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.multicast.core.SchedulingTopologyBuilder;
import org.apache.storm.kafka.spout.ByTopicRecordTranslator;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff;
import org.apache.storm.kafka.spout.KafkaSpoutRetryService;
import org.apache.storm.report.ThroughputReportBolt;
import org.apache.storm.topology.SpoutDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import static org.apache.storm.Constraints.*;
import static org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy.EARLIEST;

/**
 * locate org.apache.storm.starter
 * Created by mastertj on 2018/3/5.
 * DiDi滴滴打车订单匹配Topology
 * storm jar didiOrderMatchWhale-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderSequentialMatchThroughputTopology DiDiOrderMatchThroughputTopology ordersTopic 29 1 1024
 * storm jar didiOrderMatchWhale-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderSequentialMatchThroughputTopology DiDiOrderMatchThroughputTopology ordersTopic 7 1 1024
 */
public class DiDiOrderSequentialMatchThroughputTopology {

    public static void main(String[] args) throws Exception{
        String topologyName=args[0];
        String topic=args[1];
        Integer numworkers=Integer.valueOf(args[2]);
        Integer spoutInstancesNum=Integer.valueOf(args[3]);
        Integer boltInstancesNum=Integer.valueOf(args[4]);
        Integer batchSize = Integer.valueOf(args[5]);
        SchedulingTopologyBuilder builder=new SchedulingTopologyBuilder();

        SpoutDeclarer spoutDeclarer = builder.setSpout(KAFKA_SPOUT_ID, new DiDiOrdersSpout<>(getKafkaSpoutConfig(KAFKA_LOCAL_BROKER, topic)), spoutInstancesNum);
        spoutDeclarer.setCPULoad(100);
        builder.constructionSequential(numworkers,boltInstancesNum,KAFKA_SPOUT_ID,SPOUT_STREAM_ID,DiDiMatchBinomialTreeThroughputBolt.class);

        builder.setBolt(THROUGHPUT_BOLT_ID, new ThroughputReportBolt(),1).shuffleGrouping(Constraints.BROADCAST_BOLT+"-"+numworkers,THROUGHPUT_STREAM_ID);
        Config config=new Config();
        //config.setDebug(true);
        config.setNumAckers(0);
        config.put("Config.STORM_NETTY_MESSAGE_BATCH_SIZE",batchSize);
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

    public static KafkaSpoutConfig<String, String> getKafkaSpoutConfig(String bootstrapServers,String topic) {
        ByTopicRecordTranslator<String, String> trans = new ByTopicRecordTranslator<>(
                (r) -> new Values(r.topic(), r.partition(), r.offset(), r.key(), r.value()),
                new Fields("topic", "partition", "offset", "key", "value"), SPOUT_STREAM_ID);
//        trans.forTopic(TOPIC_2,
//                (r) -> new Values(r.topic(), r.partition(), r.offset(), r.key(), r.value()),
//                new Fields("topic", "partition", "offset", "key", "value"), TOPIC_2_STREAM);
        return KafkaSpoutConfig.builder(bootstrapServers, new String[]{topic})
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, "DiDiOrderMatchThroughputGroup1")
                .setRetry(getRetryService())
                .setRecordTranslator(trans)
                .setProcessingGuarantee(KafkaSpoutConfig.ProcessingGuarantee.AT_MOST_ONCE)
                .setFirstPollOffsetStrategy(EARLIEST)
                .build();
    }

    public static KafkaSpoutRetryService getRetryService() {
        return new KafkaSpoutRetryExponentialBackoff(KafkaSpoutRetryExponentialBackoff.TimeInterval.microSeconds(0),
                KafkaSpoutRetryExponentialBackoff.TimeInterval.milliSeconds(2), Integer.MAX_VALUE, KafkaSpoutRetryExponentialBackoff.TimeInterval.seconds(10));
    }
}
