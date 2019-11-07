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
 * storm jar didiOrderMatchWhale-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderSequentialMatchRDMABenchTopology DiDiOrderMatchThroughputTopology ordersTopic 19 1 19 rdmabench 800 4096
 * storm jar didiOrderMatchWhale-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderSequentialMatchRDMABenchTopology DiDiOrderMatchThroughputTopology ordersTopic6 19 6 10 rdmabench 800 4096
 */
public class DiDiOrderSequentialMatchRDMABenchTopology {

    public static void main(String[] args) throws Exception{
        String topologyName=args[0];
        String topic=args[1];
        Integer numworkers=Integer.valueOf(args[2]);
        Integer spoutInstancesNum=Integer.valueOf(args[3]);
        Integer broadcastInstancesNum=Integer.valueOf(args[4]);
        String transport = args[5];
        Integer sendTimeLimit=Integer.valueOf(args[6]);
        Integer messageBatchSize=Integer.valueOf(args[7]);
        Integer executroSendBufferSize=Integer.valueOf(args[8]);
        Integer transferBufferSize=Integer.valueOf(args[9]);
        SchedulingTopologyBuilder builder=new SchedulingTopologyBuilder();

        SpoutDeclarer spoutDeclarer = builder.setSpout(KAFKA_SPOUT_ID, new DiDiOrdersSpout<>(getKafkaSpoutConfig(KAFKA_LOCAL_BROKER, topic)), spoutInstancesNum);

        builder.constructionSequential(broadcastInstancesNum, numworkers-1,KAFKA_SPOUT_ID,SPOUT_STREAM_ID,DiDiMatchBinomialTreeThroughputBolt.class);

        builder.setBolt(THROUGHPUT_BOLT_ID, new ThroughputReportBolt(),1).shuffleGrouping(Constraints.BROADCAST_BOLT+"-"+broadcastInstancesNum,THROUGHPUT_STREAM_ID);
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
