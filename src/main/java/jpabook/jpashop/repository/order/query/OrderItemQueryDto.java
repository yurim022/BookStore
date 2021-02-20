package jpabook.jpashop.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class OrderItemQueryDto {

    @JsonIgnore
    private Long orderId;
    private String itemName;//상품명
    private int orderPrice;//주문갸격
    private int count;//주문수량

    public OrderItemQueryDto(Long orderId,String itemName, int orderPrice, int count){
        this.count = count;
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
    }

}
