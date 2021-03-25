package inditex.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import inditex.enums.Curr;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="prices")
@Data
public class Prices
{
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    @Column(name="brand_id", nullable=false, unique=false)
    private int brandId;
    @Column(name="start_date", nullable=false, unique=false)
    @JsonFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime startDate;
    @Column(name="end_date", nullable=false, unique=false)
    @JsonFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime endDate;
    @Column(name="price_list", nullable=false, unique=false)
    private int priceList;
    @Column(name="product_id", nullable=false, unique=false)
    private int productId;
    @Column(name="priority", nullable=false, unique=false)
    private Integer priority;
    @Column(name="price", nullable=false, unique=false)
    private Double price;
    @Enumerated(EnumType.STRING)
    private Curr currency;
}

