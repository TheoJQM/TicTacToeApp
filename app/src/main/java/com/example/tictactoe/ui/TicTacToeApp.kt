package com.example.tictactoe.ui

import android.annotation.SuppressLint
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tictactoe.R
import com.example.tictactoe.ui.game.GameScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TicTacToeApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TicTacToeTopBar()
        }
    ) {
        GameScreen(contentPadding = it)
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