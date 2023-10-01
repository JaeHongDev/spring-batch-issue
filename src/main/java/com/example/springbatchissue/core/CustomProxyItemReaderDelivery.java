package com.example.springbatchissue.core;

import com.example.springbatchissue.domain.Member;
import com.example.springbatchissue.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.SingleItemPeekableItemReader;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class CustomProxyItemReaderDelivery implements ProxyItemReaderDelivery<Member> {
    private final Parameter parameter = Parameter.A;
    @Override
    public ItemReader<Member> deliveryItemReader() {
        return switch (parameter){
            case A,B-> new JpaCursorItemReader<>();
            case C -> new SingleItemPeekableItemReader<>();
        };
    }

    static enum Parameter{
        A,
        B,
        C
    }
}

