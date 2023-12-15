package com.example.tictactoe.ui.game

import androidx.lifecycle.ViewModel
import com.example.tictactoe.data.TicTacToe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GameUiState(
    val player1: String = "",
    val player2: String = "",
    val currPlayer: String = "",
    val symbol: String = "X",
    val result: String = "",
    val isGameOver: Boolean = false
)

class GameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    var uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val game = TicTacToe()

    private var currPlayer: String = " "
    private val listCells = mutableListOf<Pair<Int, String>>()

    init {
        resetGame()
    }

    /**
     * Launch the game only if the names of the two players are provided
     */
    fun launchGame(): Boolean {
        return if (_uiState.value.player1 != "" && _uiState.value.player2 != "") {
            resetGame()
            true
        } else {
            false
        }
    }

    /**
     * Get the symbol of a specific cell
     * @param index : An int that identify the cell(0 to 8)
     *
     * @return The symbol of the cell (O or X)
     */
    fun getSymbol(index: Int): String {
        return listCells.find{ it.first == index }!!.second
    }

    /**
     * Check if the player can click on a specific cell
     * @param index : An Int used to identify a specific cell(0 to 8)
     */
    fun checkMove(index: Int) {
        val result: String
        // The player can't click on a cell if the game is finished
        if (!_uiState.value.isGameOver) {
            when(index) {
                in 0..2 -> {
                    if (game.emptyCell(0, index)) {
                        assignSymbol(index)
                        game.updateGrid(0, index)
                        result = game.checkState()
                        checkGameFinished(result)
                    }
                }
                in 3..5 -> {
                    if (game.emptyCell(1, index - 3)) {
                        assignSymbol(index)
                        game.updateGrid(1, index - 3)
                        result = game.checkState()
                        checkGameFinished(result)
                    }
                }
                in 6..8 -> {
                    if (game.emptyCell(2, index - 6)) {
                        assignSymbol(index)
                        game.updateGrid(2, index - 6)
                        result = game.checkState()
                        checkGameFinished(result)
                    }
                }
            }
        }
    }

    /**
     * Assign the symbol to the clicked cell
     * @param index : An Int used to identify the clicked cell
     */
    private fun assignSymbol(index: Int) {
        val cell = listCells.find{it.first == index}
        val pair = cell!!.copy(second = _uiState.value.symbol)
        listCells[listCells.indexOf(cell)] = pair
    }

    /**
     * Check the state of the game
     * @param result : The state of the game, either :
     * "" -> The game is not finished, we update the current player and changed the symbol
     * "Draw" -> The game is finished and it's a draw
     * "Win" -> The game is finished and one of the player has won
     */
    private fun checkGameFinished(result: String) {
        when (result) {
            "" -> {
                updateCurrentPlayer()
                updateSymbol()
            }
            "Draw", "Win" -> _uiState.update { currState -> currState.copy(result = result, isGameOver = true) }
        }
    }

    /**
     * Update the val `currPlayer` with the name of the current player
     */
    private fun updateCurrentPlayer() {
        when (currPlayer) {
            "", _uiState.value.player2 -> {
                _uiState.update { currState -> currState.copy(currPlayer = _uiState.value.player1) }
                currPlayer = _uiState.value.player1
            }
            _uiState.value.player1 -> {
                _uiState.update { currState -> currState.copy(currPlayer = _uiState.value.player2) }
                currPlayer = _uiState.value.player2
            }
        }
    }

    /**
     * Update the _uiState val `symbol` (either O or X)
     */
    private fun updateSymbol() {
        when (_uiState.value.symbol) {
            "X" -> _uiState.update { currState -> currState.copy(symbol = "O") }
            "O" -> _uiState.update { currState -> currState.copy(symbol = "X") }
        }
    }

    /**
     * Reset of the values of the _uiState and the variables of the class
     * Then update the value of the currPlayer
     * Finally, load the list `listCells`
     */
    fun resetGame() {
        listCells.clear()
        currPlayer = ""
        game.resetGame()
        _uiState.update { currState ->
            currState.copy(
                symbol = "X",
                result = "",
                isGameOver= false,
                currPlayer = ""
            )
        }
        updateCurrentPlayer()
        loadFilledCells()
    }

    /**
     * Update the name of the player
     * @param player : Pair of :
     * An Int (1 or 2) to identify the player(player1 or player2)
     * A String that represents the name of the player
     */
    fun updatePlayerName(player: Pair<Int, String>) {
        if (player.first == 1) {
            _uiState.update { currState ->currState.copy( player1 = player.second)}
        } else {
            _uiState.update { currState ->currState.copy( player2 = player.second)}
        }
    }

    /**
     * Load the list `listCells` with 9 Pairs of :
     * An Int (0 to 8)that represents the cell ( 0 = top left, 8 = bottom right)
     * A empty string that will be used to show a symbol (O or X)
     */
    private fun loadFilledCells() {
        for (i in 0..8) {
            listCells.add(Pair(i, ""))
        }
    }
}