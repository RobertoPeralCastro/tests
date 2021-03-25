package com.amaris.inditex.services;

import com.amaris.inditex.exceptions.PriceNotFoundException;
import inditex.entities.Prices;
import inditex.repositories.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InditexTestService
{
    @Autowired
    PricesRepository pricesRepository;

    /**
     * Asks for the price of a product in a concrete period of time, and throws an exception when not found.
     * @param productId the id of the product
     * @param brandId the id of the brand of the product
     * @param applicationDate the date in which the price is requested
     * @return the information of the price in the entity Prices
     * @throws PriceNotFoundException
     */
    public Optional<Prices> getProductPriceForDateAndBrand(int productId, int brandId, LocalDateTime applicationDate)
    {
        return Optional.ofNullable(pricesRepository.getProductPriceByProductIdAndBrandIdAndDate(productId, brandId, applicationDate));
    }

}
