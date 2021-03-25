package inditex.repositories;

import inditex.entities.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Repository
public interface PricesRepository extends JpaRepository<Prices, Long>
{
    @Query("SELECT p FROM Prices p WHERE productId = :productId and brandId = :brandId and :date >= startDate and :date <= endDate order by priority desc limit 1 ")
    public Prices getProductPriceByProductIdAndBrandIdAndDate(@Param("productId") int productId, @Param("brandId")int brandId, @Param("date")LocalDateTime date);
}
