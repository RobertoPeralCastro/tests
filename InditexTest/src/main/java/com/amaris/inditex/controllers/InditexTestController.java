package com.amaris.inditex.controllers;

import com.amaris.inditex.services.InditexTestService;
import inditex.entities.Prices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
     * @param productId
     * @param brandId
     * @param requestDate
     * @return
     */
    @GetMapping("/price/{productId}/{brandId}/{requestDate}")
    public ResponseEntity getProductPriceForDateAndBrand(
            @ApiParam(value = "an integer value representing the identifier of a product." ,	defaultValue = "35455") @PathVariable int productId,
            @ApiParam(value = "an integer value representing the brand of a product." ,	defaultValue = "1") @PathVariable int brandId,
            @ApiParam(value = "a date representing the moment for which the price of the product is required.",	defaultValue = "2020-06-14-18.30.00")
            @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
            @PathVariable LocalDateTime requestDate)
    {
        logger.debug("ITC_001 getProductPriceForDateAndBrand start");
        try
        {
            Optional<Prices> result = inditexTestService.getProductPriceForDateAndBrand(productId, brandId,requestDate);
            return result.map(c->ResponseEntity.ok().body(c)).orElse(ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            logger.error("ITC_004 unexpected error in application. " + ExceptionUtils.getStackTrace(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
