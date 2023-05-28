package fit.hutech.spring.repositories;

import fit.hutech.spring.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Long>{
}
