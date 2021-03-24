package com.amaris.inditex;

import inditex.entities.Prices;
import inditex.enums.Curr;
import inditex.repositories.PricesRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@DataJpaTest
@EnableJpaRepositories(basePackages = "inditex.repositories")
@ContextConfiguration(
		classes = {Prices.class},
		loader = AnnotationConfigContextLoader.class)
@EntityScan(basePackages = "inditex.entities")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InditexTestApplicationTests
{

	@Autowired
	PricesRepository pricesRepository;

	/**
	 * Method that provides the parameters and expected result of parameterized test "testReturnedValuesFound"
	 * the parameters are productId, BrandId, request date
	 * the last number of each Argument is the expected result for that line, the priceList.
	 * @return
	 */
	private static Stream<Arguments> pricesArguments() {
		return Stream.of(
			arguments(35455,1,"2020-06-14-10.00.00",1),
			arguments(35455,1,"2020-06-14-16.00.00",2),
			arguments(35455,1,"2020-06-14-21.00.00",1),
			arguments(35455,1,"2020-06-15-10.00.00",3),
			arguments(35455,1,"2020-06-16-21.00.00",4)
		);
	}

	/**
	 * Parameterized test Method that tests the query from the repository that returns the price list for a product belonging to a brand
	 * in a concrete date and time. Parameters and expected results are specified in pricesArguments method.
	 * @param productId
	 * @param brandId
	 * @param stringDate
	 * @param priceList expected result for the query
	 * @throws ParseException
	 */
	@ParameterizedTest
	@MethodSource("pricesArguments") // six numbers
	public void testReturnedValuesFound(int productId,int brandId,String stringDate, int priceList) throws ParseException, ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		Date date=simpleDateFormat.parse(stringDate);
		Prices priceResult = pricesRepository.findFirstByProductIdAndBrandIdAndEndDateAfterAndStartDateBeforeOrderByPriorityDesc(productId,brandId,date,date);
		assert(priceResult.getPriceList()==priceList);
	}


	/**
	 * Method executed before all the tests which loads data into database using hibernate entities.
	 * @throws Exception
	 */
	@BeforeAll
	public void loadTestData() throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		Prices price1 = new Prices();
		price1.setBrandId(1);
		price1.setStartDate(simpleDateFormat.parse("2020-06-14-00.00.00"));
		price1.setEndDate(simpleDateFormat.parse("2020-12-31-23.59.59"));
		price1.setPriceList(1);
		price1.setProductId(35455);
		price1.setPriority(0);
		price1.setPrice(35.50);
		price1.setCurrency(Curr.EUR);
		pricesRepository.save(price1);

		Prices price2 = new Prices();
		price2.setBrandId(1);
		price2.setStartDate(simpleDateFormat.parse("2020-06-14-15.00.00"));
		price2.setEndDate(simpleDateFormat.parse("2020-06-14-18.30.00"));
		price2.setPriceList(2);
		price2.setProductId(35455);
		price2.setPriority(1);
		price2.setPrice(25.45);
		price2.setCurrency(Curr.EUR);
		pricesRepository.save(price2);

		Prices price3 = new Prices();
		price3.setBrandId(1);
		price3.setStartDate(simpleDateFormat.parse("2020-06-15-00.00.00"));
		price3.setEndDate(simpleDateFormat.parse("2020-06-15-11.00.00"));
		price3.setPriceList(3);
		price3.setProductId(35455);
		price3.setPriority(1);
		price3.setPrice(30.50);
		price3.setCurrency(Curr.EUR);
		pricesRepository.save(price3);


		Prices price4 = new Prices();
		price4.setBrandId(1);
		price4.setStartDate(simpleDateFormat.parse("2020-06-15-16.00.00"));
		price4.setEndDate(simpleDateFormat.parse("2020-12-31-23.59.59"));
		price4.setPriceList(4);
		price4.setProductId(35455);
		price4.setPriority(1);
		price4.setPrice(38.95);
		price4.setCurrency(Curr.EUR);
		pricesRepository.save(price4);
	}
}
