package com.spring.jpastudy;

import com.spring.jpastudy.chap01.entity.Product;
import com.spring.jpastudy.chap01.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@Rollback  // 실제로 데베에 넣지 않기 위한 !
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("상품을 데베에 저장한다")
    void saveTest() {
        //given
        Product product = Product.builder()
                .name("정장")
                .price(120000)
                .category(Product.Category.FASHION)
                .build();
        //when
        // INSERT 후 저장된 데이터의 객체를 반환
        Product saved = productRepository.save(product);
        //then
        assertNotNull(saved);
    }

}
