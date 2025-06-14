package com.zerobase.cms.user.service;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class SignupCustomerServiceTest {
    @Autowired
    private SignupCustomerService service;

    @Test
    void signUp() {
        SignUpForm form = SignUpForm.builder()
                .name("name")
                .birth(LocalDate.now())
                .email("ysh010206@gmail.com")
                .password("1")
                .phone("01090975864")
                .build();
        Customer c = service.signUp(form);
        assertNotNull(c.getId());
        assertNotNull(c.getCreatedAt());
    }
}