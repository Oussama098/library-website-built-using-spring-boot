package ous.LabraryWebSite.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ous.LabraryWebSite.Services.BookService;
import ous.LabraryWebSite.Services.CartService;
import ous.LabraryWebSite.models.Book;

import java.util.List;

@Controller
@SessionAttributes("cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private BookService bookService;

    @GetMapping(path = "/book/{id}/cart")
    public String addToCart(@PathVariable("id") int id ) {
        Book book = this.bookService.getBookById(id);
        if(book != null) {
            for (Book bookC : this.cartService.getCart()) {
                if(bookC.getId() == book.getId()) {
                    return "redirect:/book";
                }
            }
            this.cartService.addToCart(book);
        }
        return "redirect:/book";
    }

    @GetMapping(path = "/book/cart")
    public String showCart(Model model) {
        List<Book> books = this.cartService.getCart();
        model.addAttribute("books", books);
        return "Cart/cart";
    }

    @GetMapping(path = "/book/cart/{id}/remove")
    public String removeFromCart(@PathVariable("id") long id) {
        this.cartService.removeFromCart(id);
        return "redirect:/book/cart?success";
    }
}
