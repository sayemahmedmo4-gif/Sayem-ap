package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.model.QuickRepeatItem
import com.example.model.RecentRepeatItem
import com.example.model.SavedTextItem
import com.example.model.TextStyleType
import com.example.util.TextStyler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONArray
import org.json.JSONObject

data class TextRepeaterUiState(
  val inputText: String = "",
  val repetitionCount: Int = 1,
  val repeatInNewLine: Boolean = true,
  val repeatedResult: String = "",
  val currentStyle: TextStyleType = TextStyleType.ORIGINAL,
  val quickRepeats: List<QuickRepeatItem> = emptyList(),
  val recentRepeats: List<RecentRepeatItem> = emptyList(),
  val savedRepeats: List<SavedTextItem> = emptyList(),
  val showStylizeDialog: Boolean = false,
  val showSavedDialog: Boolean = false,
  val snackbarMessage: String? = null,
  val isCurrentSaved: Boolean = false
)

class TextRepeaterViewModel(application: Application) : AndroidViewModel(application) {

  private val prefs = application.getSharedPreferences("text_repeater_prefs", Context.MODE_PRIVATE)

  private val _uiState = MutableStateFlow(TextRepeaterUiState())
  val uiState: StateFlow<TextRepeaterUiState> = _uiState.asStateFlow()

  init {
    loadLocalData()
  }

  private fun loadLocalData() {
    val sampleQuickRepeats = listOf(
      QuickRepeatItem("q1", "I Love You ❤️", "Emotion", 145, true),
      QuickRepeatItem("q2", "ভালোবাসি তোমাকে 🌸", "Bangla", 100, true),
      QuickRepeatItem("q3", "Happy Birthday! 🎂🎉", "Greetings", 50, true),
      QuickRepeatItem("q4", "শুভ জন্মদিন! 🎈✨", "Bangla", 25, true),
      QuickRepeatItem("q5", "Sorry! 🙏🥺", "Apology", 100, true),
      QuickRepeatItem("q6", "I Miss You 💕", "Emotion", 75, true),
      QuickRepeatItem("q7", "Good Morning ☀️", "Daily", 50, false),
      QuickRepeatItem("q8", "কেমন আছো? 😊", "Bangla", 50, false),
      QuickRepeatItem("q9", "ধন্যবাদ! 💐", "Bangla", 30, true),
      QuickRepeatItem("q10", "Congratulations! 🏆👏", "Celebration", 145, true)
    )

    val savedRecentsJson = prefs.getString("saved_recents_json", null)
    val recentsList = if (!savedRecentsJson.isNullOrEmpty()) {
      parseRecentsJson(savedRecentsJson)
    } else {
      listOf(
        RecentRepeatItem("r1", "I Love You ❤️", 145, true, "10m ago"),
        RecentRepeatItem("r2", "ভালোবাসি তোমাকে 🌸", 100, true, "25m ago"),
        RecentRepeatItem("r3", "Happy Birthday! 🎂🎉", 50, true, "1h ago"),
        RecentRepeatItem("r4", "Sorry! 🙏🥺", 120, true, "Yesterday")
      )
    }

    _uiState.update {
      it.copy(
        repetitionCount = 1,
        quickRepeats = sampleQuickRepeats,
        recentRepeats = recentsList
      )
    }
  }

  fun onInputTextChanged(newText: String) {
    _uiState.update {
      it.copy(
        inputText = newText,
        isCurrentSaved = false
      )
    }
  }

  fun onRepetitionCountChanged(count: Int) {
    val clamped = count.coerceIn(1, 10000)
    _uiState.update { it.copy(repetitionCount = clamped) }
  }

  fun onNewLineToggle(checked: Boolean) {
    _uiState.update { it.copy(repeatInNewLine = checked) }
  }

  fun repeatText() {
    val state = _uiState.value
    if (state.inputText.isBlank()) {
      showSnackbar("Please enter some text first")
      return
    }

    val clampedCount = state.repetitionCount.coerceIn(1, 10000)
    val result = generateRepetition(
      state.inputText,
      clampedCount,
      state.repeatInNewLine,
      state.currentStyle
    )

    val newRecent = RecentRepeatItem(
      id = System.currentTimeMillis().toString(),
      text = state.inputText,
      count = clampedCount,
      inNewLine = state.repeatInNewLine,
      timestamp = "Just now"
    )

    val updatedRecents = listOf(newRecent) + state.recentRepeats.filter { it.text != state.inputText }.take(9)
    saveRecentsToStorage(updatedRecents)

    _uiState.update {
      it.copy(
        repeatedResult = result,
        recentRepeats = updatedRecents
      )
    }
  }

