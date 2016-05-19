# The Othello Game

## About
The Othello a.k.a. Reversi was implemented in Java as a school project. 


## Installation
Clone the repository and in the root directory provide ant compile command.

```
git clone < link>

ant compile
ant run
```

##Run the game
In root depository provide:

```
ant run
```

or in the **dest-client** directory is generated .jar file which you can run (on UNIX systems) as:

```
java -jar othello-client.jar
```

##Rules
[General rules](https://en.wikipedia.org/wiki/Reversi) of the game.

The application supports:
- option choose the board size (6x6, 8x8, 10x10, 12x12)
- save and load a game (game is saved into **examples** directory)
- undo operation
- choose an oponnent (human or computer)
- frozing fields (frozen fields cannot be turned), in settings it's needed to set up:
  - Fields (%) - maximum fields (in percents) which can be frozen
  - Time to change (sec) - random number of fields will freeze, when the time's up
  - Froze time (sec) - fields will be made unfrozen, when the time's up, however, after the current turn



