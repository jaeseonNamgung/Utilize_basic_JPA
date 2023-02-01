package jpabook.jpashop.api;

import jpabook.jpashop.Controller.OrderSearch;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * XToOne (ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getOrders(); // 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result =
                orders.stream().map(SimpleOrderDto::fromEntity).collect(Collectors.toList());
        return result;
    }
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(new OrderSearch());
        List<SimpleOrderDto> result =
                orders.stream().map(SimpleOrderDto::fromEntity).collect(Collectors.toList());
        return result;
    }



    public record SimpleOrderDto(
            Long orderId,
            String name,
            LocalDateTime orderDate,
            OrderStatus orderStatus,
            Address address
    ){

        public static SimpleOrderDto fromEntity(
                Order order
        ){
            return new SimpleOrderDto(
                    order.getId(),
                    order.getMember().getName(), // Lazy 초기화
                    order.getOrderDate(),
                    order.getStatus(),
                    order.getDelivery().getAddress() // Lazy 초기화
            );
        }

    }
}
