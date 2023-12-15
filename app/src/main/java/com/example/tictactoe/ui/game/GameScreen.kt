package com.example.tictactoe.ui.game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoe.R
import com.example.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {

    val gameUiState = gameViewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayerInformation(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
            player1 = gameUiState.value.player1,
            player2 = gameUiState.value.player2
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_extra_large)))

        GameContent(
            modifier = Modifier
                .fillMaxWidth(),
            gameViewModel
        )
    }
}

@Composable
fun PlayerInformation(
    modifier: Modifier = Modifier,
    player1: String,
    player2: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

    ) {
        Text(
            text = String.format(stringResource(id = R.string.display_player_one_name), player1),
            style = MaterialTheme.typography.displayMedium
        )

        Text(
            text = stringResource(id = R.string.versus),
            style = MaterialTheme.typography.displayMedium
        )

        Text(
            text = String.format(stringResource(id = R.string.display_player_two_name), player2),
            style = MaterialTheme.typography.displayMedium
        )

    }
}

@Composable
fun GameContent(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel
) {
    val gameUiState = gameViewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = String.format(stringResource(id = R.string.choose_cell), gameUiState.value.currPlayer),
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

        GameGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
            gameViewModel = gameViewModel
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))


        if (gameUiState.value.isGameOver) {
            if (gameUiState.value.result == "Win") {
                Text(
                    text = String.format(stringResource(id = R.string.player_won), gameUiState.value.currPlayer),
                    style = MaterialTheme.typography.displayMedium
                )
            } else {
                Text(
                    text = stringResource(id = R.string.draw),
                    style = MaterialTheme.typography.displayMedium
                )
            }

            Spacer(modifier = Modifier.weight(1F))

            Button(
                onClick = { gameViewModel.resetGame() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                    .padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    text = stringResource(id = R.string.restart),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
            }
        }


    }
}

@Composable
fun GameGrid(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel
    ) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)

    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(16.dp),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                items(9) {index ->
                    Card(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.game_cell_size))
                            .clickable { gameViewModel.checkMove(index) },
                        shape = MaterialTheme.shapes.small,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        if (gameViewModel.isFilled(index)) {
                            Text(
                                text = gameViewModel.getSymbol(index),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentHeight(Alignment.CenterVertically),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.displayLarge

                            )
                        }

                    }
                }
            }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameScreenPreview() {
    TicTacToeTheme {
        GameScreen()
    }
}