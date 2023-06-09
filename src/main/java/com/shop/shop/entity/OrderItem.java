package com.shop.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity{

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="item_id")
    private Item item;

    @Column(name = "item_name")
    private String itemName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격

    private int count; //수량



    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setItemName(item.getItemNm());
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice() * item.getDiscount() / 100);
        item.removeStock(count);
        return orderItem;

    }

    public int getTotalPrice(){
        return orderPrice * count;
    }

    public void cancel(){
        this.getItem().addStock(count);
    }


}
