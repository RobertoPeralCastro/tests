package com.amaris.inditex.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PriceRequestDto
{
    @ApiModelProperty(dataType = "int",example ="35455")
    private int productId;
    @ApiModelProperty(dataType = "int",example = "1")
    private int brandId;
    @JsonFormat(pattern="yyyy-MM-dd-HH.mm.ss")
    @ApiModelProperty(dataType = "java.util.Date",example = "2020-06-14-11.24.49")
    private Date requestDate;
}
