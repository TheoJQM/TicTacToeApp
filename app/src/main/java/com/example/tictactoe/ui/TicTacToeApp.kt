package com.example.tictactoe.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tictactoe.R
import com.example.tictactoe.ui.game.GameScreen
import com.example.tictactoe.ui.game.GameViewModel
import com.example.tictactoe.ui.game.MainScreen

enum class TicTacToeScreen {
    Main,
    Game
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TicTacToeApp(modifier: Modifier = Modifier) {
    val gameViewModel: GameViewModel = viewModel()
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TicTacToeTopBar()
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = TicTacToeScreen.Main.name,
            modifier = modifier.padding(it)
        ) {
            composable(TicTacToeScreen.Main.name) {
                MainScreen(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    gameViewModel = gameViewModel,
                    onStartGameButtonClicked = {
                        if (gameViewModel.lunchGame()) navController.navigate(TicTacToeScreen.Game.name)
                    }
                    )
            }

            composable(TicTacToeScreen.Game.name) {
                GameScreen(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.padding_extra_large)),
                    gameViewModel = gameViewModel
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeTopBar(modifier: Modifier = Modifier) {
    Surface(shadowElevation = 8.dp) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.title),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            modifier = modifier
        )
    }
}