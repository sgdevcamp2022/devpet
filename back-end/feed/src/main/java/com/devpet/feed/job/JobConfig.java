//package com.devpet.feed.job;
//
//import static org.quartz.JobBuilder.newJob;
//
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import lombok.RequiredArgsConstructor;
//import org.quartz.*;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class JobConfig {
//
//    private final Scheduler scheduler; // 쿼츠 스케줄 객체
//
//    /**
//     * cron
//     * 분 , 시 , 일 , 월 , 요일
//     */
//    @PostConstruct
//    @Deprecated
//    public void run() {
//        // job 객체 생성
//        JobDetail detail = creatSaveFollowRelationJob();
//
//        try {
//            // 스케줄 시간 설정 후  스케줄 실행
//            scheduler.scheduleJob(detail, createJobTrigger("* * * * * ?"));
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * redis에서 post 데이터를 읽어와서 mongodb에 저장하는 job 생성
//     * @return PostSaveJob JobDetail 반환
//     */
//    public JobDetail creatSaveFollowRelationJob()
//    {
//        return createJobDetail(SaveFollowCached.class);
//    }
//    public Trigger createJobTrigger(String scheduleExp){
//        // 크론 스케줄 사용
//        return TriggerBuilder.newTrigger()
//                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
//    }
//
//    public JobDetail createJobDetail(Class <? extends Job> job, Map<String,Object> params) {
//        JobDataMap jobDataMap = new JobDataMap();
//        jobDataMap.putAll(params);
//        return newJob(job).usingJobData(jobDataMap).build();
//    }
//    public JobDetail createJobDetail(Class <? extends Job> job) {
//        return newJob(job).build();
//    }
//}
