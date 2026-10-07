package com.example

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.model.QuickRepeatItem
import com.example.model.TextStyleType
import com.example.ui.viewmodel.TextRepeaterViewModel
import com.example.util.TextStyler
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleUnitTest {

  @Test
  fun testDefaultRepetitionCountIsOne() {
    val app = ApplicationProvider.getApplicationContext<Application>()
    val viewModel = TextRepeaterViewModel(app)
    assertEquals(1, viewModel.uiState.value.repetitionCount)
  }

  @Test
  fun testRepetitionWithBanglaAndEmoji() {
    val app = ApplicationProvider.getApplicationContext<Application>()
    val viewModel = TextRepeaterViewModel(app)
    viewModel.onInputTextChanged("ভালোবাসি ❤️")
    viewModel.onRepetitionCountChanged(3)
    viewModel.onNewLineToggle(true)
    viewModel.repeatText()

    val expected = "ভালোবাসি ❤️\nভালোবাসি ❤️\nভালোবাসি ❤️"
    assertEquals(expected, viewModel.uiState.value.repeatedResult)
  }

  @Test
  fun testRepetitionContinuousWithSpace() {
    val app = ApplicationProvider.getApplicationContext<Application>()
    val viewModel = TextRepeaterViewModel(app)
    viewModel.onInputTextChanged("Repeater")
    viewModel.onRepetitionCountChanged(3)
    viewModel.onNewLineToggle(false)
    viewModel.repeatText()

    val expected = "Repeater Repeater Repeater"
    assertEquals(expected, viewModel.uiState.value.repeatedResult)
  }

  @Test
  fun testSelectQuickRepeatPutsTextInInput() {
    val app = ApplicationProvider.getApplicationContext<Application>()
    val viewModel = TextRepeaterViewModel(app)
    val item = QuickRepeatItem("test1", "I Love You ❤️", "Emotion", 145, true)
    viewModel.selectQuickRepeat(item)

    assertEquals("I Love You ❤️", viewModel.uiState.value.inputText)
    assertEquals(145, viewModel.uiState.value.repetitionCount)
  }

  @Test
  fun testStylizePreservesBanglaAndEmoji() {
    val styledBold = TextStyler.applyStyle("Hello", TextStyleType.BOLD)
    assertTrue(styledBold.isNotEmpty())

    // Bangla text is preserved gracefully
    val banglaStyled = TextStyler.applyStyle("বাংলা ❤️", TextStyleType.BOLD)
    assertTrue(banglaStyled.contains("বাংলা"))
    assertTrue(banglaStyled.contains("❤️"))
  }
}
