package com.springboot.controller;

import com.springboot.domain.Book;
import com.springboot.exception.BookIdException;
import com.springboot.exception.CategoryException;
import com.springboot.service.BookService;
import com.springboot.service.BookServiceImpl;
import com.springboot.validator.BookValidator;
import com.springboot.validator.UnitsInStockValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller // 컨트롤러 컴포넌트로 등록(Bean)
// 이 애노테이션은 Spring에게 이 클래스가 웹 요청을 
//처리하는 '컨트롤러' 역할을 한다는 것을 알려줍니다.
@RequestMapping(value = "/books")
public class BookController {


    @Autowired
     // Spring의 의존성 주입(Dependency Injection, DI) 기능을 사용합니다.
    // Spring 컨테이너가 BookService 타입의 빈(객체)을 찾아서 
    //private BookService bookService; 변수에 자동으로 할당해 줍니다.
    // 이를 통해 개발자는 객체를 직접 생성하거나 관리할 필요 없이
    // 필요한 서비스를 사용할 수 있습니다.


    private BookService bookService;  // 의존성 주입(DI) -> Bean에 있는 객체를 가져온다
    // BookService 인터페이스 타입의 bookService 변수를 선언합니다.
    // 실제로는 이 인터페이스를 구현한 클래스(예: BookServiceImpl)의 
    //객체가 주입됩니다.
    // autowired 때문에   BookService bookService = new BookService() 가 된 것과 같다***
    @RequestMapping
    // 이 애노테이션은 클라이언트의 특정 HTTP 요청이 이 메서드를 호출하도록 매핑합니다.
    // - value = "/books": URL 경로가 "/books"일 때 이 메서드를 실행합니다.
    // - method = RequestMethod.GET: HTTP GET 요청일 때만 이 메서드를 실행합니다.
    // 즉, 웹 브라우저에서 'http://localhost:8080/books' (GET 방식)로 접속하면 이 메서드가 실행됩니다.
    public String requestBookList(Model model) {
        // requestBookList라는 이름의 메서드를 정의합니다.
    // - public String: 반환 타입이 String으로, 이는 응답할 '뷰(View)'의 이름을 나타냅니다.
    // - (Model model): Spring(프레임워크)이 제공하는 Model 객체를 매개변수로 받습니다.
    //   이 Model 객체는 컨트롤러에서 뷰(예: HTML 템플릿)로 데이터를 전달하는 데 사용됩니다.
    
    // Model 객체는 컨트롤러에서 뷰(View)로 데이터를 전달하는 데 사용되는 컨테이너
    // 웹페이지를 요청하면, 컨트롤러는 비즈니스 로직을 수행하여 필요한 데이터를 생성하거나 가져옵니다.
    // 이 데이터를 웹페이지에 동적으로 표시해야 할 때 Model 객체에 담아서 뷰에 전달
    //컨트롤러 메서드에 Model model 파라미터를 선언하면, Spring이 자동으로 Model 객체를 생성하여 주입해 줍니다.



        List<Book> list = bookService.getAllBookList();     // 모든 도서 목록들을 가져옴
        // 이 메서드는 데이터베이스에서 도서 정보를 조회하는 등의 비즈니스 로직을 수행할 것입니다.
        // 가져온 도서 목록(List<Book> 타입)은 'list' 변수에 저장됩니다.
        model.addAttribute("bookList", list);   // 모델에 도서 목록들을 속성으로 저장
        // Model 객체에 "bookList"라는 이름으로 'list' 변수에 담긴 도서 목록 데이터를 추가합니다.
        // 이제 'books.html'과 같은 뷰 템플릿에서는 "bookList"라는 이름으로 이 데이터에 접근할 수 있습니다.
        // attributeName은  자동완성 기능처럼, 그냥 들어간것. 입력된 값이 아님.
        //model.addAttribute("key", value) 메서드를 사용하여 데이터를 추가합니다. 
        //여기서 "key"는 뷰 템플릿에서 해당 데이터에 접근할 때 사용할 이름이고, value는 실제 데이터입니다.

        return "books";  // 뷰 이름 반환 (books.html(thymeleaf 템플릿)을 의미,여기로 전달)
         // "books"라는 문자열을 반환합니다. Spring Boot는 이 문자열을 기반으로
        // 설정된 뷰 리졸버(예: Thymeleaf)를 사용하여 'src/main/resources/templates/books.html' 파일을 찾아 렌더링합니다.
        // Model 객체에 저장된 데이터는 이 템플릿으로 전달되어 동적인 웹페이지를 생성하게 됩니다.

        //컨트롤러가 뷰 이름을 반환하면, Spring은 Model 객체에 담긴 데이터를 해당
        // 뷰 템플릿으로 전달합니다. 뷰 템플릿은 이 데이터를 사용하여 최종적인 HTML 페이지를 렌더링합니다.
   
        //예를 들어, model.addAttribute("bookList", list); 코드는 list 변수에
        // 담긴 도서 목록 데이터를 "bookList"라는 이름으로 뷰에 전달하겠다는 의미입니다. 뷰 템플릿(예: Thymeleaf)에서는 
        // ${bookList}와 같이 이 데이터를 참조하여 도서 목록을 화면에 출력할 수 있습니다.
        
    }
    @GetMapping("/all")
    public ModelAndView requestAllBooks() {
        ModelAndView modelAndView = new ModelAndView();
        List<Book> list =bookService.getAllBookList();

        modelAndView.addObject("bookList", list);
        modelAndView.setViewName("books");
        return modelAndView;
    }
    @Autowired
    private BookValidator bookValidator;
    @GetMapping("/book")
    public String requestBookById(@RequestParam("id") String bookId, Model model) {
        Book bookById = bookService.getBookById(bookId);
        model.addAttribute("book", bookById);
        return "book";
    }

