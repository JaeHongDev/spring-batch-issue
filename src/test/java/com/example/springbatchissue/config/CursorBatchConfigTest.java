package com.example.springbatchissue.config;

import static org.junit.jupiter.api.Assertions.*;

import com.example.springbatchissue.domain.Member;
import com.example.springbatchissue.domain.MemberRepository;
import com.example.springbatchissue.util.SpringBatchSupport;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class CursorBatchConfigTest extends SpringBatchSupport {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    void test(){
        IntStream.iterate(1, n -> n + 1)
                .boxed()
                .map(i -> new Member(i + ""))
                .limit(100)
                .forEach(memberRepository::save);

        this.launchJob(CursorBatchConfig.JOB_NAME);
    }
}