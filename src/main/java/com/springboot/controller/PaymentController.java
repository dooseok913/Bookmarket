package com.springboot.controller;

import com.springboot.domain.Book;
import com.springboot.domain.Order;
import com.springboot.domain.OrderItem;
import com.springboot.domain.Payment;
import com.springboot.service.BookService;
import com.springboot.service.OrderProService;
import com.springboot.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderProService orderProService;
    private final BookService bookService;

    @GetMapping("/prepare/{orderId}")
    public String prepare(@PathVariable Long orderId, Model model) {
        System.out.println("=== Payment Prepare 시작 ===");
        System.out.println("OrderId: " + orderId);


        Payment payment = paymentService.prepare(orderId);
        System.out.println("Payment 생성: " + payment);
        System.out.println("MerchantUid: " + payment.getMerchantUid());

        Order order = orderProService.get(orderId);
        System.out.println("Order 조회 완료");
        System.out.println("Customer: " + order.getCustomer().getName());
        System.out.println("Shipping: " + order.getShipping().getName());
        System.out.println("GrandTotal: " + order.getGrandTotal());

        List<Book> listofBooks = new ArrayList<>();
        System.out.println("OrderItems 수: " + order.getOrderItems().size());

        for (OrderItem item : order.getOrderItems().values()) {
            String bookId = item.getBookId();
            System.out.println("Book 조회: " + bookId);
            Book book = bookService.getBookById(bookId);
            listofBooks.add(book);

        }
        System.out.println("Book list size : " + listofBooks.size());

        model.addAttribute("bookList", listofBooks);
        model.addAttribute("payment", payment);
        model.addAttribute("order", order);
        System.out.println("prepare 메서드 실행");
        return "orderConfirmation";
    }

    @PostMapping("/webhook")
    @ResponseBody
    public Map<String, String> webhook(@RequestBody Map<String, Object> payload) {
        try {
            System.out.println("=== Webhook 수신 ===");
            System.out.println("Payload: " + payload);

            // JavaScript에서 보내는 필드명과 일치
            String merchantUid = (String) payload.get("merchantUid");
            String transactionId = (String) payload.get("transactionId");
            BigDecimal paidAmount = new BigDecimal(payload.get("paidAmount").toString());

            System.out.println("MerchantUid: " + merchantUid);
            System.out.println("TransactionId: " + transactionId);
            System.out.println("PaidAmount: " + paidAmount);

            paymentService.confirmPaid(merchantUid, transactionId, paidAmount);

            return Map.of("status", "success", "message", "결제 완료");
        } catch (Exception e) {
            System.err.println("Webhook 에러: " + e.getMessage());
            e.printStackTrace();
            return Map.of("status", "error", "message", e.getMessage());
        }
    }
}