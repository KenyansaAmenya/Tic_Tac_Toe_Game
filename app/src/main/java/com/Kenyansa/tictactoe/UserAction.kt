package com.Kenyansa.tictactoe

sealed class UserAction {
    object playAgainButtonClicked: UserAction()
    data class BoardTapped(val cellNo: Int): UserAction()
}