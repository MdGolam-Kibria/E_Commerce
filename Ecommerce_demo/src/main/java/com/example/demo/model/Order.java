package com.example.demo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order extends BaseModel {
    private String totalPrice;
    private String area;
    @Column(updatable = false)
    private LocalDateTime orderTime;
    private String paymentType;
    private String transactionId;
    private String orderId;
    @Column(updatable = false)
    private String customerIp;
    @Column(updatable = false,length = 10, precision =0)
    private Long customerId;
    private Boolean isOrderCompleted;
    @PrePersist
    public void init(){
        orderTime = LocalDateTime.now();
        isOrderCompleted = false;
    }
}

