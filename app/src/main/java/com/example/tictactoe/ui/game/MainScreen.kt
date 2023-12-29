package com.example.tictactoe.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoe.R
import com.example.tictactoe.ui.theme.TicTacToeTheme

private const val MAXCHAR = 10

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onStartGameButtonClicked: () -> Unit,
    gameViewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = gameUiState.player1,
            label = { Text(text = stringResource(id = R.string.label_input_player_one)) },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.placeholder_input_player),
                    style = MaterialTheme.typography.bodyMedium
                ) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_player), contentDescription = "Icon of a player")},
            onValueChange = {  if (it.length <= MAXCHAR) gameViewModel.updatePlayerName(Pair(1, it)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
        )

        OutlinedTextField(
            value = gameUiState.player2,
            label = { Text(text = stringResource(id = R.string.label_input_player_two)) },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.placeholder_input_player),
                    style = MaterialTheme.typography.bodyMedium
                    ) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_player), contentDescription = "Icon of a player")},
            onValueChange = { if (it.length <= 10) gameViewModel.updatePlayerName(Pair(2, it)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                .padding(bottom = dimensionResource(id = R.dimen.padding_large))
        )

        Button(
            onClick = { onStartGameButtonClicked() },
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(id = R.string.button_start_game),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    TicTacToeTheme {
        MainScreen( onStartGameButtonClicked = {})
    }
}