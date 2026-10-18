# AI Test Review

The AI tests were pretty good for a first try. They compiled after I deleted the package and import lines VS Code added, and they checked the exact exception messages and made sure the balance and slots didn't change after an error. They also tested an exact-balance purchase and a free item. The bad part was that some tests repeated each other, like the getters test and the getBalance test, and the invalid slot codes were separate tests instead of one parameterized test.

My Task 1 suite got 100% statement and branch coverage. The AI suite got about 95% on its first run. My tests used parameterized boundary cases (1.49, 1.50, 1.51), and the AI's tests were better at checking messages and state.

The coverage prompt helped. I told the AI which lines and branches weren't covered, it added some tests, and coverage went up to 100%.

The AI also assumed things the Javadocs don't say. It wrote tests saying null and empty item names are allowed, but nothing in the Javadocs says that. I caught it by checking each test against the Javadocs. AI is good for getting a fast first draft, but you still have to check what it assumes and the tests it repeats, and a high coverage number doesn't mean the tests are good.