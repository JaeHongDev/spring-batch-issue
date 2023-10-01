package com.example.springbatchissue.config;


import com.example.springbatchissue.domain.Member;
import lombok.EqualsAndHashCode;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class CreateItemReaderFactory {

    public ItemReader<Member> createItemReader(){
        return () -> null;
    }


}
