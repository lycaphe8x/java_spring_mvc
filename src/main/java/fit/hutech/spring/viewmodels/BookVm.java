package fit.hutech.spring.viewmodels;

import fit.hutech.spring.entities.Book;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BookVm(Long id, String name, String author, Double price, Long categoryId) {
    public static BookVm fromEntity(@NotNull Book book)
    {
        return BookVm.builder()
                .id(book.getId())
                .name(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice()).build();
    }
}
