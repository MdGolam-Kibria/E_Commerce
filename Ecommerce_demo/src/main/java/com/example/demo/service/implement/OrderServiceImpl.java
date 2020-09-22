package com.example.demo.service.implement;

import com.example.demo.dto.OrderDto;
import com.example.demo.model.Order;
import com.example.demo.model.OrderProduct;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderProductRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrderService;
import com.example.demo.view.Response;
import com.example.demo.view.ResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProductRepository orderProductRepository, ModelMapper modelMapper, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public Response create(OrderDto orderDto, HttpServletRequest request) {
        final Boolean[] isInvalidProductSelected = {false};
        final double[] totalPrice = {0.0};
        orderDto.getOrderProducts().forEach(orderProductDto -> {
            Product product = productRepository.findByIdAndIsActiveTrue(orderProductDto.getProductId());
            if (product == null) {
                isInvalidProductSelected[0] = true;
            } else {
                double productPrice = (product.getMainPrice() - product.getDiscountPrice()) * orderProductDto.getProductQuantity();
                totalPrice[0] += productPrice;
            }
        });
        if (isInvalidProductSelected[0]) {
            //return bad response here
            return ResponseBuilder.getFailureResponce(HttpStatus.BAD_REQUEST, "please select a product");
        }
        Order order = new Order();
        order.setTotalPrice(String.valueOf(totalPrice[0]));
        order.setArea(orderDto.getArea());//Set all other properties here
        order.setCustomerIp(request.getRemoteAddr());
        order.setPaymentType(orderDto.getPaymentType());
        order.setCustomerId(orderDto.getCustomerId());
        order = orderRepository.save(order);
        Order finalOrder = order;
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderDto.getOrderProducts().forEach(orderProductDto -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderId(finalOrder.getId());
            orderProduct.setProductQuantity(orderProductDto.getProductQuantity());
            orderProduct.setProductId(orderProductDto.getProductId());
            orderProduct = orderProductRepository.save(orderProduct);
            orderProducts.add(orderProduct);
        });
        if (order != null && orderProducts.size() > 0) {
            //return success response here
            return ResponseBuilder.getSuccessResponce(HttpStatus.ACCEPTED, "Order Accepted", null);
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");// return 500 here ok
    }
}
