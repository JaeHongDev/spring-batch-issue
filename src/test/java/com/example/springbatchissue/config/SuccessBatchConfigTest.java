package com.example.springbatchissue.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.springbatchissue.config.ErrorCaseBatchConfigTest.FakeItemReader;
import com.example.springbatchissue.core.CustomProxyItemReaderDelivery;
import com.example.springbatchissue.domain.Member;
import com.example.springbatchissue.domain.UpdateMemberRepository;
import com.example.springbatchissue.util.SpringBatchSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SuccessBatchConfigTest extends SpringBatchSupport {

    @MockBean
    private CustomProxyItemReaderDelivery customProxyItemReaderDelivery;
    @Autowired
    private UpdateMemberRepository updateMemberRepository;
    @Test
    void mock_에_성공한다(){
        final var fakeItemReader = new FakeItemReader(
                new Member("start"),
                new Member("second"),
                new Member("third"),
                null
        );

        BDDMockito.given(customProxyItemReaderDelivery.deliveryItemReader()).willReturn(fakeItemReader);

        // when
        this.launchJob(SuccessBatchConfig.JOB_NAME);

        // then

        assertThat(updateMemberRepository.count()).isEqualTo(3L);

    }

}