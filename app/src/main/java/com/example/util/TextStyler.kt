package com.example.util

import com.example.model.TextStyleType

object TextStyler {

  fun applyStyle(input: String, style: TextStyleType): String {
    return when (style) {
      TextStyleType.ORIGINAL -> input
      TextStyleType.UPPERCASE -> input.uppercase()
      TextStyleType.SPACED -> input.map { it.toString() }.joinToString(" ")
      TextStyleType.BOLD -> transformCharacters(input, BOLD_A, BOLD_a, BOLD_0)
      TextStyleType.ITALIC -> transformCharacters(input, ITALIC_A, ITALIC_a, null)
      TextStyleType.MONOSPACE -> transformCharacters(input, MONO_A, MONO_a, MONO_0)
      TextStyleType.BUBBLE -> transformBubble(input)
      TextStyleType.SQUARED -> transformSquared(input)
    }
  }

  private const val BOLD_A = 0x1D400
  private const val BOLD_a = 0x1D41A
  private const val BOLD_0 = 0x1D7CE

  private const val ITALIC_A = 0x1D434
  private const val ITALIC_a = 0x1D44E

  private const val MONO_A = 0x1D670
  private const val MONO_a = 0x1D68A
  private const val MONO_0 = 0x1D7F6

  private fun transformCharacters(
    text: String,
    upperBase: Int,
    lowerBase: Int,
    digitBase: Int?
  ): String {
    val sb = StringBuilder()
    for (char in text) {
      when {
        char in 'A'..'Z' -> {
          val codePoint = upperBase + (char - 'A')
          sb.append(String(Character.toChars(codePoint)))
        }
        char in 'a'..'z' -> {
          val codePoint = lowerBase + (char - 'a')
          sb.append(String(Character.toChars(codePoint)))
        }
        digitBase != null && char in '0'..'9' -> {
          val codePoint = digitBase + (char - '0')
          sb.append(String(Character.toChars(codePoint)))
        }
        else -> sb.append(char)
      }
    }
    return sb.toString()
  }

  private fun transformBubble(text: String): String {
    val sb = StringBuilder()
    for (char in text) {
      when {
        char in 'A'..'Z' -> {
          val codePoint = 0x24B6 + (char - 'A')
          sb.append(String(Character.toChars(codePoint)))
        }
        char in 'a'..'z' -> {
          val codePoint = 0x24D0 + (char - 'a')
          sb.append(String(Character.toChars(codePoint)))
        }
        char in '1'..'9' -> {
          val codePoint = 0x2460 + (char - '1')
          sb.append(String(Character.toChars(codePoint)))
        }
        char == '0' -> sb.append("⓪")
        else -> sb.append(char)
      }
    }
    return sb.toString()
  }

  private fun transformSquared(text: String): String {
    val sb = StringBuilder()
    for (char in text) {
      when {
        char in 'A'..'Z' -> {
          val codePoint = 0x1F130 + (char - 'A')
          sb.append(String(Character.toChars(codePoint)))
        }
        char in 'a'..'z' -> {
          val codePoint = 0x1F130 + (char - 'a')
          sb.append(String(Character.toChars(codePoint)))
        }
        else -> sb.append(char)
      }
    }
    return sb.toString()
  }
}
