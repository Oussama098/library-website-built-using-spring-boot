package ous.LabraryWebSite.Controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ous.LabraryWebSite.Services.BookService;
import ous.LabraryWebSite.Services.CategoryService;
import ous.LabraryWebSite.models.Book;
import ous.LabraryWebSite.models.Category;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    private BookService bookService;
    private CategoryService categoryService;
    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/book")
    public String book(Model model) {
        List<Book> books = this.bookService.getAllBooks();
        List<Category> category = this.categoryService.getAllCategory();
        List<Book> booksRecentlyAdded = this.bookService.getBookRecentlyAdded();
        model.addAttribute("booksRec" , booksRecentlyAdded);
        model.addAttribute("books", books);
        model.addAttribute("category", category);
        return "book/book";
    }

    @GetMapping(path = "/book/new")
    public String newBook(Model model) {
        List<Category> category = this.categoryService.getAllCategory();
        model.addAttribute("category", category);
        model.addAttribute("book", new Book());
        return "book/newBook";
    }

    @PostMapping(path = "/book/new")
    public String addBook(@Valid @ModelAttribute("book") Book book , BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "book/newBook?error="+bindingResult.getAllErrors();
        }else{
            this.bookService.CreateBook(book);
            return "redirect:/book";
        }
    }

    @GetMapping(path = "/book/{id}/update")
    public String updateBook(@PathVariable long id, Model model) {
        List<Category> cate = this.categoryService.getAllCategory();

        Book book = this.bookService.getBookById(id);
        if (book != null) {
            model.addAttribute("book", book);
            model.addAttribute("category", cate);
        }
        return "book/updateBook";
    }

    @PostMapping(path = "/book/{id}/update")
    public String updateBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "book/updateBook?error="+bindingResult.getAllErrors();
        }else{
            this.bookService.CreateBook(book);
        }
        return "redirect:/book";
    }

    @GetMapping(path = "/book/{id}/delete")
    public String deleteBook(@PathVariable long id) {
        this.bookService.deleteBook(id);
        return "redirect:/book";
    }

    @GetMapping(path = "/book/{id}/category")
    public String BookByCategory(@PathVariable long id, Model model) {
        List<Book> books = this.bookService.getBooksByCategory(id);
        List<Category> cate = this.categoryService.getAllCategory();
        List<Book> booksRecentlyAdded = this.bookService.getBookRecentlyAdded();
        model.addAttribute("booksRec" , booksRecentlyAdded);
        model.addAttribute("category", cate);
        model.addAttribute("books", books);
        return "book/book";
    }

    @GetMapping(path = "/book/addToCart")
    public String addToCart(@RequestParam Book book, Model model) {
        List<Book> booksToBuy = new ArrayList<>();
        booksToBuy.add(book);
        int NumOfBooksToBuy = booksToBuy.size();
        System.out.println(NumOfBooksToBuy);
        model.addAttribute("num", NumOfBooksToBuy);
        return "book/book";
    }
}
