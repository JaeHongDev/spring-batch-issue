package com.example.springbatchissue.config;


import com.example.springbatchissue.domain.Member;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class FirstBatchItemReader implements ItemReader<Member> {

    @Override
    public Member read(){
        return null;
    }
}
