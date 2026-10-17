package VendingMachineAI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {
    private VendingMachine vendingMachine;

    @BeforeEach
    void setUp() {
        vendingMachine = new VendingMachine();
    }

    @Test
    void constructorStartsWithZeroBalanceAndEmptySlots() throws VendingMachineException {
        assertEquals(0.0, vendingMachine.getBalance(), 0.000001);
        assertNull(vendingMachine.getItem(VendingMachine.A_CODE));
        assertNull(vendingMachine.getItem(VendingMachine.B_CODE));
        assertNull(vendingMachine.getItem(VendingMachine.C_CODE));
        assertNull(vendingMachine.getItem(VendingMachine.D_CODE));
    }

    @Test
    void addItemPlacesItemInEachValidSlot() throws VendingMachineException {
        VendingMachineItem itemA = new VendingMachineItem("Water", 1.25);
        VendingMachineItem itemB = new VendingMachineItem("Chips", 1.50);
        VendingMachineItem itemC = new VendingMachineItem("Juice", 2.00);
        VendingMachineItem itemD = new VendingMachineItem("Candy", 0.75);

        vendingMachine.addItem(itemA, VendingMachine.A_CODE);
        vendingMachine.addItem(itemB, VendingMachine.B_CODE);
        vendingMachine.addItem(itemC, VendingMachine.C_CODE);
        vendingMachine.addItem(itemD, VendingMachine.D_CODE);

        assertSame(itemA, vendingMachine.getItem(VendingMachine.A_CODE));
        assertSame(itemB, vendingMachine.getItem(VendingMachine.B_CODE));
        assertSame(itemC, vendingMachine.getItem(VendingMachine.C_CODE));
        assertSame(itemD, vendingMachine.getItem(VendingMachine.D_CODE));
    }

    @Test
    void addItemRejectsAnOccupiedSlot() throws VendingMachineException {
        VendingMachineItem original = new VendingMachineItem("Water", 1.00);
        VendingMachineItem replacement = new VendingMachineItem("Soda", 1.50);
        vendingMachine.addItem(original, VendingMachine.A_CODE);

        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> vendingMachine.addItem(replacement, VendingMachine.A_CODE));

        assertEquals("Slot A already occupied", exception.getMessage());
        assertSame(original, vendingMachine.getItem(VendingMachine.A_CODE));
    }

    @Test
    void addItemRejectsAnInvalidSlotCode() {
        VendingMachineItem item = new VendingMachineItem("Water", 1.00);

        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> vendingMachine.addItem(item, "E"));

        assertEquals("Invalid code for vending machine item", exception.getMessage());
    }

    @Test
    void getItemRejectsAnInvalidSlotCode() {
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> vendingMachine.getItem("Z"));

        assertEquals("Invalid code for vending machine item", exception.getMessage());
    }

    @Test
    void removeItemReturnsTheItemAndEmptiesItsSlot() throws VendingMachineException {
        VendingMachineItem item = new VendingMachineItem("Water", 1.00);
        vendingMachine.addItem(item, VendingMachine.B_CODE);

        VendingMachineItem removed = vendingMachine.removeItem(VendingMachine.B_CODE);

        assertSame(item, removed);
        assertNull(vendingMachine.getItem(VendingMachine.B_CODE));
    }

    @Test
    void removeItemRejectsAnEmptySlot() throws VendingMachineException {
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> vendingMachine.removeItem(VendingMachine.C_CODE));

        assertEquals("Slot C is empty -- cannot remove item", exception.getMessage());
        assertNull(vendingMachine.getItem(VendingMachine.C_CODE));
    }

    @Test
    void removeItemRejectsAnInvalidSlotCode() {
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> vendingMachine.removeItem(""));

        assertEquals("Invalid code for vending machine item", exception.getMessage());
    }

    @Test
    void insertMoneyAddsAmountToBalance() throws VendingMachineException {
        vendingMachine.insertMoney(1.25);
        vendingMachine.insertMoney(2.50);

        assertEquals(3.75, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void insertMoneyAcceptsZero() throws VendingMachineException {
        vendingMachine.insertMoney(0.0);

        assertEquals(0.0, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void insertMoneyRejectsNegativeAmountWithoutChangingBalance() throws VendingMachineException {
        vendingMachine.insertMoney(2.00);

        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> vendingMachine.insertMoney(-0.01));

        assertEquals("Invalid amount.  Amount must be >= 0", exception.getMessage());
        assertEquals(2.00, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void getBalanceReturnsBalanceWithoutChangingIt() throws VendingMachineException {
        vendingMachine.insertMoney(3.25);

        assertEquals(3.25, vendingMachine.getBalance(), 0.000001);
        assertEquals(3.25, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void purchaseReturnsFalseWhenSlotIsEmptyAndPreservesBalance()
            throws VendingMachineException {
        vendingMachine.insertMoney(5.00);

        assertFalse(vendingMachine.makePurchase(VendingMachine.A_CODE));
        assertEquals(5.00, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void purchaseReturnsFalseWhenBalanceIsInsufficientAndPreservesItemAndBalance()
            throws VendingMachineException {
        VendingMachineItem item = new VendingMachineItem("Water", 1.00);
        vendingMachine.addItem(item, VendingMachine.A_CODE);
        vendingMachine.insertMoney(0.99);

        assertFalse(vendingMachine.makePurchase(VendingMachine.A_CODE));
        assertSame(item, vendingMachine.getItem(VendingMachine.A_CODE));
        assertEquals(0.99, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void purchaseSucceedsWhenBalanceExactlyEqualsPrice() throws VendingMachineException {
        VendingMachineItem item = new VendingMachineItem("Water", 1.00);
        vendingMachine.addItem(item, VendingMachine.A_CODE);
        vendingMachine.insertMoney(1.00);

        assertTrue(vendingMachine.makePurchase(VendingMachine.A_CODE));

        assertNull(vendingMachine.getItem(VendingMachine.A_CODE));
        assertEquals(0.00, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void purchaseDeductsPriceAndEmptiesSlotWhenEnoughMoneyIsInserted()
            throws VendingMachineException {
        VendingMachineItem item = new VendingMachineItem("Juice", 1.25);
        vendingMachine.addItem(item, VendingMachine.D_CODE);
        vendingMachine.insertMoney(2.00);

        assertTrue(vendingMachine.makePurchase(VendingMachine.D_CODE));

        assertNull(vendingMachine.getItem(VendingMachine.D_CODE));
        assertEquals(0.75, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void purchaseSucceedsForFreeItemWithZeroBalance() throws VendingMachineException {
        VendingMachineItem freeItem = new VendingMachineItem("Sample", 0.00);
        vendingMachine.addItem(freeItem, VendingMachine.C_CODE);

        assertTrue(vendingMachine.makePurchase(VendingMachine.C_CODE));

        assertNull(vendingMachine.getItem(VendingMachine.C_CODE));
        assertEquals(0.00, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void returnChangeReturnsBalanceAndResetsItToZero() throws VendingMachineException {
        vendingMachine.insertMoney(3.50);

        double change = vendingMachine.returnChange();

        assertEquals(3.50, change, 0.000001);
        assertEquals(0.00, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void returnChangeOnZeroBalanceReturnsZeroAndKeepsBalanceAtZero() {
        assertEquals(0.00, vendingMachine.returnChange(), 0.000001);
        assertEquals(0.00, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void returnChangeCanBeCalledRepeatedly() throws VendingMachineException {
        vendingMachine.insertMoney(2.00);

        assertEquals(2.00, vendingMachine.returnChange(), 0.000001);
        assertEquals(0.00, vendingMachine.returnChange(), 0.000001);
        assertEquals(0.00, vendingMachine.getBalance(), 0.000001);
    }
}