  fun selectQuickRepeat(item: QuickRepeatItem) {
    _uiState.update { state ->
      state.copy(
        inputText = item.text,
        repetitionCount = item.defaultCount,
        repeatInNewLine = item.inNewLine
      )
    }
    showSnackbar("Loaded '${item.text.take(16)}...' into input")
  }

  fun selectRecentRepeat(recent: RecentRepeatItem) {
    _uiState.update { state ->
      state.copy(
        inputText = recent.text,
        repetitionCount = recent.count,
        repeatInNewLine = recent.inNewLine
      )
    }
    showSnackbar("Loaded recent text into input")
  }

  fun applyStyle(style: TextStyleType) {
    _uiState.update { state ->
      val newResult = if (state.repeatedResult.isNotEmpty()) {
        generateRepetition(
          state.inputText,
          state.repetitionCount,
          state.repeatInNewLine,
          style
        )
      } else {
        ""
      }
      state.copy(
        currentStyle = style,
        repeatedResult = newResult,
        showStylizeDialog = false
      )
    }
  }

  fun clearInput() {
    _uiState.update { it.copy(inputText = "", repeatedResult = "") }
  }

  fun toggleStylizeDialog(show: Boolean) {
    _uiState.update { it.copy(showStylizeDialog = show) }
  }

  fun toggleSavedDialog(show: Boolean) {
    _uiState.update { it.copy(showSavedDialog = show) }
  }

  fun toggleFavoriteCurrent() {
    val state = _uiState.value
    if (state.inputText.isBlank()) {
      showSnackbar("Enter text to favorite")
      return
    }
    val newSavedState = !state.isCurrentSaved
    if (newSavedState) {
      val newItem = SavedTextItem(
        id = System.currentTimeMillis().toString(),
        title = state.inputText.take(24),
        text = state.inputText,
        count = state.repetitionCount
      )
      _uiState.update {
        it.copy(
          savedRepeats = listOf(newItem) + it.savedRepeats,
          isCurrentSaved = true,
          snackbarMessage = "Added to favorites! ♥"
        )
      }
    } else {
      _uiState.update { it.copy(isCurrentSaved = false) }
    }
  }

  fun showSnackbar(message: String) {
    _uiState.update { it.copy(snackbarMessage = message) }
  }

  fun dismissSnackbar() {
    _uiState.update { it.copy(snackbarMessage = null) }
  }

  private fun generateRepetition(
    text: String,
    count: Int,
    inNewLine: Boolean,
    style: TextStyleType
  ): String {
    if (text.isEmpty() || count <= 0) return ""
    val styledBase = TextStyler.applyStyle(text, style)
    val separator = if (inNewLine) "\n" else " "
    return buildString {
      for (i in 0 until count) {
        append(styledBase)
        if (i < count - 1) {
          append(separator)
        }
      }
    }
  }

  private fun saveRecentsToStorage(recents: List<RecentRepeatItem>) {
    try {
      val array = JSONArray()
      for (item in recents) {
        val obj = JSONObject()
        obj.put("id", item.id)
        obj.put("text", item.text)
        obj.put("count", item.count)
        obj.put("inNewLine", item.inNewLine)
        obj.put("timestamp", item.timestamp)
        array.put(obj)
      }
      prefs.edit().putString("saved_recents_json", array.toString()).apply()
    } catch (_: Exception) {}
  }

  private fun parseRecentsJson(jsonStr: String): List<RecentRepeatItem> {
    val list = mutableListOf<RecentRepeatItem>()
    try {
      val array = JSONArray(jsonStr)
      for (i in 0 until array.length()) {
        val obj = array.getJSONObject(i)
        list.add(
          RecentRepeatItem(
            id = obj.optString("id", System.currentTimeMillis().toString()),
            text = obj.optString("text", ""),
            count = obj.optInt("count", 1),
            inNewLine = obj.optBoolean("inNewLine", true),
            timestamp = obj.optString("timestamp", "Recently")
          )
        )
      }
    } catch (_: Exception) {}
    return list
  }
}
