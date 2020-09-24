package com.example.demo.secheduler;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class Schedulers {
    private static final Logger logger = LoggerFactory.getLogger(Schedulers.class.getName());
    private final OrderRepository orderRepository;

    @Autowired
    public Schedulers(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Scheduled(fixedRate = 300000l)//check in processing order after 5 min and delete all in processing order data
    public void testScheduling() {
        List<Order> orderList = orderRepository.findByTransactionIdAndIsActiveTrue(null);
        if (orderList != null) {
            orderRepository.deleteAll(orderList);
            logger.info("Delete inProcessing Orders Using Scheduling");
        }
    }
}
