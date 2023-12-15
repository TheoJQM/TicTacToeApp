package com.example.tictactoe.data

import kotlin.math.abs

// Every state possible for the Tic-Tac-Toe game
enum class State {
    GAMENOTFINISHED,
    DRAW,
    XWINS,
    OWINS,
    IMPOSSIBLE
}

class TicTacToe {
    private val gridSize = 3
    private val nbBoxes = gridSize * gridSize
    private var state = State.GAMENOTFINISHED
    private val grid = mutableListOf(
        mutableListOf(' ', ' ', ' '),
        mutableListOf(' ', ' ', ' '),
        mutableListOf(' ', ' ', ' ')
    )
    private var symbol = 'X'

    init {
        resetGame()
    }

    /**
     * Fill the `grid` variable with `_`
     */
    private fun loadGameGrid() {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                grid[i][j] = '_'
            }
        }
    }

    /**
     * Reset the game : empty the grid and redefined the state of the game to GAMENOTFINISHED
     */
    fun resetGame() {
        loadGameGrid()
        state = State.GAMENOTFINISHED
    }


    /**
     * Check if the cell chosen by the user is empty or not
     * @param x : The chosen line
     * @param y : The chosen column
     *
     * @return True if the cell is empty, false if not
     */
    fun emptyCell(x: Int, y: Int): Boolean {
        return grid[x][y] == '_'
    }

    /**
     * Update the grid with the new symbol (`X` or `O`)
     * @param x : The chosen line
     * @param y : The chosen column
     */
    fun updateGrid(x: Int, y: Int) {
        grid[x][y] = symbol
        symbol = if (symbol == 'X') 'O' else 'X'
    }

    /**
     * Check the current state of the grid
     * @return the state of the game, either :
     * "" -> The game is not finished
     * "Draw" -> The game is finished and it's a draw
     * "Win" -> The game is finished and one of the player has won
     */
    fun checkState(): String {
        val (xCount, oCount) = countXAndO()
        val diagonal = checkDiagonal()
        val vertical = checkVertical()
        val horizontal = checkHorizontal()

        var result = ""

        when {
            // Every conditions that make the game impossible
            diagonal.first + vertical.first + horizontal.first > 1 ||
                    abs(xCount - oCount) > 1 ->  state = State.IMPOSSIBLE

            // Conditions for the player O to win
            diagonal == Pair(1, 'O') || vertical == Pair(1, 'O') ||
                    horizontal == Pair(1, 'O') -> {
                state = State.OWINS
                result = "Win"
            }

            // Conditions for the player X to win
            diagonal == Pair(1, 'X') || vertical == Pair(1, 'X') ||
                    horizontal == Pair(1, 'X') -> {
                        state =  State.XWINS
                        result = "Win"
                    }

            // Condition for the game to be a draw
            xCount + oCount == nbBoxes -> {
                state = State.DRAW
                result = "Draw"
            }

            // By default, the state is on GAMENOTFINISHED
        }
        return result

    }

    /**
     * Count the number of X and O in the grid
     *
     * @return Pair<Int, Int> :
     * The first element is the number of X
     * The second element is the number of O
     */
    private fun countXAndO(): Pair<Int, Int> {
        var x = 0
        var o = 0

        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                when(grid[i][j]) {
                    'X' -> x++
                    'O' -> o++
                }
            }
        }

        return Pair(x,o)
    }

    /**
     * Check every column of the grid to see if there is a winner
     *
     * @return Pair<Int, Char> :
     * The first element is the number of vertical winning lines( not suppose to be more than 1)
     * The second element is the winning character (X or O)
     */
    private fun checkVertical(): Pair<Int, Char> {
        var line = Pair(0, ' ')
        for (j in 0 until gridSize) {
            val i = 0
            when  {
                grid[i][j] == 'X' && grid[i + 1][j] == 'X' && grid[i + 2][j] == 'X'-> {
                    line = line.copy(first = line.first +1, second = 'X')
                }
                grid[i][j] == 'O' && grid[i + 1][j] == 'O' && grid[i + 2][j] == 'O'-> {
                    line = line.copy(first = line.first +1, second = 'O')
                }
            }
        }
        return line
    }

    /**
     * Check every line of the grid to see if there is a winner
     *
     * @return Pair<Int, Char> :
     * The first element is the number of horizontal winning lines( not suppose to be more than 1)
     * The second element is the winning character (X or O)
     */
    private fun checkHorizontal(): Pair<Int, Char> {
        var line = Pair(0, ' ')
        for (i in 0 until gridSize) {
            val j = 0
            when  {
                grid[i][j] == 'X' && grid[i][j + 1] == 'X' && grid[i][j + 2] == 'X'-> {
                    line = line.copy(first = line.first +1, second = 'X')
                }
                grid[i][j] == 'O' && grid[i][j + 1] == 'O' && grid[i][j + 2] == 'O'-> {
                    line = line.copy(first = line.first +1, second = 'O')
                }
            }
        }
        return line
    }

    /**
     * Check every diagonal ( always 2 diagonals)  of the grid to see if there is a winner
     *
     * @return Pair<Int, Char> :
     * The first element is the number of diagonal winning lines( not suppose to be more than 1)
     * The second element is the winning character (X or O)
     */
    private fun checkDiagonal(): Pair<Int, Char> {
        var line = Pair(0, ' ')
        val i = 0
        val listPossibility = listOf('X', 'O')
        listPossibility.forEach{ char ->
            when {
                grid[i][i] == char && grid[i + 1][i + 1] == char && grid[i + 2][i + 2] == char ->
                    line = line.copy(first = line.first +1, second = char)

                grid[i][i + 2] == char && grid[i + 1][i + 1] == char && grid[i + 2][i] == char ->
                    line = line.copy(first = line.first +1, second = char)

            }
        }
        return line
    }
}