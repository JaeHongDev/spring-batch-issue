package com.example.springbatchissue.config;


import com.example.springbatchissue.domain.Member;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CursorBatchConfig {
    private static final String BATCH_NAME = "CursorBatch";
    public static final String JOB_NAME = BATCH_NAME + "Job";
    private static final String STEP_NAME = BATCH_NAME + "Step";


    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;


    @Bean(JOB_NAME)
    Job job(){
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(step())
                .build();
    }

    @Bean(STEP_NAME)
    Step step(){
        final var index = new int[]{0};
        return new StepBuilder(STEP_NAME, jobRepository)
                .<Member, Member>chunk(10, transactionManager)
                .reader(new JpaCursorItemReaderBuilder<Member>()
                        .name("cursor")
                        .entityManagerFactory(entityManagerFactory)
                        .queryString("SELECT M from Member M")
                        .build())
                .writer(chunk -> {
                    index[0]++;
                    log.info("index {}", index[0]);
                    log.info("chunk size {}", chunk.size());
                    log.info("{}", chunk.getItems());
                })
                .build();
    }





}
