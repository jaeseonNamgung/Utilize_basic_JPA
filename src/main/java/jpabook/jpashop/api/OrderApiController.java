package jpabook.jpashop.api;

import jpabook.jpashop.Controller.OrderSearch;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.OrderQueryRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o-> o.getItem().getName());
        }
        return all;
    }
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all.stream().map(OrderDto::of).collect(Collectors.toList());
    }
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> all = orderRepository.findAllWithItem(new OrderSearch());
        System.out.println("Order Size: " + all.size());
        all.forEach(o->{
            System.out.println("Member Name: " + o.getMember().getName() + " |Order Id: " + o.getId());
        });
        return all.stream().map(OrderDto::of).collect(Collectors.toList());
    }
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        return orderQueryRepository.findOrderQueryDtos();

    }
     record OrderDto(
            Long orderId,
            String name,
            LocalDateTime orderDate,
            Address address,
            List<OrderItems> orderItems
    ){
        public static OrderDto of(Order order){
            return new OrderDto(
                    order.getId(),
                    order.getMember().getName(),
                    order.getOrderDate(),
                    order.getDelivery().getAddress(),
                    order.getOrderItems().stream()
                            .map(OrderItems::of)
                            .collect(Collectors.toList())
            );
        }
    }

    record OrderItems(
            String itemName,
            int orderPrice,
            int count
    ){
        public static OrderItems of(OrderItem orderItem){
            return new OrderItems(
                    orderItem.getItem().getName(),
                    orderItem.getOrderPrice(),
                    orderItem.getCount()
            );
        }

    }
}
