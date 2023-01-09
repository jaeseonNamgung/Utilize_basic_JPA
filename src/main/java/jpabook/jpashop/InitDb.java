package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }
    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member member = createMember("userA", "마포구", "1111");
            em.persist(member);

            Book book1 = createBook("jpa1", 10000, 100);
            em.persist(book1);
            Book book2 = createBook("jpa2", 20000, 200);
            em.persist(book2);
            OrderItem orderItem1 = createOrderItem(book1, 10000, 100);
            OrderItem orderItem2 = createOrderItem(book2, 20000, 200);
            em.persist(orderItem1);
            em.persist(orderItem2);

            Delivery delivery = createDelivery(member);
            em.persist(delivery);

            Order order = createOrder(member,delivery, orderItem1, orderItem2);
            em.persist(order);
        }



        public void dbInit2(){
            Member member = createMember("userB", "서초구", "2222");
            em.persist(member);

            Book book1 = createBook("spring1", 30000, 300);
            em.persist(book1);
            Book book2 = createBook("spring2", 40000, 400);
            em.persist(book2);
            OrderItem orderItem1 = createOrderItem(book1, 10000, 100);
            OrderItem orderItem2 = createOrderItem(book2, 20000, 200);
            em.persist(orderItem1);
            em.persist(orderItem2);

            Delivery delivery = createDelivery(member);
            em.persist(delivery);

            Order order = createOrder(member,delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private static Order createOrder(Member member, Delivery delivery, OrderItem orderItem1, OrderItem orderItem2) {
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            return order;
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private static OrderItem createOrderItem(Book book, int orderPrice, int count) {
            OrderItem orderItem = OrderItem.createOrderItem(book, orderPrice, count);
            return orderItem;
        }

        private static Book createBook(String name, int price, int stockQuantity) {
            Book book1 = Book.builder()
                    .name(name)
                    .price(price)
                    .stockQuantity(stockQuantity)
                    .build();
            return book1;
        }

        private static Member createMember(String name, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address("서울", street, zipcode));
            return member;
        }
    }



}
