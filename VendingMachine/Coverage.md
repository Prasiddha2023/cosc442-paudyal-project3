# Coverage Report - VendingMachine

## Initial Coverage
| Source File | Statement/Line | Branch |
| --- | --- | --- |
| VendingMachine.java | 100% | 100% |
| VendingMachineItem.java | 100% | 100% |

## Final Coverage
| Source File | Statement/Line | Branch |
| --- | --- | --- |
| VendingMachine.java | 100% | 100% |
| VendingMachineItem.java | 100% | 100% |

## Tests Added
The Project 2 suite already reached 100% statement and branch coverage, so no additional tests were needed to improve coverage.

## Condition-Coverage Analysis
Condition-Coverage Check
Select one compound Boolean decision in the VendingMachine implementation (for example, a decision in makePurchase()). Identify the individual Boolean conditions that make up the decision. Briefly describe which of your tests cause each condition to evaluate true and false. This analysis is manual; the goal is to connect the coverage ideas from class with the tests you wrote.

Answer: if ((item != null) && (this.balance >= item.getPrice())) - (compound condition)
Individual Booleans are item != null and this.balance >= item.getPrice()

For item!=null-

True: testMakePurchase_moreThanPrice_succeedsAndSubtractsPrice, 
testMakePurchaseExactBalanceSucceeds, testMakePurchase_insufficientBalance_failsAndKeepsItem,
testMakePurchase_balanceBoundary

False: testMakePurchase_emptySlot_returnsFalse

For this.balance >= item.getPrice()- 
True: testMakePurchase_moreThanPrice_succeedsAndSubtractsPrice (2.00 vs 1.50), 
testMakePurchaseExactBalanceSucceeds (1.50 vs 1.50), 
and the 1.5, 1.51, and 3.0 rows of testMakePurchase_balanceBoundary.

False: testMakePurchase_insufficientBalance_failsAndKeepsItem (1.49 vs 1.50) and the 0.0, 1.0, and 1.49 rows of testMakePurchase_balanceBoundary.


## Test-Sensitivity Experiment
Injected fault: in makePurchase(), changed `this.balance >= item.getPrice()` to `this.balance > item.getPrice()`.

Result: the fault was detected. testMakePurchaseExactBalanceSucceeds and the 1.5 row of testMakePurchase_balanceBoundary failed, because when the balance exactly equals the price the faulty code returns false while the tests expect true. The fault was then removed, all tests passed again, and a final commit was made.