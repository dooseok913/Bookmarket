package com.springboot.repository.mybatis;

import com.springboot.domain.Shipping;

public interface ShippingMapper {

    Shipping findShippingById(Long id) ;
    int insertShipping(Shipping shipping);

    int updateShipping(Shipping shipping);
    int deleteShipping(Long id);
}
