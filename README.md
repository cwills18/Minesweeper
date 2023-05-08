## Minesweeper in Java

This is a recreation of the 90s PC game 'Minesweeper', played in the terminal. For this project, I used Java OOP and implemented TDD by creating a suite of JUnit5 tests for methods required for the application. Features of the game include:
- Java Scanner util for getting player input
- Method overriding for players to enter their custom grid size using either 1 number (for a square) or 2 numbers (for a rectangle)
- Players can customise the difficulty by choosing how many bombs are placed into the grid
- After each square selection, players can choose to either flag a square or select a new one
- When an empty square is selected, cells will cascade to the outermost positive values in that block

Challenges of this project:
- Due to so many of the methods in the game relying on the random placement of mines, I haven't yet developed testing for methods that rely on random data
- I decided to use a new IDE for this project (IntelliJ IDEA), so had to tackle some configuration issues with Maven and Junit5 testing in this IDE. 
- Made a few silly mistakes with Github pushes and pulls, and had to recreate the project from back-ups on about 3 occasions.
