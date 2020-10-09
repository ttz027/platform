package com.platform.utils.quartz;

import com.alipay.api.domain.Activity;
import com.platform.entity.ActivityLotteryEntity;
import com.platform.entity.ActivityLotteryJoinEntity;
import com.platform.entity.ActivityLotteryResultEntity;
import com.platform.entity.GoodsEntity;
import com.platform.service.ActivityLotteryJoinService;
import com.platform.service.ActivityLotteryResultService;
import com.platform.service.ActivityLotteryService;
import com.platform.service.GoodsService;
import com.platform.utils.SpringContextHolder;
import com.platform.utils.SpringContextUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;

/**
 * @Author ztt
 * @Description
 * @Date 2020/9/25 0025 下午 4:07
 */
public class AcitivtyTask implements Job {

    private ActivityLotteryService activityLotteryService = SpringContextHolder.getBean(ActivityLotteryService.class);
    private ActivityLotteryJoinService activityLotteryJoinService = SpringContextHolder.getBean(ActivityLotteryJoinService.class);
    private ActivityLotteryResultService activityLotteryResultService = SpringContextHolder.getBean(ActivityLotteryResultService.class);
    private GoodsService goodsService = SpringContextHolder.getBean(GoodsService.class);

    @Override
    public void execute(JobExecutionContext context) {

       /* ClassPathXmlApplicationContext contextLoader = new ClassPathXmlApplicationContext(
                new String[] { "classpath*:spring-jdbc.xml", "classpath*:mybatis.xml"});

        DataSourceTransactionManager transactionManager = contextLoader.getBean(
                "transactionManager", DataSourceTransactionManager.class);*/
        //1.获取事务控制管理器
        DataSourceTransactionManager transactionManager = SpringContextUtils.getBean(DataSourceTransactionManager.class);
        //2.获取事务定义
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //3.设置事务隔离级别，开启新事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        //4.获得事务状态
        TransactionStatus transactionStatus = transactionManager.getTransaction(def);
        try {

            String jobDetail = context.getJobDetail().getKey().getName();
            Integer entityId = Integer.valueOf(jobDetail.replace("startLotteryJob_",""));
            ActivityLotteryEntity entity = new ActivityLotteryEntity();
            entity.setId(entityId);
            entity.setLotteryStatus(1);


            activityLotteryService.update(entity);//修改活动状态
            ActivityLotteryEntity activity = activityLotteryService.queryObject(entityId);
            Map joinMap = new HashMap();
            joinMap.put("lotteryId",entityId);
            List<Integer> joinList = activityLotteryJoinService.queryIds(joinMap);
            Set<Integer> joinIds = new HashSet();  //参与人数
            Set<Integer> luckIds = new HashSet();  //中奖人数
            joinIds.addAll(joinList);

            if(joinIds.size()!=0){
                Map<String,Object> joinMapByEdit = new HashMap<>();
                joinMapByEdit.put("status",2);
                joinMapByEdit.put("lotteryId",entityId);
                activityLotteryJoinService.updateStatus(joinMapByEdit); //将所有参与人数状态改为未中奖

                if(joinIds.size() > activity.getPlacesNum()){
                    while(true){
                        randomGet(luckIds,joinList);
                        if(luckIds.size() >= activity.getPlacesNum())
                            break;
                    }
                }else{
                    luckIds = joinIds;
                }
    //                1.改参与状态  2.加中奖记录  3.扣库存
                Map<String,Object> luckPeople = new HashMap<>();
                luckPeople.put("status",1);
                luckPeople.put("ids",new ArrayList<>(luckIds));
                activityLotteryJoinService.updateStatus(luckPeople);

                List<ActivityLotteryResultEntity> lrList = new ArrayList<>();
                ActivityLotteryResultEntity resultEntity = null;
                for (Integer id: luckIds) {
                    resultEntity = new ActivityLotteryResultEntity();
                    resultEntity.setLotteryId(entityId);
                    resultEntity.setGoodsId(activity.getPrizeId());
                    resultEntity.setGoodsName(activity.getPrizeName());
                    resultEntity.setCreateTime(new Date());
                    resultEntity.setUserId(activityLotteryJoinService.queryObject(id).getUserId());
                    lrList.add(resultEntity);
                }
                activityLotteryResultService.saveBatch(lrList);//添加奖品关联

                GoodsEntity goodsEntity = new GoodsEntity();
                goodsEntity.setId(activity.getPrizeId());
                goodsEntity.setGoodsNumber(-luckIds.size());
                goodsService.updateGoodsNum(goodsEntity);//扣库存
                System.out.println("成功开奖。。。。。");
            }
            transactionManager.commit(transactionStatus);
        } catch (RuntimeException e) {
            e.printStackTrace();
            transactionManager.rollback(transactionStatus);
        }
    }

    //随机产生幸运儿
    private void randomGet(Set<Integer> luckIds,List<Integer> joinIds){
        Random random = new Random();
        int temp = random.nextInt(joinIds.size());
        luckIds.add(joinIds.get(temp));
    }
}


