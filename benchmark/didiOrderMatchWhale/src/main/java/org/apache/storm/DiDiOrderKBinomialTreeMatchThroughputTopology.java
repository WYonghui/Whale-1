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
 * storm jar didiOrderMatchWhale-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderKBinomialTreeMatchThroughputTopology DiDiOrderMatchThroughputTopology ordersTopic 29 1 3 rdma 800
 * storm jar didiOrderMatchWhale-2.0.0-SNAPSHOT.jar org.apache.storm.DiDiOrderKBinomialTreeMatchThroughputTopology DiDiOrderMatchThroughputTopology ordersTopic 7 1 3 rdma 800
 */
public class DiDiOrderKBinomialTreeMatchThroughputTopology {

    public static void main(String[] args) throws Exception{
        String topologyName=args[0];
        String topic=args[1];
        Integer numworkers=Integer.valueOf(args[2]);
        Integer spoutInstancesNum=Integer.valueOf(args[3]);
        Integer boltInstancesNum=Integer.valueOf(args[4]);
        Integer k=Integer.valueOf(args[5]);
        String transport = args[6];
        Integer sendTimeLimit=Integer.valueOf(args[7]);
        SchedulingTopologyBuilder builder=new SchedulingTopologyBuilder();

        int mod=numworkers % k;
        int res=numworkers / k;
        KafkaSpoutConfig<String, String> kafkaSpoutConfig = getKafkaSpoutConfig(KAFKA_LOCAL_BROKER, topic);

        for (int i = 1; i <= k; i++) {
            SpoutDeclarer spoutDeclarer = builder.setSpout(KAFKA_SPOUT_ID+"-"+i, new DiDiOrdersSpout<>(kafkaSpoutConfig), spoutInstancesNum);
            spoutDeclarer.setCPULoad(100);
            int KNums=res;
            if(mod>0){
                KNums++;
                mod--;
            }
            //TODO 考虑boltInstancesNum 不能整除的情形
            builder.constructionKBinomialTree(i, boltInstancesNum/k, KNums,KAFKA_SPOUT_ID+"-"+i,SPOUT_STREAM_ID,BROADCAST_STREAM_ID,DiDiMatchBinomialTreeThroughputBolt.class);

            builder.setBolt(THROUGHPUT_BOLT_ID+"-"+i, new ThroughputReportBolt(),1).shuffleGrouping(i+"-"+ Constraints.BROADCAST_BOLT+"-"+KNums,THROUGHPUT_STREAM_ID);
        }

        Config config=new Config();
        //config.setDebug(true);
        config.setNumAckers(0);
        config.put(Config.STORM_MESSAGING_TRANSPORT,"org.apache.storm.messaging."+transport+".Context");
        config.put(Config.STORM_MESSAGING_RDMA_SEND_LIMIT_TIME,sendTimeLimit);
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
