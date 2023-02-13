package com.smilegate.devpet.appserver.job;

import static org.quartz.JobBuilder.newJob;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.mongodb.lang.Nullable;
import org.quartz.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

    @Autowired
    private Scheduler scheduler; // 쿼츠 스케줄 객체

    /**
     * 게시글 redis에서 모아서 저장 안하기로함
     */
    @PostConstruct
    @Deprecated
    public void run() {
        // job 객체 생성
//        JobDetail detail = creatPostAveJob();
//
//        try {
//            // 스케줄 시간 설정 후  스케줄 실행
//            scheduler.scheduleJob(detail, createJobTrigger("0 0/2 * * * * ?"));
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * redis에서 post 데이터를 읽어와서 mongodb에 저장하는 job 생성
     * @return PostSaveJob JobDetail 반환
     */
    public JobDetail creatPostAveJob()
    {
        return createJobDetail(PostSaveJob.class);
    }
    public Trigger createJobTrigger(String scheduleExp){
        // 크론 스케줄 사용
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    public JobDetail createJobDetail(Class <? extends Job> job,@Nullable Map<String,Object> params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);
        return newJob(job).usingJobData(jobDataMap).build();
    }
    public JobDetail createJobDetail(Class <? extends Job> job) {
        return newJob(job).build();
    }
}
