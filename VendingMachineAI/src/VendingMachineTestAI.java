package VendingMachineAI.src;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTestAI {
    private VenMach vendingMachine;

    @BeforeEach
    void setUp() {
        vendingMachine = new VenMach();
    }

    @Test
    void constructorStartsWithZeroBalanceAndEmptySlots() throws VenMachException {
        assertEquals(0.0, vendingMachine.getBalance(), 0.000001);
        assertNull(vendingMachine.getItem(VenMach.A_CODE));
        assertNull(vendingMachine.getItem(VenMach.B_CODE));
        assertNull(vendingMachine.getItem(VenMach.C_CODE));
        assertNull(vendingMachine.getItem(VenMach.D_CODE));
    }

    @Test
    void addItemPlacesItemInEachValidSlot() throws VenMachException {
        VenMachItem itemA = new VenMachItem("Water", 1.25);
        VenMachItem itemB = new VenMachItem("Chips", 1.50);
        VenMachItem itemC = new VenMachItem("Juice", 2.00);
        VenMachItem itemD = new VenMachItem("Candy", 0.75);

        vendingMachine.addItem(itemA, VenMach.A_CODE);
        vendingMachine.addItem(itemB, VenMach.B_CODE);
        vendingMachine.addItem(itemC, VenMach.C_CODE);
        vendingMachine.addItem(itemD, VenMach.D_CODE);

        assertSame(itemA, vendingMachine.getItem(VenMach.A_CODE));
        assertSame(itemB, vendingMachine.getItem(VenMach.B_CODE));
        assertSame(itemC, vendingMachine.getItem(VenMach.C_CODE));
        assertSame(itemD, vendingMachine.getItem(VenMach.D_CODE));
    }

    @Test
    void addItemRejectsAnOccupiedSlot() throws VenMachException {
        VenMachItem original = new VenMachItem("Water", 1.00);
        VenMachItem replacement = new VenMachItem("Soda", 1.50);
        vendingMachine.addItem(original, VenMach.A_CODE);

        VenMachException exception = assertThrows(
                VenMachException.class,
                () -> vendingMachine.addItem(replacement, VenMach.A_CODE));

        assertEquals("Slot A already occupied", exception.getMessage());
        assertSame(original, vendingMachine.getItem(VenMach.A_CODE));
    }

    @Test
    void addItemRejectsAnInvalidSlotCode() {
        VenMachItem item = new VenMachItem("Water", 1.00);

        VenMachException exception = assertThrows(
                VenMachException.class,
                () -> vendingMachine.addItem(item, "E"));

        assertEquals("Invalid code for vending machine item", exception.getMessage());
    }

    @Test
    void getItemRejectsAnInvalidSlotCode() {
        VenMachException exception = assertThrows(
                VenMachException.class,
                () -> vendingMachine.getItem("Z"));

        assertEquals("Invalid code for vending machine item", exception.getMessage());
    }

    @Test
    void removeItemReturnsTheItemAndEmptiesItsSlot() throws VenMachException {
        VenMachItem item = new VenMachItem("Water", 1.00);
        vendingMachine.addItem(item, VenMach.B_CODE);

        VenMachItem removed = vendingMachine.removeItem(VenMach.B_CODE);

        assertSame(item, removed);
        assertNull(vendingMachine.getItem(VenMach.B_CODE));
    }

    @Test
    void removeItemRejectsAnEmptySlot() throws VenMachException {
        VenMachException exception = assertThrows(
                VenMachException.class,
                () -> vendingMachine.removeItem(VenMach.C_CODE));

        assertEquals("Slot C is empty -- cannot remove item", exception.getMessage());
        assertNull(vendingMachine.getItem(VenMach.C_CODE));
    }

    @Test
    void removeItemRejectsAnInvalidSlotCode() {
        VenMachException exception = assertThrows(
                VenMachException.class,
                () -> vendingMachine.removeItem(""));

        assertEquals("Invalid code for vending machine item", exception.getMessage());
    }

    @Test
    void insertMoneyAddsAmountToBalance() throws VenMachException {
        vendingMachine.insertMoney(1.25);
        vendingMachine.insertMoney(2.50);

        assertEquals(3.75, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void insertMoneyAcceptsZero() throws VenMachException {
        vendingMachine.insertMoney(0.0);

        assertEquals(0.0, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void insertMoneyRejectsNegativeAmountWithoutChangingBalance() throws VenMachException {
        vendingMachine.insertMoney(2.00);

        VenMachException exception = assertThrows(
                VenMachException.class,
                () -> vendingMachine.insertMoney(-0.01));

        assertEquals("Invalid amount.  Amount must be >= 0", exception.getMessage());
        assertEquals(2.00, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void getBalanceReturnsBalanceWithoutChangingIt() throws VenMachException {
        vendingMachine.insertMoney(3.25);

        assertEquals(3.25, vendingMachine.getBalance(), 0.000001);
        assertEquals(3.25, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void purchaseReturnsFalseWhenSlotIsEmptyAndPreservesBalance() throws VenMachException {
        vendingMachine.insertMoney(5.00);

        assertFalse(vendingMachine.makePurchase(VenMach.A_CODE));
        assertEquals(5.00, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void purchaseReturnsFalseWhenBalanceIsInsufficientAndPreservesItemAndBalance()
            throws VenMachException {
        VenMachItem item = new VenMachItem("Water", 1.00);
        vendingMachine.addItem(item, VenMach.A_CODE);
        vendingMachine.insertMoney(0.99);

        assertFalse(vendingMachine.makePurchase(VenMach.A_CODE));
        assertSame(item, vendingMachine.getItem(VenMach.A_CODE));
        assertEquals(0.99, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void purchaseSucceedsWhenBalanceExactlyEqualsPrice() throws VenMachException {
        VenMachItem item = new VenMachItem("Water", 1.00);
        vendingMachine.addItem(item, VenMach.A_CODE);
        vendingMachine.insertMoney(1.00);

        assertTrue(vendingMachine.makePurchase(VenMach.A_CODE));

        assertNull(vendingMachine.getItem(VenMach.A_CODE));
        assertEquals(0.00, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void purchaseDeductsPriceAndEmptiesSlotWhenEnoughMoneyIsInserted()
            throws VenMachException {
        VenMachItem item = new VenMachItem("Juice", 1.25);
        vendingMachine.addItem(item, VenMach.D_CODE);
        vendingMachine.insertMoney(2.00);

        assertTrue(vendingMachine.makePurchase(VenMach.D_CODE));

        assertNull(vendingMachine.getItem(VenMach.D_CODE));
        assertEquals(0.75, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void purchaseSucceedsForFreeItemWithZeroBalance() throws VenMachException {
        VenMachItem freeItem = new VenMachItem("Sample", 0.00);
        vendingMachine.addItem(freeItem, VenMach.C_CODE);

        assertTrue(vendingMachine.makePurchase(VenMach.C_CODE));

        assertNull(vendingMachine.getItem(VenMach.C_CODE));
        assertEquals(0.00, vendingMachine.getBalance(), 0.000001);
    }

    @Test
    void returnChangeReturnsBalanceAndResetsItToZero() throws VenMachException {
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
    void returnChangeCanBeCalledRepeatedly() throws VenMachException {
        vendingMachine.insertMoney(2.00);

        assertEquals(2.00, vendingMachine.returnChange(), 0.000001);
        assertEquals(0.00, vendingMachine.returnChange(), 0.000001);
        assertEquals(0.00, vendingMachine.getBalance(), 0.000001);
    }
}