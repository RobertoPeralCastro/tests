package com.amaris.inditex;

import inditex.entities.Prices;
import inditex.repositories.PricesRepository;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
		LocalDateTime dateTime = LocalDateTime.parse(stringDate, formatter);
		Prices priceResult = pricesRepository.findFirstByProductIdAndBrandIdAndEndDateAfterAndStartDateBeforeOrderByPriorityDesc(productId,brandId,dateTime,dateTime);
		assert(priceResult.getPriceList()==priceList);
	}
}
