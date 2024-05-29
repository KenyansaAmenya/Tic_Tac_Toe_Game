package com.Kenyansa.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    var state by mutableStateOf(GameState())

    val boardItems: MutableMap<Int, GameState.BoardCellValue> = mutableMapOf(
        1 to GameState.BoardCellValue.NONE,
        2 to GameState.BoardCellValue.NONE,
        3 to GameState.BoardCellValue.NONE,
        4 to GameState.BoardCellValue.NONE,
        5 to GameState.BoardCellValue.NONE,
        6 to GameState.BoardCellValue.NONE,
        7 to GameState.BoardCellValue.NONE,
        8 to GameState.BoardCellValue.NONE,
        9 to GameState.BoardCellValue.NONE,
    )

    fun onAction(action: UserAction){
        when(action) {
            is UserAction.BoardTapped -> {
                addValueToBoard(action.cellNo)
            }
            UserAction.playAgainButtonClicked -> {
                gameReset()
            }
        }
    }

    // function to reset the game
    private fun gameReset(){
        boardItems.forEach{ (i, _) ->
            boardItems[i] = GameState.BoardCellValue.NONE
        }
        state = state.copy(
            hintText = "Player '0' turn",
            currentTurn = GameState.BoardCellValue.CIRCLE,
            victoryType = GameState.VictoryType.NONE,
            hasWon = false

        )
    }

    private fun addValueToBoard(cellNo: Int) {
       if (boardItems[cellNo] != GameState.BoardCellValue.NONE) {
           return
       }
        if (state.currentTurn == GameState.BoardCellValue.CIRCLE) {
            boardItems[cellNo] = GameState.BoardCellValue.CIRCLE

            // condition to check if there is a win or not
            if (checkForVictory(GameState.BoardCellValue.CIRCLE)) {
                state = state.copy(
                    hintText = "Player '0' Won",
                    playerCircleCount = state.playerCircleCount + 1,
                    currentTurn = GameState.BoardCellValue.NONE,
                    hasWon = true
                )
            } else if (hasBoardFull()){
                state = state.copy(
                    hintText = "Game Draw",
                    drawCount = state.drawCount + 1
                )
            } else {
                state = state.copy(
                    hintText = "Player 'X' turn",
                    currentTurn = GameState.BoardCellValue.CROSS
                )
            }

        } else if (state.currentTurn == GameState.BoardCellValue.CROSS) {
            boardItems[cellNo] = GameState.BoardCellValue.CROSS
            if (checkForVictory(GameState.BoardCellValue.CROSS)) {
                state = state.copy(
                    hintText = "Player 'X' Won",
                    playerCrossCount = state.playerCrossCount + 1,
                    currentTurn = GameState.BoardCellValue.NONE,
                    hasWon = true
                )
            } else if (hasBoardFull()){
                state = state.copy(
                    hintText = "Game Draw",
                    drawCount = state.drawCount + 1
                )
            } else {
                state = state.copy(
                    hintText = "Player 'O' turn",
                    currentTurn = GameState.BoardCellValue.CIRCLE
                )
            }

        }
    }

    private fun checkForVictory(boardValue: GameState.BoardCellValue): Boolean {
       when{
           boardItems[1] == boardValue && boardItems[2] == boardValue && boardItems[3] == boardValue -> {
               state = state.copy(victoryType = GameState.VictoryType.HORIZONTAL1)
               return true
           }
           boardItems[4] == boardValue && boardItems[5] == boardValue && boardItems[6] == boardValue -> {
               state = state.copy(victoryType = GameState.VictoryType.HORIZONTAL2)
               return true
           }
           boardItems[7] == boardValue && boardItems[8] == boardValue && boardItems[9] == boardValue -> {
               state = state.copy(victoryType = GameState.VictoryType.HORIZONTAL3)
               return true
           }
           boardItems[1] == boardValue && boardItems[4] == boardValue && boardItems[7] == boardValue -> {
               state = state.copy(victoryType = GameState.VictoryType.VERTICAL1)
               return true
           }
           boardItems[2] == boardValue && boardItems[5] == boardValue && boardItems[8] == boardValue -> {
               state = state.copy(victoryType = GameState.VictoryType.VERTICAL2)
               return true
           }
           boardItems[3] == boardValue && boardItems[6] == boardValue && boardItems[9] == boardValue -> {
               state = state.copy(victoryType = GameState.VictoryType.VERTICAL3)
               return true
           }
           boardItems[1] == boardValue && boardItems[5] == boardValue && boardItems[9] == boardValue -> {
               state = state.copy(victoryType = GameState.VictoryType.DIAGONAL1)
               return true
           }
           boardItems[3] == boardValue && boardItems[5] == boardValue && boardItems[7] == boardValue -> {
               state = state.copy(victoryType = GameState.VictoryType.DIAGONAL2)
               return true
           }
           else -> return false
       }
    }

    private fun hasBoardFull(): Boolean {
       if (boardItems.containsValue(GameState.BoardCellValue.NONE)) return false
        return true
    }
}