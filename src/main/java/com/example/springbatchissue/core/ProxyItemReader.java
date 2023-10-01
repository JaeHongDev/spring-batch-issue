package com.example.springbatchissue.core;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;


@RequiredArgsConstructor
public class ProxyItemReader<T> implements ItemReader<T>{
    private ItemReader<T> lazyItemReader;

    private final Supplier<ItemReader<T>> proxyItemReader;
    @Override
    public T read() throws Exception{
        if(lazyItemReader == null){
            lazyItemReader = proxyItemReader.get();
        }
        return lazyItemReader.read();
    }
}
