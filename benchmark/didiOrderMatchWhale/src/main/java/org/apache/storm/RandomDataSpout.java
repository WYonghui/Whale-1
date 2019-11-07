package org.apache.storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * locate org.apache.storm
 * Created by MasterTj on 2019/5/21.
 */
public class RandomDataSpout extends BaseRichSpout{
    private static Logger logger= LoggerFactory.getLogger(RandomDataSpout.class);
    public static final String SPOUT_STREAM_ID ="spout_stream";
    public static final String THROUGHPUT_STREAM_ID ="throughputstream";
    private SpoutOutputCollector outputCollector;
    private Timer timer;
    private long tuplecount=0; //记录单位时间ACK的元组数量
    private int thisTaskId =0;

    private String getRandomOredersData(){
        String ordaersData="8c9dcba50533612757f195d6e0540b54,1480331793,f07cd8e357a05434a4650459bfa180c3";
        return ordaersData;
    }

    //初始化操作
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        logger.info("------------SentenceThroughputSpout open------------");
        this.outputCollector=spoutOutputCollector;
        thisTaskId=topologyContext.getThisTaskId();

        this.timer=new Timer();
        //设置计时器没1s计算时间
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                //将最后结果输出到日志文件中
                outputCollector.emit(THROUGHPUT_STREAM_ID,new Values(tuplecount,System.currentTimeMillis(),thisTaskId));
                tuplecount = 0;
            }
        }, 1,1000);// 设定指定的时间time,此处为1000毫秒
    }

    //向下游输出
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declareStream(SPOUT_STREAM_ID,new Fields("topic", "partition", "offset", "key", "value"));
        outputFieldsDeclarer.declareStream(THROUGHPUT_STREAM_ID,new Fields("tuplecount","timeinfo","taskid"));
    }

    //核心逻辑
    public void nextTuple() {
        String topic="ordersTopic";
        String partition="ordersTopic-0";
        Long offset=0L;
        String key="null";
        String value=getRandomOredersData();
        //System.out.println(value);
        tuplecount++;
        outputCollector.emit(SPOUT_STREAM_ID,new Values(topic,partition,offset,key,value));
    }

    //Storm 的消息ack机制
    @Override
    public void ack(Object msgId) {
        super.ack(msgId);
    }

    @Override
    public void fail(Object msgId) {
        super.fail(msgId);
    }

    @Override
    public void close() {
    }

}
