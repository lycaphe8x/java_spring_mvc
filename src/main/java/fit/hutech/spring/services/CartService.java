package fit.hutech.spring.services;

import fit.hutech.spring.daos.Cart;
import fit.hutech.spring.daos.Item;
import fit.hutech.spring.entities.Invoice;
import fit.hutech.spring.entities.ItemInvoice;
import fit.hutech.spring.repositories.IBookRepository;
import fit.hutech.spring.repositories.IInvoiceRepository;
import fit.hutech.spring.repositories.IItemInvoiceRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private static final String CART_SESSION_KEY = "cart";

    private final IInvoiceRepository invoiceRepository;

    private final IItemInvoiceRepository itemInvoiceRepository;

    private final IBookRepository bookRepository;

    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public Cart getCart(@NotNull HttpSession session) {
        return Optional.ofNullable((Cart) session.getAttribute(CART_SESSION_KEY))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    session.setAttribute(CART_SESSION_KEY, cart);
                    return cart;
                });
    }

    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public void updateCart(@NotNull HttpSession session, Cart cart) {
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeCart(@NotNull HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }

    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public int getSumQuantity(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToInt(Item::getQuantity)
                .sum();
    }

    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public double getSumPrice(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public void saveCart(@NotNull HttpSession session) {
        var cart = getCart(session);
        if (cart.getCartItems().isEmpty()) return;

        var invoice = new Invoice();
        invoice.setInvoiceDate(new Date(new Date().getTime()));
        invoice.setPrice(getSumPrice(session));
        invoiceRepository.save(invoice);

        cart.getCartItems().forEach(item -> {
            var items = new ItemInvoice();
            items.setInvoice(invoice);
            items.setQuantity(item.getQuantity());
            items.setBook(bookRepository.findById(item.getBookId()).orElseThrow());
            itemInvoiceRepository.save(items);
        });

        removeCart(session);
    }
}
