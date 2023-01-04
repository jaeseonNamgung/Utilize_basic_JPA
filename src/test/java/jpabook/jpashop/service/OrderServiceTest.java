package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class OrderServiceTest {
    
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @PersistenceContext
    private EntityManager em;
    
    @Test
    void 상품_조회(){
        // given
        Member member = createMember();
        Item item = createBook();
        int count = 2;
        // when
        Long orderId = orderService.order(member.getId(), item.getId(), count);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(10000*2);
        assertThat(item.getStockQuantity()).isEqualTo(6);
    }

    @Test
    void 상품주문_재고수량초과(){
        // given
        Member member = createMember();
        Item item = createBook();

        // when


        // then
        assertThatExceptionOfType(NotEnoughStockException.class)
                .isThrownBy(()->{
                    orderService.order(member.getId(), item.getId(), 10);
                }).withMessage("need more stock");
        assertThatThrownBy(()->{
            orderService.order(member.getId(), item.getId(), 10);
        })
                .isInstanceOf(NotEnoughStockException.class)
                .hasMessageStartingWith("need");

    }

    @Test
    void 주문_취소(){
        // Given
        Member member = createMember();
        Item item = createBook();
        Long orderId = orderService.order(member.getId(), item.getId(), 2);

        // When
        orderService.cancelOrder(orderId);

        // Then
        assertThat(item.getStockQuantity()).isEqualTo(8);
    }
    
    private Member createMember(){
        Member member = new Member();
        member.setName("member");
        member.setAddress(new Address("도시", "지역", "우편번호"));
        em.persist(member);
        return member;
    }

    private Book createBook(){
        Book book = new Book();
        book.setName("jpa basic");
        book.setPrice(10000);
        book.setStockQuantity(8);
        em.persist(book);
        return book;
    }

}