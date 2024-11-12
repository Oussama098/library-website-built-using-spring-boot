package ous.LabraryWebSite.Services;

import lombok.Data;
import org.springframework.stereotype.Service;
import ous.LabraryWebSite.models.Book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Service
public class CartService {
    private List<Book> cart;
    private final BookService bookService;

    public CartService(BookService bookService) {
        cart = new ArrayList<>();
        this.bookService = bookService;
    }
    public void addToCart(Book book) {
        if(book != null) {
            cart.add(book);
        }
    }



    public long getSizeOfCart() {
        return cart.size();
    }

    public void removeFromCart(long id) {
        Book book = this.bookService.getBookById(id);
        for (Iterator<Book> iterator = cart.iterator(); iterator.hasNext(); ) {
            Book book1 = iterator.next();
            if(book1.getId() == book.getId()) {
                iterator.remove();
                break;
            }
        }

    }


}
