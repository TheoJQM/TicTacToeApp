package com.example.tictactoe.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tictactoe.data.TicTacToe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GameUiState(
    val player1: String = "Alpha",
    val player2: String = "Gamma",
    val currPlayer: String = "Alpha",
    val symbol: String = "X",
    val result: String = "",
    val isGameOver: Boolean = false
)

class GameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    var uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val game = TicTacToe()

    private var currPlayer: String = ""
    private val filledCells = mutableListOf<Pair<Int, String>>()

    init {
        resetGame()
    }

    fun updatePlayerName(player: Pair<Int, String>) {
        if (player.first == 1) {
            _uiState.update { currState ->currState.copy( player1 = player.second)}
        } else {
            _uiState.update { currState ->currState.copy( player2 = player.second)}
        }
    }

    fun isFilled(index: Int): Boolean {
        return filledCells.contains(Pair(index, "X") ) || filledCells.contains(Pair(index, "O") )
    }

    fun getSymbol(index: Int): String {
        return filledCells.find{ it.first == index }!!.second
    }

    fun checkNames(): Boolean {
        return !(_uiState.value.player1 != "" && _uiState.value.player2 != "")
    }

    fun checkMove(index: Int) {
        val result: String
        if (!_uiState.value.isGameOver) {
            when(index) {
                in 0..2 -> {
                    if (game.emptyCell(0, index)) {
                        filledCells.add(Pair(index, _uiState.value.symbol))
                        result = game.updateGrid(0, index)
                        updateGame(result)
                    }
                }
                in 3..5 -> {
                    if (game.emptyCell(1, index - 3)) {
                        filledCells.add(Pair(index, _uiState.value.symbol))
                        result = game.updateGrid(1, index - 3)
                        updateGame(result)
                    }
                }
                in 6..8 -> {
                    if (game.emptyCell(2, index - 6)) {
                        filledCells.add(Pair(index, _uiState.value.symbol))
                        result = game.updateGrid(2, index - 6)
                        updateGame(result)
                    }
                }
            }
        }
    }

    private fun updateGame(result: String) {
        Log.e("list", result)
        when (result) {
            "" -> {
                updateCurrentPlayer()
                updateSymbol()
            }
            "Draw", "Win" -> _uiState.update { currState -> currState.copy(result = result, isGameOver = true) }
        }
    }

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

    private fun updateSymbol() {
        when (_uiState.value.symbol) {
            "X" -> _uiState.update { currState -> currState.copy(symbol = "O") }
            "O" -> _uiState.update { currState -> currState.copy(symbol = "X") }
        }
    }

    private fun resetGame() {
        filledCells.clear()
        currPlayer = _uiState.value.player1
    }
}