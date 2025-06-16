package com.zerobase.cms.user.application;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.SignupCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignupCustomerService signupCustomerService;
    private final MailgunClient mailgun;

    public String cutomerSignUp(SignUpForm form){
        if (signupCustomerService.isEmailExists(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
            //exception
        } else {
            String code = getRandomCode();
            Customer c = signupCustomerService.signUp(form);
            SendMailForm sendMailForm = SendMailForm.builder()
                            .from("Mailgun Sandbox <postmaster@sandbox28f60fab10384f68a5c607b8a1b1773f.mailgun.org>")
                                    .to(form.getEmail())
                                            .subject("Verification Email")
                                                    .text(getVerificationEmailBody(form.getEmail(), form.getName(),code))
                                                            .build();
            log.info("Send email result: " + mailgunClient.sendEmail(sendMailForm).getBody());
            mailgunClient.sendEmail(sendMailForm);
            signupCustomerService.changeCustomerValidateEmail(c.getId(), code);
            return "회원가입에 성공하였습니다.";
        }
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true,true);
    }

    private String getVerificationEmailBody(String email, String name, String code){
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello ").append(name).append("! Please Click Link for verification. \n\n")
                .append("http://localhost:8080/cutomer/signup/verify?email=")
                .append(email)
                .append("&code=")
                .append(code)
                .toString();
    }


}
