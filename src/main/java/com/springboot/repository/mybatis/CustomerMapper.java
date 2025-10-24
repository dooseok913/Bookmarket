package com.springboot.repository.mybatis;

import com.springboot.domain.Customer;

public interface CustomerMapper {
    Customer findCustomerById(Long id);

    int insertCustomer(Customer customer);

    int updateCustomer(Long id);

    int updateCustomerPhoneById(Customer customer);

    int deleteById(Long id);
}
