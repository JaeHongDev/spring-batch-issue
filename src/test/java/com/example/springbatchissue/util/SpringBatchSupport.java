package com.example.springbatchissue.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LoggerGroup;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;



@SpringBootTest
@SpringBatchTest
@TestPropertySource(properties = "spring.batch.job.enabled=false")
public class SpringBatchSupport {
    private static final Logger log = LoggerFactory.getLogger(SpringBatchSupport.class);
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private ApplicationContext applicationContext;


    protected void launchJob(String jobName) {
        jobLauncherTestUtils.setJob(applicationContext.getBean(jobName, Job.class));
        try {
            jobLauncherTestUtils.launchJob();
        } catch (Exception e) {
            log.info("job 실행 실패");
            throw new RuntimeException(e);
        }
    }
}
