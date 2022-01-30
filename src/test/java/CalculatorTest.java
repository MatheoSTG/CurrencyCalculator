import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    private static final String FILEPATH = "src/test/resources/eurofxref-daily-test.xml";

    private Calculator testCalculator;

    //convertEuros() tests
    @Test
    public void given100Euros_whenConvertEuros_thanReturn_113_35_Usd() {
        testCalculator = new Calculator(FILEPATH);
        testCalculator.setCurrentCurrency("USD");
        BigDecimal usdValue = testCalculator.convertEuros(new BigDecimal("100.00"));
        assertEquals(new BigDecimal("111.35"), usdValue);
    }

    @Test
    public void givenMinus100Euros_whenConvertEuros_thanReturn0Usd() {
        testCalculator = new Calculator(FILEPATH);
        testCalculator.setCurrentCurrency("USD");
        BigDecimal usdValue = testCalculator.convertEuros(new BigDecimal("-100.00"));
        assertEquals(BigDecimal.ZERO, usdValue);
    }

    @Test
    public void givenNullObject_whenConvertEuros_thanReturn0Usd() {
        testCalculator = new Calculator(FILEPATH);
        testCalculator.setCurrentCurrency("USD");
        BigDecimal usdValue = testCalculator.convertEuros(null);
        assertEquals(BigDecimal.ZERO, usdValue);
    }

    //setCurrency() tests
    @Test
    public void givenProperCurrency_whenSetCurrentCurrency_thanReturn1() {
        testCalculator = new Calculator(FILEPATH);
        int returnedValue = testCalculator.setCurrentCurrency("USD");

        assertEquals(1, returnedValue);
    }

    @Test
    public void givenNotExistingCurrency_whenSetCurrentCurrency_thanReturn0() {
        testCalculator = new Calculator(FILEPATH);
        int returnedValue = testCalculator.setCurrentCurrency("XXX");

        assertEquals(0, returnedValue);
    }

    @Test
    public void givenNull_whenSetCurrentCurrency_thanReturn0() {
        testCalculator = new Calculator(FILEPATH);
        int returnedValue = testCalculator.setCurrentCurrency(null);

        assertEquals(0, returnedValue);
    }

    //getCurrenciesSet() tests
    @Test
    public void givenTestXml_whenGetCurrenciesSet_thanReturn22Elements() {
        testCalculator = new Calculator(FILEPATH);
        int size = testCalculator.getCurrenciesSet().size();

        assertEquals(22, size);
    }

}
