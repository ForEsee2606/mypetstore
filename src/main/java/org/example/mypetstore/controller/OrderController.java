package org.example.mypetstore.controller;

import java.util.List;
import org.example.mypetstore.mapper.OrderMapper;
import org.example.mypetstore.model.PetOrder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderMapper orderMapper;

    public OrderController(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public List<PetOrder> list() {
        return orderMapper.findAll();
    }

    @PostMapping
    public PetOrder create(@RequestBody PetOrder order) {
        orderMapper.insert(order);
        return order;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody PetOrder order) {
        order.setId(id);
        orderMapper.update(order);
        return "ok";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        orderMapper.delete(id);
        return "ok";
    }

    @PostMapping("/{id}/ship")
    public String ship(@PathVariable Long id) {
        orderMapper.ship(id);
        return "ok";
    }

    @PostMapping("/{id}/complete")
    public String complete(@PathVariable Long id) {
        orderMapper.complete(id);
        return "ok";
    }
}