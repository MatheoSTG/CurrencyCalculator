import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MenuTest {

    private static final String FILEPATH = "src/test/resources/eurofxref-daily-test.xml";
    private final Menu testMenu = new Menu(new Calculator(FILEPATH));

    //checkInputEuros() tests
    @Test
    public void givenProperString_whenCheckInputEuros_returnProperObject() {
        String properString = "130.12";
        Optional<BigDecimal> returnedValue = testMenu.checkInputEuros(properString);

        assertEquals(new BigDecimal(properString), returnedValue.get());
    }

    @Test
    public void givenInvalidString_whenCheckInputEuros_returnEmptyOptional() {
        String properString = "abcd";
        Optional<BigDecimal> returnedValue = testMenu.checkInputEuros(properString);

        assertFalse(returnedValue.isPresent());
    }

    @Test
    public void givenNegativeValue_whenCheckInputEuros_returnEmptyOptional() {
        String properString = "-112";
        Optional<BigDecimal> returnedValue = testMenu.checkInputEuros(properString);

        assertFalse(returnedValue.isPresent());
    }
}
