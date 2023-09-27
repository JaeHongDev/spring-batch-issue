package com.example.springbatchissue.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.example.springbatchissue.domain.Member;
import com.example.springbatchissue.domain.UpdateMemberRepository;
import com.example.springbatchissue.util.SpringBatchSupport;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ErrorCaseBatchConfigTest extends SpringBatchSupport {

    @MockBean
    private CreateItemReaderFactory createItemReaderFactory;

    @Autowired
    private UpdateMemberRepository updateMemberRepository;

    @Test
    void ItemReaderFactory의_mock이_동작하지_않는다(){
        final var fakeItemReader = new FakeItemReader(
                new Member("start"),
                new Member("second"),
                new Member("third"),
                null
        );


        given(createItemReaderFactory.createItemReader())
                .willReturn(fakeItemReader);

        //when
        this.launchJob(ErrorCaseBatchConfig.JOB_NAME);


        //then
        Assertions.assertThat(updateMemberRepository.count()).isEqualTo(3L);
    }



    static class FakeItemReader implements ItemReader<Member>{

        private final Queue<Member> members;


        public FakeItemReader(Member... members) {
            this.members = new LinkedList<>(Arrays.stream(members).toList());
        }

        @Override
        public Member read() {
            return members.poll();
        }
    }

}