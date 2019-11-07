package org.apache.storm.multicast.core;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.multicast.model.BalancedPartialMulticastGraph;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseComponent;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static org.apache.storm.multicast.core.SchedulingTopologyBuilderTest.BroadcastBolt.BROADCAST_STREAM_ID;
import static org.apache.storm.multicast.core.SchedulingTopologyBuilderTest.TestSpout.SPOUT_STREAM_ID;

/**
 * locate org.apache.storm.multicast.core
 * Created by MasterTj on 2019/10/12.
 */
public class SchedulingTopologyBuilderTest {
    
    private  SchedulingTopologyBuilder builder= new SchedulingTopologyBuilder();

    public static class TestSpout extends BaseComponent implements IRichSpout {
        private SpoutOutputCollector collector;
        private Random random;
        private String[] sentences;
        public static final String SPOUT_STREAM_ID ="spoutstream";
        private int count=0;

        public TestSpout() {
        }

        @Override
        public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
            this.collector = collector;
            this.random = new Random();
            sentences = new String[]{
                    "the cow jumped over the moon",
                    "an apple a day keeps the doctor away",
                    "four score and seven years ago",
                    "snow white and the seven dwarfs",
                    "i am at two with nature"
            };
        }

        @Override
        public void close() {
        }

        @Override
        public void activate() {
        }

        @Override
        public void deactivate() {
        }

        @Override
        public void nextTuple() {
            final String sentence = sentences[random.nextInt(sentences.length)];
            count++;
            collector.emit(SPOUT_STREAM_ID, new Values(sentence), UUID.randomUUID());
        }

        @Override
        public void ack(Object msgId) {

        }

        @Override
        public void fail(Object msgId) {
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declareStream(SPOUT_STREAM_ID,new Fields("word"));
        }
    }

    public static class BroadcastBolt extends BaseRichBolt {
        public static final String BROADCAST_STREAM_ID ="broadcaststream";
        private OutputCollector collector;

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declareStream(BROADCAST_STREAM_ID,new Fields("word", "count"));
        }

        @Override
        public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
            this.collector= collector;
        }

        @Override
        public void execute(Tuple input) {
            String sentence = input.getString(0);
            for (String word : sentence.split("\\s+")) {
                collector.emit(BROADCAST_STREAM_ID, new Values(word, 1));
            }
        }
    }

    //TODO Local RUN
    @Test
    public void constructionBinomialTree() throws Exception {
        BalancedPartialMulticastGraph<String> dag = null;

        builder.setSpout("testSpout", new TestSpout());
        dag = builder.constructionBinomialTree(34, 10, "testSpout", SPOUT_STREAM_ID, BROADCAST_STREAM_ID, BroadcastBolt.class);
        System.out.println(BalancedPartialMulticastGraph.graphToJson(dag));
        System.out.println(dag.getElementByLayer(dag.getLayer()));

        String topologyName = "BinomialTree-Multicast";
        Config config = new Config();
        config.setDebug(false);
        config.setNumAckers(0);

        //Multicast Configuration
        config.put(Config.TOPOLOGY_MULTICAST_SOURCE_STREMAID,SPOUT_STREAM_ID);
        config.put(Config.TOPOLOGY_MULTICAST_BROADCASTBOLT_STREMAID,BROADCAST_STREAM_ID);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(topologyName, config, builder.createTopology());
        Utils.sleep(1000 * 30);
        cluster.killTopology(topologyName);
        cluster.shutdown();
    }

    @Test
    public void constructionBalancedPartialMulticast() throws Exception {
        BalancedPartialMulticastGraph<String> dag = null;

        builder.setSpout("testSpout", new TestSpout());
        dag = builder.constructionBalancedPartialMulticast(34, 10,  2,"testSpout", SPOUT_STREAM_ID, BROADCAST_STREAM_ID, BroadcastBolt.class);

        System.out.println(dag.getElementByLayer(dag.getLayer()));

        String topologyName = "BalancedPartial-Multicast";
        Config config = new Config();
        config.setDebug(false);
        config.setNumAckers(0);

        //Multicast Configuration
        config.put(Config.TOPOLOGY_MULTICAST_SOURCE_STREMAID,SPOUT_STREAM_ID);
        config.put(Config.TOPOLOGY_MULTICAST_BROADCASTBOLT_STREMAID,BROADCAST_STREAM_ID);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(topologyName, config, builder.createTopology());
        Utils.sleep(1000 * 30);
        cluster.killTopology(topologyName);
        cluster.shutdown();
    }

    @Test
    public void constructionChain() throws Exception {
        BalancedPartialMulticastGraph<String> dag = null;

        builder.setSpout("testSpout", new TestSpout());
        dag = builder.constructionChain(34, 10,"testSpout", SPOUT_STREAM_ID, BROADCAST_STREAM_ID, BroadcastBolt.class);

        System.out.println(dag.getElementByLayer(dag.getLayer()));

        String topologyName = "Chain-Multicast";
        Config config = new Config();
        config.setDebug(false);
        config.setNumAckers(0);

        //Multicast Configuration
        config.put(Config.TOPOLOGY_MULTICAST_SOURCE_STREMAID,SPOUT_STREAM_ID);
        config.put(Config.TOPOLOGY_MULTICAST_BROADCASTBOLT_STREMAID,BROADCAST_STREAM_ID);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(topologyName, config, builder.createTopology());
        Utils.sleep(1000 * 30);
        cluster.killTopology(topologyName);
        cluster.shutdown();
    }

    @Test
    public void constructionSequential() throws Exception {
        BalancedPartialMulticastGraph<String> dag = null;

        builder.setSpout("testSpout", new TestSpout());
        dag = builder.constructionSequential(34, 10,"testSpout", SPOUT_STREAM_ID, BroadcastBolt.class);

        System.out.println(dag.getElementByLayer(dag.getLayer()));

        String topologyName = "Sequential-Multicast";
        Config config = new Config();
        config.setDebug(false);
        config.setNumAckers(0);

        //Multicast Configuration
        config.put(Config.TOPOLOGY_MULTICAST_SOURCE_STREMAID,SPOUT_STREAM_ID);
        config.put(Config.TOPOLOGY_MULTICAST_BROADCASTBOLT_STREMAID,BROADCAST_STREAM_ID);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(topologyName, config, builder.createTopology());
        Utils.sleep(1000 * 30);
        cluster.killTopology(topologyName);
        cluster.shutdown();
    }

    @Test
    public void constructionKBinomialTree() throws Exception {
    }

}
