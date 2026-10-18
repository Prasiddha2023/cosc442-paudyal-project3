# JoshHack Coverage - Inventory.java

## Coverage
| Stage | Statement/Line | Branch |
| --- | --- | --- |
| Baseline (no tests ran Inventory.java) | 0% | 0% |
| Final | 100% | 100% |

## Tests Added
I added InventoryTest.java with 13 JUnit 5 tests for Inventory.java:
- Constructor: creates an inventory of the given size with empty slots.
- add: adds to the first empty slot, goes to the next slot when the first is taken, and does nothing when the inventory is full.
- remove: clears the slot of an item that is present (in the first and a later slot) and changes nothing when the item isn't present.
- isFull: false when empty, false when partly filled, true when all slots are filled, and false again after an item is removed.
- contains: true when the item is present, false when it isn't.

## Remaining Uncovered Behavior
Nothing in Inventory.java is uncovered. Two behaviors I noticed but did not test because they aren't specified in the code: add() on a full inventory silently does nothing (no exception), and contains(null) returns true whenever any slot is empty.