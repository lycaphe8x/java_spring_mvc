package fit.hutech.spring.repositories;

import fit.hutech.spring.entities.ItemInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemInvoiceRepository extends JpaRepository<ItemInvoice, Long>{
}
