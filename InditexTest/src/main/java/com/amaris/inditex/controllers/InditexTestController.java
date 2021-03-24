package com.amaris.inditex.controllers;

import com.amaris.inditex.dtos.PriceRequestDto;
import com.amaris.inditex.exceptions.PriceNotFoundException;
import com.amaris.inditex.services.InditexTestService;
import inditex.entities.Prices;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequestMapping("/inditexTest")
@Api("inditex-api")
public class InditexTestController
{

    @Autowired
    InditexTestService inditexTestService;
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * endpoint to obtain the prices of the products filtered by productId, brandId, and applicationDate.
     * @param priceRequestDto dto with the parameters of the request
     * @return
     */
    @PostMapping("/getProductPriceForDateAndBrand")
    public ResponseEntity getProductPriceForDateAndBrand(@RequestBody PriceRequestDto priceRequestDto)
    {
        logger.debug("ITC_001 getProductPriceForDateAndBrand start");
        try
        {
            Prices result = inditexTestService.getProductPriceForDateAndBrand(priceRequestDto.getProductId(), priceRequestDto.getBrandId(),priceRequestDto.getRequestDate());
            logger.debug("ITC_100 getProductPriceForDateAndBrand finished ok");
            return ResponseEntity.ok(Optional.of(result));
        }
        catch(PriceNotFoundException pnfe)
        {
            SimpleDateFormat format = new SimpleDateFormat();
            String errorMessage = "ITC_003 product prices not found for parameters productId: " + priceRequestDto.getProductId() + " brandId: " + priceRequestDto.getBrandId() + " applicationDate: " + format.format(priceRequestDto.getRequestDate());
            logger.error(errorMessage);
            return ResponseEntity.of((Optional.of(errorMessage)));
        }
        catch (Exception e)
        {
            logger.error("ITC_004 unexpected error in application. " + ExceptionUtils.getStackTrace(e));
            return ResponseEntity.of((Optional.of(e.getMessage())));
        }
    }
}
