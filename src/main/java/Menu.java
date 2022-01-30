import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Class containing user interface for converting Euros.
 * <p>Show user proper options to choose from, also taking inputs from user.</p>
 *
 * @author Mateusz Prill
 * @version 1.0
 */
public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private final Calculator calculator;

    public Menu(Calculator calculator) {
        this.calculator = calculator;
    }

    /**
     * Method showing user menu, also takes his actions and redirects to other methods.
     *
     * <p>This method should end only when user quit it by selecting appropriate option or
     * critical error appears.</p>
     *
    */
    public void showMenu() {
        System.out.println("Welcome to Currency Calculator! You can change Euros to one of other given currencies.\n");

        int choice = 1;
        while (choice != 3) {
            if (calculator.getCurrentCurrency().equals("")) {
                System.out.println("No target currency set! \n");
            } else {
                System.out.println("Target currency: " + calculator.getCurrentCurrency() + "\n");
            }
            System.out.println("1. Convert Euros");
            System.out.println("2. Change currency");
            System.out.println("3. Quit");
            System.out.println();

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Action " + scanner.nextLine() + " not found, try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    convertEuros();
                    break;
                case 2:
                    changeCurrency();
                    break;
                case 3:
                    System.out.println("Exiting application.");
                    break;
                default:
                    System.out.println("Action not found, try again.");
            }
        }

        //closing scanner after quiting program
        scanner.close();
    }


    /**
     * Changes target currency used for converting euros by getting proper user input.
     *
     */
    private void changeCurrency() {
        //Get list of all currencies from map with currencies-rates relations stored in calculator
        List<String> currenciesList = new ArrayList<>(calculator.getCurrenciesSet());

        //Boolean value for while loop, only change when input changed currency
        boolean noChoice = true;

        int currenciesPerPage = 8;

        //Current page of currenciesList for easier navigation.
        int page = 0;

        int lastPage = (int) Math.ceil((currenciesList.size() - 1) / currenciesPerPage);

        //Main loop for method - ends only when proper input is given by user
        while (noChoice) {

            //Getting as many currencies per page as specified in currenciesPerPage
            for (int i = 0; i < currenciesPerPage; i++) {
                int currentIndex = i + (page * 8);

                if (currentIndex >= currenciesList.size()) {
                    break;
                }
                System.out.println((i+1) + ". " + currenciesList.get(currentIndex));
            }

            //No next page when its last one
            if (page < lastPage) {
                System.out.println("9. Next currencies");
            }

            //Can't get to previous page on 1st page
            if (page > 0) {
                System.out.println("0. Previous currencies");
            }

            //Variable for storing user choice
            int choice;

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                //If bad data provided - set choice to incorrect value that end this iteration
                choice = -1;
            }

            //Empty scanner to be ready for next data
            scanner.nextLine();

            if (choice < 0) {
                System.out.println("Action not found, try again. \n");
            } else if (choice > 9) {
                System.out.println("Action not found, try again. \n");
            } else if (choice == 0) {
                //Need to check if first page to prevent out of bound index
                if (page > 0) {
                    page--;
                    System.out.println();
                } else {
                    System.out.println("Action not found, try again. \n");
                }
            } else if (choice == 9) {
                //Need to check if last page to prevent out of bound index
                if (page < lastPage) {
                    page++;
                    System.out.println();
                } else {
                    System.out.println("Action not found, try again. \n");
                }
            } else {
                //Indexes are numerated from 0, and choices from 1
                int chosenIndex = choice - 1 + (page * 8);
                String currency = currenciesList.get(chosenIndex);

                //There's no need to check returned value of this method, because it was taken from map keys list
                calculator.setCurrentCurrency(currency);
                System.out.println("Currency set to " + currency + ".\n");
                noChoice = false;
            }


        }

    }

    /**
     * Method for converting euros to target value.
     *
     * <p>Takes user data and passes it to Calculator class, than show converted value as a message.</p>
     */
    private void convertEuros() {
        //If currency is not initialized or set as blank String user need to pick one from list before converting money
        if( (calculator.getCurrentCurrency() == null) || (calculator.getCurrentCurrency().equals("")) ) {
            System.out.println("First you need to choose the target currency.");
            this.changeCurrency();
        }

        BigDecimal euros = BigDecimal.ZERO;
        boolean isBadFormat = true;

        //As long as user don't provide correct value, loop will ask again to enter data
        while (isBadFormat) {
            System.out.println("Enter amount of Euros in format XX.XX, or XX. " +
                    "It must be greater than zero, also data will be rounded up to 2nd decimal part:");
            String eurosString = scanner.nextLine();

            Optional<BigDecimal> optionalEuros = checkInputEuros(eurosString);

            //if there is value in optional loop will end
            if (optionalEuros.isPresent()) {
                euros = optionalEuros.get();
                isBadFormat = false;
            } else {
                System.out.println("Bad format of data. Try again. \n");
            }

        }

        //Calculations are done in calculator method with the same name
        BigDecimal afterConversion = calculator.convertEuros(euros);
        String currency = calculator.getCurrentCurrency();

        System.out.println("-----------------------------------------------");
        System.out.println(euros + " EUR =");
        System.out.println(afterConversion + " " + currency);
        System.out.println("-----------------------------------------------");
        System.out.println("Press enter to continue.");

        //Waiting until user press enter
        scanner.nextLine();

    }

    /**
     * Method for checking if user input can be converted to BigDecimal.
     * <p>It also cheks if the value is greater than zero.</p>
     *
     * @param euros String given by user, it should contains amount of euros in correct data
     * @return If data can't be converted to BigDecimal or is less than 0 - returns empty Optional. In other cases it returns Big Decimal value wrapped by Optional object.
     */
    public Optional<BigDecimal> checkInputEuros(String euros){

        Optional<BigDecimal> returnValue;
        BigDecimal eurosBigDecimal;

        try {
            //Rounding up given value to 2nd decimal part
            eurosBigDecimal = new BigDecimal(euros).setScale(2, RoundingMode.HALF_UP);

        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        //If euros after conversion are positive value - write it as returning value
        if(eurosBigDecimal.compareTo(BigDecimal.ZERO) > 0) {
            returnValue = Optional.of(eurosBigDecimal);
        } else {
            returnValue = Optional.empty();
        }

        return returnValue;
    }
}
