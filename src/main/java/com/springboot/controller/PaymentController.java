package com.springboot.controller;

import com.springboot.domain.Payment;
import com.springboot.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/prepare/{orderId}")
    public  String prepare(@PathVariable Long orderId, Model model) {
        Payment payment = paymentService.prepare(orderId);
        System.out.println("null여부 확인"+ payment);
        model.addAttribute("payment", payment);
        model.addAttribute("orderId", orderId);
        System.out.println("prepare 메서드 실행");
        return  "orderConfirmation";
    }

    @PostMapping("/webhook")
    @ResponseBody
    public String webhook (@RequestBody Map<String, Object> payload)
    {
        String merchantUid = (String) payload.get("merchant_uid");
        String impUid = (String) payload.get("imp_uid");
        BigDecimal amount = new BigDecimal(payload.get("paidAmount").toString());

        paymentService.confirmPaid(merchantUid, impUid, amount);
        return "OK";
    }


}
