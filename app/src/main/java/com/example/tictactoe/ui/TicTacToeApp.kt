package com.example.tictactoe.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TicTacToeScreen.valueOf(
        backStackEntry?.destination?.route ?: TicTacToeScreen.Main.name
    )
    Scaffold(
        topBar = {
            TicTacToeTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
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
                        if (gameViewModel.launchGame()) navController.navigate(TicTacToeScreen.Game.name)
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
fun TicTacToeTopBar(
    currentScreen: TicTacToeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(shadowElevation = 8.dp) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name).uppercase(),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                if ( canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            },
            modifier = modifier
        )
    }
}