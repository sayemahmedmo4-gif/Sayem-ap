package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.TextRepeaterViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          TextRepeaterApp()
        }
      }
    }
  }
}

@Composable
fun TextRepeaterApp(
  viewModel: TextRepeaterViewModel = viewModel()
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val context = LocalContext.current

  // Show Toast when snackbarMessage is triggered
  LaunchedEffect(uiState.snackbarMessage) {
    uiState.snackbarMessage?.let { msg ->
      Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
      viewModel.dismissSnackbar()
    }
  }

  // Single-screen app: HomeScreen handles all text repeating, quick repeats, recents, stylizing, and WhatsApp contact
  HomeScreen(
    uiState = uiState,
    onInputChanged = { viewModel.onInputTextChanged(it) },
    onCountChanged = { viewModel.onRepetitionCountChanged(it) },
    onNewLineChanged = { viewModel.onNewLineToggle(it) },
    onRepeatClicked = { viewModel.repeatText() },
    onClearClicked = { viewModel.clearInput() },
    onToggleFavorite = { viewModel.toggleFavoriteCurrent() },
    onToggleStylizeDialog = { viewModel.toggleStylizeDialog(it) },
    onToggleSavedDialog = { viewModel.toggleSavedDialog(it) },
    onSelectStyle = { viewModel.applyStyle(it) },
    onSelectQuickRepeat = { viewModel.selectQuickRepeat(it) },
    onSelectRecentRepeat = { viewModel.selectRecentRepeat(it) },
    onShowToast = { msg ->
      Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
  )
}
