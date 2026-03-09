package com.iuh.kidclothes.mapper;

import com.iuh.kidclothes.dto.respone.OrderRespone;
import com.iuh.kidclothes.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderRespone toOrderRespone(Order order);
    List<OrderRespone> toOrderResponeList(List<Order> orders);
}
