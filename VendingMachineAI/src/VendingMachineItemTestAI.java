package VendingMachineAI.src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineItemTestAI {

    @Test
    void constructorStoresNameAndPrice() {
        VenMachItem item = new VenMachItem("Water", 1.25);

        assertEquals("Water", item.getName());
        assertEquals(1.25, item.getPrice(), 0.000001);
    }

    @Test
    void constructorAcceptsZeroPrice() {
        VenMachItem item = new VenMachItem("Free sample", 0.00);

        assertEquals("Free sample", item.getName());
        assertEquals(0.00, item.getPrice(), 0.000001);
    }

    @Test
    void constructorAcceptsVerySmallNonnegativePrice() {
        VenMachItem item = new VenMachItem("Sample", 0.001);

        assertEquals("Sample", item.getName());
        assertEquals(0.001, item.getPrice(), 0.000001);
    }

    @Test
    void constructorRejectsNegativePrice() {
        VenMachException exception = assertThrows(
                VenMachException.class,
                () -> new VenMachItem("Water", -0.01));

        assertEquals("Price cannot be less than zero", exception.getMessage());
    }

    @Test
    void constructorRejectsSmallestNegativeDouble() {
        VenMachException exception = assertThrows(
                VenMachException.class,
                () -> new VenMachItem("Water", -Double.MIN_VALUE));

        assertEquals("Price cannot be less than zero", exception.getMessage());
    }

    @Test
    void gettersReturnTheValuesProvidedAtConstruction() {
        String name = "Chocolate";
        double price = 2.75;
        VenMachItem item = new VenMachItem(name, price);

        assertSame(name, item.getName());
        assertEquals(price, item.getPrice(), 0.000001);
    }

    @Test
    void constructorAcceptsEmptyName() {
        VenMachItem item = new VenMachItem("", 1.00);

        assertEquals("", item.getName());
        assertEquals(1.00, item.getPrice(), 0.000001);
    }

    @Test
    void constructorAcceptsNullName() {
        VenMachItem item = new VenMachItem(null, 1.00);

        assertNull(item.getName());
        assertEquals(1.00, item.getPrice(), 0.000001);
    }

    @Test
    void constructorAcceptsLargestFiniteDoublePrice() {
        VenMachItem item = new VenMachItem("Expensive item", Double.MAX_VALUE);

        assertEquals("Expensive item", item.getName());
        assertEquals(Double.MAX_VALUE, item.getPrice(), 0.0);
    }

    @Test
    void constructorAcceptsPositiveInfinity() {
        VenMachItem item = new VenMachItem("Expensive item", Double.POSITIVE_INFINITY);

        assertEquals(Double.POSITIVE_INFINITY, item.getPrice());
    }

    @Test
    void constructorAcceptsNaN() {
        VenMachItem item = new VenMachItem("Unusual item", Double.NaN);

        assertTrue(Double.isNaN(item.getPrice()));
    }
}