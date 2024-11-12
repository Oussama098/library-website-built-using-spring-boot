package ous.LabraryWebSite.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.w3c.dom.Text;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "the title should not be empty")
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @NotNull(message = "the publisherDate should not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publisherDate;
    @NotNull(message = "the isbn should not be empty")
    @Column(nullable = false)
    private String isbn;
    @Column(nullable = false)
    @NotNull(message = "the pager should not be empty")
    private Long pages;
    private String description;
//    private String url;
    private String image;
    @NotNull(message = "the quantity should not be empty")
    private int quantity;
    @NotNull(message = "the price should not be empty")
    private float price;
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Category category;
    @NotEmpty(message = "the author should not be empty")
    private String author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date addingDate=new Date();

}
