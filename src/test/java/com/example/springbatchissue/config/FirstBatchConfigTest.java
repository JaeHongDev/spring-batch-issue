package com.example.springbatchissue.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.example.springbatchissue.domain.Member;
import com.example.springbatchissue.domain.UpdateMemberRepository;
import com.example.springbatchissue.util.SpringBatchSupport;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FirstBatchConfigTest extends SpringBatchSupport {

    @MockBean
    private FirstBatchItemReader firstBatchItemReader;

    @Autowired
    private UpdateMemberRepository updateMemberRepository;
    @Test
    void 정상적으로_mock이_발생합니다(){
        given(firstBatchItemReader.read())
                .willReturn( new Member("start"),
                        new Member("second"),
                        new Member("third"), null);

        //when
        this.launchJob(FirstBatchConfig.JOB_NAME);


        //then
        Assertions.assertThat(updateMemberRepository.count()).isEqualTo(3L);
    }
}