package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {
//    @NotBlank(message = "orderId is mandatory")
//    private String orderId; // what should be the value of it??
//    @NotBlank(message = "totalPrice is Mandatory")
//    private String totalPrice; //I think its not necessary here. You can calculate it from selected product from backend.
    @NotBlank(message = "area is mandatory")
    private String area;
    private String transactionId;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
//    private LocalDate orderTime; // It should be current date. User will not give date.
    @NotBlank(message = "paymentType is mandatory")
    private String paymentType;
    @NotNull(message = "customerId is mandatory")
    private Long customerId;
    private List<OrderProductDto> orderProducts;
}
