package com.example.model

data class QuickRepeatItem(
  val id: String,
  val text: String,
  val category: String,
  val defaultCount: Int = 20,
  val inNewLine: Boolean = true
)

data class RecentRepeatItem(
  val id: String,
  val text: String,
  val count: Int,
  val inNewLine: Boolean,
  val timestamp: String
)

data class SavedTextItem(
  val id: String,
  val title: String,
  val text: String,
  val count: Int
)

enum class TextStyleType(val label: String) {
  ORIGINAL("Original"),
  BOLD("𝐁𝐨𝐥𝐝"),
  ITALIC("𝐼𝑡𝑎𝑙𝑖𝑐"),
  MONOSPACE("𝙼𝚘𝚗𝚘𝚜𝚙𝚊𝚌𝚎"),
  BUBBLE("Ⓑⓤⓑⓑⓛⓔ"),
  SQUARED("🄱🄾🅇🄴🄳"),
  SPACED("S p a c e d"),
  UPPERCASE("UPPERCASE")
}
