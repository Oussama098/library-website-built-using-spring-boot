package ous.LabraryWebSite.Services;

import org.springframework.stereotype.Service;
import ous.LabraryWebSite.Repositories.BookRepository;
import ous.LabraryWebSite.models.Book;
import ous.LabraryWebSite.models.Category;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;
    private CategoryService categoryService;
    public BookService(BookRepository bookRepository, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
    }

    public void CreateBook(Book book) {
        this.bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book getBookById(long id) {
        return this.bookRepository.findById(id).orElse(null);
    }

    public void updateBook(Book book) {
        Book bookExested = getBookById(book.getId());
        if(bookExested != null) {
            bookExested.setTitle(book.getTitle());
            bookExested.setAuthor(book.getAuthor());
            bookExested.setCategory(categoryService.getCategoryById(book.getCategory().getId()));
            bookExested.setDescription(book.getDescription());
            bookExested.setPages(book.getPages());
            bookExested.setPrice(book.getPrice());
            bookExested.setQuantity(book.getQuantity());
            bookExested.setImage(book.getImage());
            bookExested.setIsbn(book.getIsbn());
            bookExested.setPublisherDate(book.getPublisherDate());
            bookRepository.save(bookExested);
        }
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBooksByCategory(long id){
        List<Book> books = this.bookRepository.getBooksByCategoryId(id);
        if (books.isEmpty()) return null;
        return books;
    }

    public List<Book> getBookRecentlyAdded(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Date oneWeekAgo = calendar.getTime();
        return this.bookRepository.getBooksRecentlyAdded(oneWeekAgo);
    }
}
