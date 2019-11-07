package org.apache.storm;

import org.apache.storm.multicast.model.MulticastID;
import org.apache.storm.generated.Grouping;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * locate org.apache.storm
 * Created by mastertj on 2018/3/6.
 */
public class DiDiMatchBinomialTreeBolt extends BaseRichBolt {
    private static Logger logger= LoggerFactory.getLogger(DiDiMatchBinomialTreeBolt.class);
    private Set<String> drivers=new HashSet<>();
    private Random random=new Random();

    private Timer timer;
    private int thisTaskId =0;
    private long boltInstancesNum = 0L;
    private long tuplecount=0; //记录单位时间ACK的元组数量
    private Map<String, Map<String, Grouping>> thisTargets;
    private MulticastID multicastMark;
    private OutputCollector outputCollector;
    private static final String THROUGHPUT_STREAM_ID ="throughputstream";
    private static final String BROADCAST_STREAM_ID ="broadcaststream";

    private void waitForNanoSeconds(long nanoSeconds){
        Long startNanoSeconds=System.nanoTime();
        while (true){
            Long endNanoSeconds=System.nanoTime();
            if(endNanoSeconds-startNanoSeconds>=nanoSeconds)
                break;
        }
    }

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.outputCollector = collector;

        this.thisTaskId = context.getThisTaskId();
        this.boltInstancesNum = (long) topoConf.get("boltInstancesNum");
        this.thisTargets = context.getThisTargets();
        this.multicastMark= new MulticastID(context.getThisComponentId());
        logger.info("multicastMark: "+multicastMark+", thisTargets:"+ thisTargets);
    }

    @Override
    public void execute(Tuple input) {
        String topic=input.getStringByField("topic");
        Integer partition=input.getIntegerByField("partition");
        Long offset=input.getLongByField("offset");
        String key=input.getStringByField("key");
        String value=input.getStringByField("value");
        tuplecount++;
        //simulation match function
        //waitForTimeMills(2);
        if(thisTargets.size() > 0)
            outputCollector.emit(BROADCAST_STREAM_ID,new Values(topic,partition,offset,key,value));

        outputCollector.ack(input);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream(BROADCAST_STREAM_ID,new Fields("topic","partition","offset","key","value"));
    }

    public class Order{
        private String orderId;
        private String time;
        private String matchDriverId;

        public Order() {
        }

        public Order(String orderId, String time, String matchDriverId) {
            this.orderId = orderId;
            this.time = time;
            this.matchDriverId = matchDriverId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMatchDriverId() {
            return matchDriverId;
        }

        public void setMatchDriverId(String matchDriverId) {
            this.matchDriverId = matchDriverId;
        }

        @Override
        public String toString() {
            return "Orders{" +
                    "orderId='" + orderId + '\'' +
                    ", time='" + time + '\'' +
                    ", matchDriverId='" + matchDriverId + '\'' +
                    '}';
        }
    }
}