    @GetMapping("/{category}")
    public String requestBooksByCategory (
            @PathVariable("category") String bookCategory, Model model
    ) {
        List<Book> booksByCategory = bookService.getBookListByCategory(bookCategory);
        if(booksByCategory == null || booksByCategory.isEmpty()) {
            throw new CategoryException();
        }
        model.addAttribute("bookList", booksByCategory);
        return "books";
    }

    @GetMapping("/filter/{bookFilter}")
    public String requestBooksByFilter (
            @MatrixVariable(pathVar = "bookFilter")Map<String, List<String>> bookFilter, Model model){
        Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", booksByFilter);
        return "books";
    }

    @Value("${file.uploadDir}")
    String fileDir;

//    @Autowired
//    private UnitsInStockValidator unitsInStockValidator;

    @GetMapping("/add")
    public String requestAddBookForm(Model model)
      {  model.addAttribute("book", new Book());
        return "addBook";
    }

    @PostMapping("/add")
    public String submitAddNewBook(@Valid @ModelAttribute Book book, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "addBook";
                    }
        try {
            MultipartFile bookImage = book.getBookImage();
            String saveName = bookImage.getOriginalFilename();
            File saveFile = new File(fileDir, saveName);
            if (bookImage != null && !bookImage.isEmpty()) {
                try {
                    bookImage.transferTo(saveFile);

                } catch (Exception e) {
                    throw new RuntimeException("도서 이미지 업로드가 실패하였습니다., e");

                }

            }
            book.setFileName(saveName);
            bookService.setNewBook(book);
        }catch (BookIdException ex) {
            bindingResult.rejectValue("bookId", "duplicate", "이미 존재하는 도서 id 입니다.");
            return "addBook";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "등록 중 오류가 발생했습니다: " + e.getMessage());
            return "addBook";
        }



        return "redirect:/books";


    }



    @GetMapping("/download")
    public void downloadBookImage(@RequestParam("file") String paramKey,
                                  HttpServletResponse response) throws IOException {
        File imageFile = new File(fileDir + paramKey);
        response.setContentType("application/download");
        response.setContentLength((int)imageFile.length());
        response.setHeader("Content-disposition", "attachment;filename=\""+paramKey+"\"");
        OutputStream os = response.getOutputStream();
        FileInputStream fis = new FileInputStream(imageFile);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();
    }



    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("addTitle", "신규 도서 등록");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){

        binder.setValidator((bookValidator));
        binder.setAllowedFields("bookId", "name", "unitPrice", "author", "description"
        , "publisher", "category", "unitsInStock", "totalPages", "releaseDate", "condition", "bookImage");
    }

//    @ExceptionHandler(value={BookIdException.class})
//    public ModelAndView handleError(HttpServletRequest req, BookIdException exception) {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("invalidBookId", exception.getBookId());
//        mav.addObject("exception", exception);
//        mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
//
//        mav.setViewName("errorBook");
//        return mav;
//    }
//    @ExceptionHandler(BookIdException.class)
//    public String handleBookIdException(BookIdException ex, Model model) {
//        model.addAttribute("errorMessage", ex.getMessage());
//        model.addAttribute("book" , new Book());
//        return  "addBook";
//    }
    @PreAuthorize("hasRole('Role_ADMIN')")
    @GetMapping("/update")
    public  String getUpdateBookForm(@ModelAttribute("updateBook") Book book,
                                     @RequestParam("id")String bookId, Model model) {
        Book bookById = bookService.getBookById(bookId);
        model.addAttribute("book", bookById);
        return "updateForm";
    }

    @PreAuthorize("hasRole('Role_ADMIN')")
    @PostMapping("/update")
    public  String processUpdateBookForm(@ModelAttribute("updateBook") Book book) {
        MultipartFile bookImage = book.getBookImage();
        String rootDirectory = fileDir;
        if(bookImage != null && !bookImage.isEmpty()) {
            try {
                String fname = bookImage.getOriginalFilename();
                bookImage.transferTo(new File(fileDir + fname));
                book.setFileName(fname);
            } catch (Exception e ) {
                throw new RuntimeException("Book Image saving failed", e);
            }
        }
        bookService.setUpdateBook(book);
        return  "redirect:/books";
    }

    @RequestMapping("/delete")
    public  String getDeleteBookForm(Model model, @RequestParam("id") String bookId){
        bookService.setDeleteBook(bookId);
        return "redirect:/books";
    }


}
