package com.amaris.inditex.services;

import com.amaris.inditex.exceptions.PriceNotFoundException;
import inditex.entities.Prices;
import inditex.repositories.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public Prices getProductPriceForDateAndBrand(int productId, int brandId, Date applicationDate) throws PriceNotFoundException
    {
        Prices result = pricesRepository.findFirstByProductIdAndBrandIdAndEndDateAfterAndStartDateBeforeOrderByPriorityDesc(productId,brandId,applicationDate,applicationDate);
        if (result != null)
        {
            return result;
        }
        else
        {
            throw new PriceNotFoundException();
        }
    }

}
