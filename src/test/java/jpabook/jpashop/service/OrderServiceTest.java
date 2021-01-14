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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
    //given
        Member member = createMember();
        Item book = creatBook("시골 JPA",10000,10);

        int orderCount = 2;

    //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(getOrder.getOrderItems().size(),1,"주문한 상품 종류 수가 정확해야 한다");
        assertEquals(getOrder.getTotalPrice(),10000*orderCount,"주문 가격은 가격*수량 이다");
        assertEquals(8,book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");


    }

    private Item creatBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강남","123-123"));
        em.persist(member);
        return member;
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
    //given
        Member member = createMember();
        Item item = creatBook("시골 JPA",10000,10);

        int orderCount = 11;

    //when

    //then
        assertThrows(NotEnoughStockException.class,()->{orderService.order(member.getId(),item.getId(),orderCount);});

    }
    @Test
    public void 주문취소() throws Exception{
    //given
        Member member = createMember();
        Item item = creatBook("시골 JPA",10000,10);

        int orderCount =2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

    //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(getOrder.getStatus(),OrderStatus.CANCEL,"주문 취소시 상태는 CANCEL");
        assertEquals(10,item.getStockQuantity(),"주문 취소된 상품은 그만큼 재고가 증가해야 한다.");


    }


}