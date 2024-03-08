package bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    @DisplayName("Al crear una cuenta no se le puede pasar un valor negativo")
    public void newBankAccountWithNegativeBalanceReturnException(){
        int negativeBalance = -200;
        
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(negativeBalance));
    }

    @Test
    @DisplayName("Al consultar el saldo de una cuenta nos debe devolver su cantidad correspondiente")
    public void getBalanceShouldReturnTheAmount(){
        BankAccount cuenta = new BankAccount(50);
        int expectedBalance = 50;

        int balance = cuenta.getBalance();

        assertEquals(balance, expectedBalance);
    }

    @Test
    @DisplayName("Una cuenta recien creada tiene un balance de 0")
    public void newBankAccountHaveZeroBalance() {
        BankAccount cuenta = new BankAccount(0);
        int expectedBalance = 0;

        int balance = cuenta.getBalance();

        assertEquals(balance, expectedBalance);
    }

    @Test
    @DisplayName("Una cuenta con dinero suficiente debe poder retirar fondos")
    public void shouldWithdrawTheAmount() {
        BankAccount cuenta = new BankAccount(100);
        int expectedBalance = 50;

        cuenta.withdraw(50);
        int balance = cuenta.getBalance();

        assertEquals(balance,expectedBalance);
    }
    
    @Test
    @DisplayName("No puedes retirar fondos sin el dinero suficiente")
    public void shouldNotWithdrawWithNotEnoughBalance() {
        BankAccount cuenta = new BankAccount(100);

        boolean result = cuenta.withdraw(150);

        assertFalse(result);
    }

    @Test
    @DisplayName("Al retirar un valor negativo de dinero debe saltar una excepcion")
    public void withdrawNegativeAmountThrowsExcepcion() {
        BankAccount cuenta = new BankAccount(100);
        int negativeAmount = -30;

        assertThrows(IllegalArgumentException.class, () -> cuenta.withdraw(negativeAmount));
    }

    @Test
    @DisplayName("Al depositar dinero debe sumarse")
    public void depositAmountShouldBeAdded(){
        BankAccount cuenta = new BankAccount(100);
        int expectedBalance = 170;

        int balance = cuenta.deposit(70);

        assertEquals(balance,expectedBalance);
    }

    @Test
    @DisplayName("No se puede depositar un valor negativo")
    public void depositNegativeAmountReturnException(){
        BankAccount cuenta = new BankAccount(100);
        int negativeAmount = -30;

        assertThrows(IllegalArgumentException.class, () -> cuenta.deposit(negativeAmount));
    }

    @Test
    @DisplayName("El calculo del prestamo debe ser correcto")
    public void paymentShouldReturnTheCalculatedPayment(){
        BankAccount cuenta = new BankAccount(100);
        double amount = 1000;
        double interes = 0.001;
        int months = 12;
        double expectedPayment = amount*(interes*Math.pow((1+interes), months)/(Math.pow((1+interes), months)-1));

        double calculatedPayment = cuenta.payment(amount, interes, months);

        assertEquals(calculatedPayment, expectedPayment);
    }

    @Test
    @DisplayName("No se puede calcular el prestamo con la cantidad negativa")
    public void paymentWithNegativeAmountReturnException(){
        BankAccount cuenta = new BankAccount(100);
        double negativeAmount = -1000;
        double interes = 0.001;
        int months = 12;

        assertThrows(IllegalArgumentException.class, () -> cuenta.payment(negativeAmount, interes, months));
    }
 
    @Test
    @DisplayName("No se puede calcular el prestamo con un interes negativo")
    public void paymentWithNegativeInterestReturnException(){
        BankAccount cuenta = new BankAccount(100);
        double amount = 1000;
        double negativeInteres = -0.001;
        int months = 12;

        assertThrows(IllegalArgumentException.class, () -> cuenta.payment(amount, negativeInteres, months));
    }   

    @Test
    @DisplayName("No se puede calcular el prestamo con un numero de meses negativo")
    public void paymentWithNegativeMonthsReturnException(){
        BankAccount cuenta = new BankAccount(100);
        double amount = 1000;
        double interes = 0.001;
        int negativeMonths = -12;

        assertThrows(IllegalArgumentException.class, () -> cuenta.payment(amount, interes, negativeMonths));
    }
    
    @Test
    @DisplayName("No se puede calcular el prestamo con 0 meses")
    public void paymentWithZeroMonthsReturnException(){
        BankAccount cuenta = new BankAccount(100);
        double amount = 1000;
        double interes = 0.001;
        int months = 0;

        assertThrows(IllegalArgumentException.class, () -> cuenta.payment(amount, interes, months));
    }
    
    @Test
    @DisplayName("El calculo de lo que queda pendiente debe ser correcto")
    public void pendingtShouldReturnTheCalculatedPendingWithPositiveMonths(){
        BankAccount cuenta = new BankAccount(100);
        double amount = 1000;
        double interes = 0.001;
        int npayments = 12;
        int month = 2;
        double ant = cuenta.pending(amount, interes, npayments, month-1);
        double expectedPending = ant - (cuenta.payment(amount,interes,npayments) - interes*ant);

        double calculatedPending = cuenta.pending(amount, interes, npayments, month);

        assertEquals(calculatedPending, expectedPending);
    }

    @Test
    @DisplayName("El calculo de lo que queda pendiente en el mes cero debe ser correcto")
    public void pendingtShouldReturnTheCalculatedPendingWithZeroMonths(){
        BankAccount cuenta = new BankAccount(100);
        double amount = 1000;
        double interes = 0.001;
        int npayments = 12;
        int month = 0;
        double expectedPending = amount;

        double calculatedPending = cuenta.pending(amount, interes, npayments, month);

        assertEquals(calculatedPending, expectedPending);
    }

    @Test
    @DisplayName("El calculo de lo que queda pendiente con un amount negativo debe devolver una excepcion")
    public void pendingWithNegativeAmountReturnException(){
        BankAccount cuenta = new BankAccount(100);
        double amount = -1000;
        double interes = 0.001;
        int npayments = 12;
        int month = 2;

        assertThrows(IllegalArgumentException.class, () -> cuenta.pending(amount,interes,npayments,month));
    }
    
    @Test
    @DisplayName("El calculo de lo que queda pendiente con un interes negativo debe devolver una excepcion")
    public void pendingWithNegativeInteresReturnException(){
        BankAccount cuenta = new BankAccount(100);
        double amount = 1000;
        double interes = -0.001;
        int npayments = 12;
        int month = 2;

        assertThrows(IllegalArgumentException.class, () -> cuenta.pending(amount,interes,npayments,month));
    }
   
    @Test
    @DisplayName("El calculo de lo que queda pendiente con el numero de pagos negativo debe devolver una excepcion")
    public void pendingWithNegativeNpaymentsReturnException(){
        BankAccount cuenta = new BankAccount(100);
        double amount = 1000;
        double interes = 0.001;
        int npayments = -12;
        int month = 2;

        assertThrows(IllegalArgumentException.class, () -> cuenta.pending(amount,interes,npayments,month));
    }
    
    @Test
    @DisplayName("El calculo de lo que queda pendiente con cero numero de pagos debe devolver una excepcion")
    public void pendingWithZeroNpaymentsReturnException(){
        BankAccount cuenta = new BankAccount(100);
        double amount = 1000;
        double interes = 0.001;
        int npayments = 0;
        int month = 2;

        assertThrows(IllegalArgumentException.class, () -> cuenta.pending(amount,interes,npayments,month));
    }
    
    @Test
    @DisplayName("El calculo de lo que queda pendiente con el mes negativo debe devolver una excepcion")
    public void pendingWithNegativeMonthReturnException(){
        BankAccount cuenta = new BankAccount(100);
        double amount = 1000;
        double interes = 0.001;
        int npayments = 12;
        int month = -2;

        assertThrows(IllegalArgumentException.class, () -> cuenta.pending(amount,interes,npayments,month));
    }
}
