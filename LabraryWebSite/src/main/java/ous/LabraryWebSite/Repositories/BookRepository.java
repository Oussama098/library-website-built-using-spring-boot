package ous.LabraryWebSite.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ous.LabraryWebSite.models.Book;

import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b , Category c where b.category.id = c.id and b.category.id = :id")
    List<Book> getBooksByCategoryId(@Param("id") Long id);

    @Query("SELECT b FROM Book b WHERE b.addingDate >= :oneWeekAgo")
    List<Book> getBooksRecentlyAdded(@Param("oneWeekAgo") Date oneWeekAgo);

}
