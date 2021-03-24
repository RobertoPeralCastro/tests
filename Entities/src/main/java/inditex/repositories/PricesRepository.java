package inditex.repositories;

import inditex.entities.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface PricesRepository extends JpaRepository<Prices, Long>
{
   // @Query("SELECT product_id, brand_id, price_list, start_date, end_date, price FROM Prices WHERE product_id = :productId and brand_id = :brandId and date ")

    public Prices findFirstByProductIdAndBrandIdAndEndDateAfterAndStartDateBeforeOrderByPriorityDesc(int productId, int brandId, Date date, Date sameDate);
}
