package fit.hutech.spring.services;

import fit.hutech.spring.entities.Category;
import fit.hutech.spring.repositories.ICategoryRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final ICategoryRepository categoryRepository;

    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void updateCategory(@NotNull Category category) {
        Category existingCategory = categoryRepository.findById(category.getId()).orElse(null);
        Objects.requireNonNull(existingCategory).setName(category.getName());
        categoryRepository.save(existingCategory);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
