package com.springboot;

import com.springboot.domain.Customer;
import com.springboot.domain.Shipping;
import com.springboot.repository.mybatis.CustomerMapper;
import com.springboot.repository.mybatis.ShippingMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookmarketApplicationTests {
	@Autowired
	CustomerMapper customerMapper;

	@Autowired
	ShippingMapper shippingMapper;
	@Test
	void contextLoads() {
	}


	@Test
	void mybatisInsertTest1() {
		Customer customer = new Customer();
		customer.setName("Alice");
		customer.setPhone("010-1234-5678");

		int rowCount = customerMapper.insertCustomer(customer);
		System.out.println("insert row count : " + rowCount);
	}

	@Test
	void mybatisSelectTest1() {
		long searchId = 1L;
		Customer customer = customerMapper.findCustomerById(searchId);
		if(customer != null) {
			System.out.println("Customer found : " + customer.getName() + ", phone" + customer.getPhone());
		} else {
			System.out.println("Customer with Id " + searchId + "not found");
		}
	}


	@Test
	void mybatisSelectTest2() {
		long searchId = 1L;
		Shipping shipping = shippingMapper.findShippingById(searchId);
		if(shipping != null) {
			System.out.println("이름 :" + shipping.getName() + "생일 "+ shipping.getDate());
		}else {
			System.out.println("찾지 못했습ㄴ디ㅏ.");
		}
	}
	@Test
	void mybatisInsertTest3() {
		Shipping shipping = new Shipping();
		shipping.setName("홍길동");
		shipping.setDate("17230201");
		int rowCount = shippingMapper.insertShipping(shipping);
		System.out.println("수정된 갯수 : "+ rowCount);
	}
	@Test
	void mybatisinsertTest4() {
		Customer customer = new Customer();
		customer.setPhone("01055552222");
		customer.setName("김동현");
		int rowCount = customerMapper.insertCustomer(customer);
		System.out.println("수정된 갯수 : "+ rowCount);

	}
	@Test
	void updateCustomerPhoneById() {
		Customer customer = new Customer();
		customer.setId(2l);
		customer.setPhone("01077777777");
		int rowCount = customerMapper.updateCustomerPhoneById(customer);
		System.out.println("업데이트된 고객 전화번호 갯수 : " + rowCount);
	}

	@Test
	void deleteById() {
		Long searchId = 3L;
		int rowCount = customerMapper.deleteById(searchId);
		System.out.println("삭제된 갯수 : " + rowCount);

	}


	@Test
	void insertShippingTest12() {
		Shipping shipping = new Shipping();
		shipping.setName("호주 마틴");
		shipping.setDate("20251111");
		int rowCount = shippingMapper.insertShipping(shipping);
		System.out.println("배송 insert 성공? "+ rowCount);
	}

	@Test
	void updateShipping() {
		Shipping shipping = new Shipping();
		shipping.setDate("20260111");
		shipping.setName("마틴");
		shipping.setId(3L);
		int rowCount =shippingMapper.updateShipping(shipping);
		System.out.println("업데이트 배송" + rowCount);

	}
	@Test
	void deleteShipping() {
		long searchId = 3l;
		int rowCount= shippingMapper.deleteShipping(searchId);
		System.out.println("삭제된 갯수"+rowCount);
	}

}
