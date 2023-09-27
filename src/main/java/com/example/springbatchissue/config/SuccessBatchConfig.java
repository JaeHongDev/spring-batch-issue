package com.example.springbatchissue.config;


import com.example.springbatchissue.core.CustomProxyItemReaderDelivery;
import com.example.springbatchissue.core.ProxyItemReader;
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
public class SuccessBatchConfig {

    public static final String BATCH_NAME = "SuccessBatch";
    public static final String JOB_NAME = BATCH_NAME + "Job";
    public static final String STEP_NAME = BATCH_NAME + "Step";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CustomProxyItemReaderDelivery proxyItemReaderDelivery;
    private final UpdateMemberRepository updateMemberRepository;
    @Bean(JOB_NAME)
    public Job job(){
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(step())
                .build();
    }

    @Bean(STEP_NAME)
    public Step step(){
        return new StepBuilder(STEP_NAME, jobRepository)
                .<Member,Member >chunk(10, transactionManager)
                .reader(new ProxyItemReader<>(proxyItemReaderDelivery))
                .writer(chunk -> {
                    log.info("{}",chunk.getItems());
                    updateMemberRepository.saveAll(chunk.getItems()
                            .stream()
                            .map(UpdateMember::from)
                            .toList());
                })
                .build();
    }

}
