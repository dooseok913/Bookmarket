package com.springboot.service;

import com.springboot.domain.*;
import com.springboot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderProService orderProService;

    @Transactional
    public Payment prepare(Long orderId) {
        Order order = orderProService.get(orderId);
        String merchantUid = "ORD-" + orderId + "-" + UUID.randomUUID();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setStatus(PaymentStatus.READY);
        payment.setAmount(order.getGrandTotal());
        payment.setMerchantUid(merchantUid);
        return paymentRepository.save(payment);
    }

    @Transactional
    public void confirmPaid(String merchantUid, String transactionId, BigDecimal paidAmount) {
        Payment payment = paymentRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new IllegalArgumentException("결제 내역 없음"));
        if (payment.getAmount().compareTo(paidAmount) != 0) {
            throw new IllegalStateException("금액 불일치");
        }
        payment.setStatus(PaymentStatus.PAID);
        payment.setTransactionId(transactionId);
        payment.setApprovedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }
}
