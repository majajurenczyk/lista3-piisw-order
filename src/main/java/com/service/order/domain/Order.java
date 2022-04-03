package com.service.order.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "\"ORDER\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String customerName;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @OneToOne(cascade = CascadeType.ALL)
    private Delivery delivery;

    public BigDecimal calculateTotalPrice(){
        return items.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String concatenateItemsNames(){
        return items.stream()
                .map(item -> item.getProduct().toString())
                .collect(Collectors.joining(", "));
    }
}

