package game;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryTest {

    private Inventory inventory;

    private Item newItem() {
        return new Item('x', Color.WHITE, "Test", null);
    }

    @BeforeEach
    void setUp() {
        inventory = new Inventory(2);
    }

    @Test
    void constructor_createsEmptySlotsOfGivenSize() {
        assertEquals(2, inventory.getItems().length);
        assertNull(inventory.get(0));
        assertNull(inventory.get(1));
    }

    @Test
    void add_emptyInventory_putsItemInFirstSlot() {
        Item a = newItem();
        inventory.add(a);
        assertSame(a, inventory.get(0));
        assertNull(inventory.get(1));
    }

    @Test
    void add_firstSlotTaken_putsItemInNextSlot() {
        Item a = newItem();
        Item b = newItem();
        inventory.add(a);
        inventory.add(b);
        assertSame(a, inventory.get(0));
        assertSame(b, inventory.get(1));
    }

    @Test
    void add_whenFull_itemIsNotAdded() {
        Item a = newItem();
        Item b = newItem();
        Item c = newItem();
        inventory.add(a);
        inventory.add(b);
        inventory.add(c);
        assertSame(a, inventory.get(0));
        assertSame(b, inventory.get(1));
        assertFalse(inventory.contains(c));
    }

    @Test
    void remove_existingItem_clearsItsSlot() {
        Item a = newItem();
        inventory.add(a);
        inventory.remove(a);
        assertNull(inventory.get(0));
        assertFalse(inventory.contains(a));
    }

    @Test
    void remove_itemInLaterSlot_clearsOnlyThatSlot() {
        Item a = newItem();
        Item b = newItem();
        inventory.add(a);
        inventory.add(b);
        inventory.remove(b);
        assertSame(a, inventory.get(0));
        assertNull(inventory.get(1));
    }

    @Test
    void remove_itemNotPresent_changesNothing() {
        Item a = newItem();
        Item other = newItem();
        inventory.add(a);
        inventory.remove(other);
        assertSame(a, inventory.get(0));
    }

    @Test
    void isFull_emptyInventory_returnsFalse() {
        assertFalse(inventory.isFull());
    }

    @Test
    void isFull_partiallyFilled_returnsFalse() {
        inventory.add(newItem());
        assertFalse(inventory.isFull());
    }

    @Test
    void isFull_allSlotsFilled_returnsTrue() {
        inventory.add(newItem());
        inventory.add(newItem());
        assertTrue(inventory.isFull());
    }

    @Test
    void isFull_afterRemove_returnsFalse() {
        Item a = newItem();
        inventory.add(a);
        inventory.add(newItem());
        inventory.remove(a);
        assertFalse(inventory.isFull());
    }

    @Test
    void contains_itemPresent_returnsTrue() {
        Item a = newItem();
        inventory.add(a);
        assertTrue(inventory.contains(a));
    }

    @Test
    void contains_itemAbsent_returnsFalse() {
        inventory.add(newItem());
        assertFalse(inventory.contains(newItem()));
    }
}