package com.example.springbatchissue.config;


import com.example.springbatchissue.domain.Member;
import com.example.springbatchissue.domain.UpdateMember;
import com.example.springbatchissue.domain.UpdateMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ErrorCaseBatchConfig {

    public static final String BATCH_NAME = "ErrorCaseBatch";
    public static final String JOB_NAME = BATCH_NAME + "Job";
    public static final String STEP_NAME = BATCH_NAME + "Step";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final UpdateMemberRepository updateMemberRepository;
    private final CreateItemReaderFactory createItemReaderFactory;
    @Bean(JOB_NAME)
    Job job(){
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(step())
                .build();
    }

    @Bean(STEP_NAME)
    Step step(){
        return new StepBuilder(JOB_NAME, jobRepository)
                .<Member,Member> chunk(10, transactionManager)
                .reader(createItemReaderFactory.createItemReader())
                .writer(chunk -> {
                    log.info("{}",chunk.getItems());
                    updateMemberRepository.saveAll(chunk.getItems()
                            .stream()
                            .map(UpdateMember::from)
                            .toList());
                }).build();
    }

}

