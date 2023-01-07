package jpabook.jpashop.web;

import jpabook.jpashop.domain.item.Book;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BookForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    @Builder
    public BookForm(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }

    public static BookForm from(Book book){
            return BookForm.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .price(book.getPrice())
                    .stockQuantity(book.getStockQuantity())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .build();
    }

    public  Book toBookEntity(){
        return Book.builder()
                .id(this.getId())
                .name(this.getName())
                .price(this.getPrice())
                .stockQuantity(this.getStockQuantity())
                .author(this.getAuthor())
                .isbn(this.getIsbn())
                .build();
    }

}
