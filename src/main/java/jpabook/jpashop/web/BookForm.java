package jpabook.jpashop.web;

import jpabook.jpashop.domain.item.Book;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

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
