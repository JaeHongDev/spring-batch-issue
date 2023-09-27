package com.example.springbatchissue.core;

import org.springframework.batch.item.ItemReader;

public interface ProxyItemReaderDelivery<T> {
    ItemReader<T> deliveryItemReader();
}
