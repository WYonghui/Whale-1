package org.apache.storm.report;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.util.DataBaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 79875 on 2017/3/7.
 * 用来统计DiDiMatchBolt 到达时间延迟
 */
public class ArriveTimeReportBolt extends BaseRichBolt {
    private static Logger LOG= LoggerFactory.getLogger(ArriveTimeReportBolt.class);

    private OutputCollector outputCollector;

    private HashMap<Long,ArrayList<Long>> tupleModelMap;

    private int upStreamBoltInstancesNum;

    public ArriveTimeReportBolt(int upStreamBoltInstancesNum) {
        this.upStreamBoltInstancesNum = upStreamBoltInstancesNum;
    }

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector){
        this.outputCollector=outputCollector;
        this.tupleModelMap=new HashMap<>();
        LOG.info("------------ArriveTimeReportBolt prepare------------");
    }

    public void execute(Tuple tuple) {
        //将最后结果输出到数据库文件中
        try {
            Integer taskid = tuple.getIntegerByField("taskid");
            Long offset = tuple.getLongByField("offset");
            Long delay = tuple.getLongByField("delay");
            Long startTime = tuple.getLongByField("startTime");

            if(tupleModelMap.get(offset)==null){
                ArrayList<Long> arrayList=new ArrayList();
                arrayList.add(delay);
                tupleModelMap.put(offset,arrayList);
            }else {
                tupleModelMap.get(offset).add(delay);
            }

            if(tupleModelMap.get(offset).size()==upStreamBoltInstancesNum){
                ArrayList<Long> list = tupleModelMap.get(offset);
                Collections.sort(list);
                Long maxdelay = list.get(list.size() - 1);
                Long mindelay = list.get(0);
                Long middledelay = 0L;
                if(list.size() % 2!=0) {
                    middledelay = list.get(list.size() / 2);
                }else {
                    int tmp = list.size() / 2;
                    middledelay = (list.get(tmp)+list.get(tmp-1))/2;
                }
                DataBaseUtil.insertDiDiDelay(offset, maxdelay, mindelay, middledelay,new Timestamp(System.currentTimeMillis()));
                tupleModelMap.remove(offset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public void cleanup() {
    }

}
