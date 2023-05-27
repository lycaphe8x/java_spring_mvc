package fit.hutech.spring.viewmodels;

import fit.hutech.spring.entities.Book;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BookPostVm(String title, String author, Double price, Long categoryId) {
    public static BookPostVm from(@NotNull Book book) {
        return BookPostVm.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .categoryId(book.getCategory().getId())
                .build();
    }
}
