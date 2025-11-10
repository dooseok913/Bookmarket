package com.springboot.controller;

import com.springboot.domain.*;
import com.springboot.service.BookService;
import com.springboot.service.CartService;
import com.springboot.service.OrderProService;
import com.springboot.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
@SessionAttributes({"order", "bookList"})
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderProService orderProService;
    @Autowired
    private BookService bookService;

    @ModelAttribute("order")
    public Order createOrder() {
        return  new Order();
    }
    @ModelAttribute("bookList")
    public  List<Book> createBookList() {
        return  new ArrayList<>();
    }


    @GetMapping("/{cartId}")
    public String requestCartList(@PathVariable(value = "cartId") String cartId,
                                  @ModelAttribute("order") Order order,
                                  @ModelAttribute("bookList") List<Book> listofBooks){
        Cart cart = cartService.validateCart(cartId);


        listofBooks.clear();
        order.getOrderItems().clear();

        for(CartItem item : cart.getCartItems().values()) {
            OrderItem orderItem = new OrderItem();
            Book book =item.getBook();
            listofBooks.add(book);

            orderItem.setBookId(book.getBookId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());
            order.getOrderItems().put(book.getBookId(), orderItem);
        }

        order.getShipping().getAddress().setCountry("대한민국");
        order.getShipping().getAddress().setZipcode("000000");

        order.setGrandTotal(cart.getGrandTotal());

        return  "redirect:/order/orderCustomerInfo";
    }

    @GetMapping("/orderCustomerInfo")
    public String requestCustomerInfoForm(@ModelAttribute("order") Order order ,Model model) {
        model.addAttribute("customer", order.getCustomer());
        return "orderCustomerInfo";

    }
    @PostMapping ("/orderCustomerInfo")
    public String requestCustomerInfo(@ModelAttribute Customer customer, @ModelAttribute("order") Order order) {
        order.setCustomer(customer);
        return "redirect:/order/orderShippingInfo";
    }

    @GetMapping("/orderShippingInfo")
    public String requestShippingInfoForm(@ModelAttribute("order") Order order ,Model model) {
        model.addAttribute("shipping", order.getShipping());
        return "orderShippingInfo";
    }
    @PostMapping("/orderShippingInfo")
    public String requestShippingInfo(@Valid @ModelAttribute Shipping shipping,
                                      BindingResult bindingResult ,
                                      @ModelAttribute("order") Order order ,
                                      Model model) {
        if(bindingResult.hasErrors())
            return  "orderShippingInfo";

        System.out.println("coutnry :  " + shipping.getAddress().getCountry());
        System.out.println("zip code :  " + shipping.getAddress().getZipcode());

        order.setShipping(shipping);
        System.out.println("requestShippingInfo 까지는 왔다.");
        return "redirect:/order/orderConfirmation";
    }

    @GetMapping("/orderConfirmation")
    public String requestConfirmation(@ModelAttribute("order") Order order,
                                        @ModelAttribute("bookList") List<Book> listofBooks,
                                        Model model) {
        System.out.println("orderconfirmation 겟 매핑 order"+order);
        System.out.println("orderconfirmation 겟 매핑 order.shipping"+order.getShipping());
        System.out.println("orderconfirmation 겟 매핑 shipping.name"+order.getShipping().getName());
        model.addAttribute("bookList", listofBooks);
        model.addAttribute("order", order);
        return "orderConfirmation";
    }

    @PostMapping("/orderConfirmation")
    public String requestConfirmationFinished(@ModelAttribute("order") Order order,
                                            Model model) {
        System.out.println("orderconfirmation 포스트 매핑이 시작되었습ㄴ다. ");
        Order saveOrder = orderProService.save(order);
        return "redirect:/payments/prepare/"+ saveOrder.getOrderId();
    }

    @GetMapping("/orderFinished")
    public String requestFinished(@ModelAttribute("order") Order order,
                                  SessionStatus sessionStatus,
                                  Model model) {

        model.addAttribute("order", order);
        sessionStatus.setComplete();

        return "orderFinished";
    }

    @GetMapping("/orderCancelled")
    public  String requestCancelled(SessionStatus sessionStatus) {

        sessionStatus.setComplete();
        return "orderCancelled";
    }


    @GetMapping("/list")
    public String viewHomePage(Model model){
        return  viewPage(1, "orderId", "asc", model);
    }

    @GetMapping("/page")
    public String viewPage (@RequestParam("pageNum") int pageNum,
                            @RequestParam("sortField") String sortField,
                            @RequestParam("sortDir") String sortDir, Model model
                                                 ) {
        Page<Order> page = orderProService.listAll(pageNum, sortField, sortDir);
        List<Order> listOrders = page.getContent();
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");
        model.addAttribute("orderList", listOrders);
        return  "orderList";
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewOrder(@PathVariable(name="id") Long id) {
        Order order = orderProService.get(id);
        List<Book> listofBooks = new ArrayList<Book>();
        for(OrderItem item : order.getOrderItems().values() ){
            String bookId = item.getBookId();
            Book book = bookService.getBookById(bookId);
            listofBooks.add(book);
        }
        ModelAndView mav = new ModelAndView("orderView");
        mav.addObject("order", order);
        mav.addObject("bookList", listofBooks);
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditOrder(@PathVariable(name="id") Long id) {
        Order order = orderProService.get(id);
        List<Book> listofBooks = new ArrayList<Book>();
        for(OrderItem item : order.getOrderItems().values()){
            String bookId = item.getBookId();
            Book book = bookService.getBookById(bookId);
            listofBooks.add(book);
        }
        ModelAndView mav = new ModelAndView("orderEdit");
        mav.addObject("order", order);
        mav.addObject("bookList", listofBooks);
        return  mav;
    }
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable(name="id") Long id) {
        orderProService.delete(id);
        return "redirect:/order/list";
    }

    @GetMapping("/deleteAll")
    public String deleteAllOrder() {
        orderProService.deleteAll();
        return "redirect:/order/list";
    }
    @PostMapping("/save")
    public  String saveProduct(@ModelAttribute Order order) {
        Order save_order = orderProService.get(order.getOrderId());
        save_order.setShipping(order.getShipping());
        orderProService.save(save_order);
        return "redirect:/order/list";

    }

}
