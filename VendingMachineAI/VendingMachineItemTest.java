package VendingMachineAI;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineItemTest {

    @Test
    void constructorStoresNameAndPrice() {
        VendingMachineItem item = new VendingMachineItem("Water", 1.25);

        assertEquals("Water", item.getName());
        assertEquals(1.25, item.getPrice(), 0.000001);
    }

    @Test
    void constructorAcceptsZeroPrice() {
        VendingMachineItem item = new VendingMachineItem("Free sample", 0.00);

        assertEquals("Free sample", item.getName());
        assertEquals(0.00, item.getPrice(), 0.000001);
    }

    @Test
    void constructorAcceptsVerySmallNonnegativePrice() {
        VendingMachineItem item = new VendingMachineItem("Sample", 0.001);

        assertEquals("Sample", item.getName());
        assertEquals(0.001, item.getPrice(), 0.000001);
    }

    @Test
    void constructorRejectsNegativePrice() {
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> new VendingMachineItem("Water", -0.01));

        assertEquals("Price cannot be less than zero", exception.getMessage());
    }

    @Test
    void gettersReturnTheValuesProvidedAtConstruction() {
        String name = "Chocolate";
        double price = 2.75;
        VendingMachineItem item = new VendingMachineItem(name, price);

        assertSame(name, item.getName());
        assertEquals(price, item.getPrice(), 0.000001);
    }

    @Test
    void constructorAcceptsEmptyNameBecauseOnlyPriceIsValidated() {
        VendingMachineItem item = new VendingMachineItem("", 1.00);

        assertEquals("", item.getName());
        assertEquals(1.00, item.getPrice(), 0.000001);
    }

    @Test
    void constructorAcceptsNullNameBecauseOnlyPriceIsValidated() {
        VendingMachineItem item = new VendingMachineItem(null, 1.00);

        assertNull(item.getName());
        assertEquals(1.00, item.getPrice(), 0.000001);
    }
}