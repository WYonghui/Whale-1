package org.apache.storm;

import org.apache.storm.benchmark.BenchBolt;
import org.apache.storm.generated.Grouping;
import org.apache.storm.multicast.model.MulticastID;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.apache.storm.Constraints.LATENCY_STREAM_ID;
import static org.apache.storm.Constraints.START_TIME_MILLS;
import static org.apache.storm.Constraints.THROUGHPUT_STREAM_ID;

/**
 * locate org.apache.storm
 * Created by mastertj on 2018/3/6.
 */
public class DiDiMatchBenchBolt extends BenchBolt {
    private static Logger logger= LoggerFactory.getLogger(DiDiMatchBenchBolt.class);
    private Set<String> drivers=new HashSet<>();
    private Random random=new Random();

    private int thisTaskId = 0;
    private Long boltInstancesNum = 0L;
    private Map<String, Map<String, Grouping>> thisTargets;
    private MulticastID multicastID;
    private OutputCollector outputCollector;
    private static final String BROADCAST_STREAM_ID ="broadcaststream";

    public DiDiMatchBenchBolt() {
        super(1000, 1000);
    }

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
        super.prepare(topoConf,context,collector);

        this.outputCollector=collector;
        this.thisTaskId = context.getThisTaskId();
        this.boltInstancesNum = (Long) topoConf.get(Constraints.BOLT_INSTANCES_NUM);
        this.thisTargets = context.getThisTargets();
        this.multicastID = new MulticastID(context.getThisComponentId());

        if(!thisTargets.containsKey(THROUGHPUT_STREAM_ID) && !thisTargets.containsKey(LATENCY_STREAM_ID)){
            this.getLatencyTimer().cancel();
            this.getThroughputTimer().cancel();
        }
    }

    @Override
    public void execute(Tuple input) {
        Long startTimeMills=input.getLongByField(START_TIME_MILLS);
        String topic=input.getStringByField("topic");
        Integer partition=input.getIntegerByField("partition");
        Long offset=input.getLongByField("offset");
        String key=input.getStringByField("key");
        String value=input.getStringByField("value");

        super.nextThroughputTuple(input);
        if(thisTargets.containsKey(BROADCAST_STREAM_ID))
            outputCollector.emit(BROADCAST_STREAM_ID,new Values(topic,partition,offset,key,value,startTimeMills));

        //simulation didiOrder-match function
        if(thisTargets.containsKey(THROUGHPUT_STREAM_ID) && thisTargets.get(THROUGHPUT_STREAM_ID).containsKey(Constraints.BENCH_BOLT_ID)
                && thisTargets.containsKey(LATENCY_STREAM_ID) && thisTargets.get(LATENCY_STREAM_ID).containsKey(Constraints.BENCH_BOLT_ID)) {
            long nanoSeconds = Constraints.DIDI_MATCH_LATECNY * Constraints.DIDI_MATCH_LINE / boltInstancesNum;
            long latency= System.currentTimeMillis()-startTimeMills;

            logger.debug("waitForNanoSeconds:{}, startTimeMills:{} ,latency:{}",nanoSeconds, startTimeMills, latency);
            waitForNanoSeconds(nanoSeconds);
        }
        super.nextLatencyTuple(input);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        super.declareOutputFields(declarer);
        declarer.declareStream(BROADCAST_STREAM_ID,new Fields("topic","partition","offset","key","value", START_TIME_MILLS));
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
