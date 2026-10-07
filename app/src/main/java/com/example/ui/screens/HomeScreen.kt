package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.QuickRepeatItem
import com.example.model.RecentRepeatItem
import com.example.model.SavedTextItem
import com.example.model.TextStyleType
import com.example.ui.components.IllustrationArea
import com.example.ui.theme.AccentHeart
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GreenContainer
import com.example.ui.theme.GreenLight
import com.example.ui.theme.GreenPrimary
import com.example.ui.theme.GreenPrimaryDark
import com.example.ui.theme.OffWhiteBg
import com.example.ui.theme.OnGreenContainer
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SurfaceGray
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.viewmodel.TextRepeaterUiState
import kotlinx.coroutines.launch
import java.net.URLEncoder

private const val WHATSAPP_PHONE_NUMBER = "+8801410284106"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  uiState: TextRepeaterUiState,
  onInputChanged: (String) -> Unit,
  onCountChanged: (Int) -> Unit,
  onNewLineChanged: (Boolean) -> Unit,
  onRepeatClicked: () -> Unit,
  onClearClicked: () -> Unit,
  onToggleFavorite: () -> Unit,
  onToggleStylizeDialog: (Boolean) -> Unit,
  onToggleSavedDialog: (Boolean) -> Unit,
  onSelectStyle: (TextStyleType) -> Unit,
  onSelectQuickRepeat: (QuickRepeatItem) -> Unit,
  onSelectRecentRepeat: (RecentRepeatItem) -> Unit,
  onShowToast: (String) -> Unit
) {
  val context = LocalContext.current
  val clipboard = LocalClipboardManager.current
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()
  var showAllQuickRepeats by remember { mutableStateOf(false) }

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet(
        modifier = Modifier.width(310.dp),
        drawerContainerColor = PureWhite
      ) {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
        ) {
          // Drawer Header with Official Uploaded Logo
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 20.dp)
          ) {
            Image(
              painter = painterResource(id = R.drawable.img_app_logo),
              contentDescription = "Text Repeater Logo",
              modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column {
              Text(
                text = "Text Repeater",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
              )
              Text(
                text = "Offline Utility • v1.0",
                fontSize = 12.sp,
                color = GreenPrimary,
                fontWeight = FontWeight.SemiBold
              )
            }
          }

          HorizontalDivider(color = CardBorder)

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = "⚡ Features",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = TextPrimary
          )

          Spacer(modifier = Modifier.height(10.dp))
          DrawerInfoItem(title = "100% Offline", subtitle = "No login, no server, instant text repetition")
          DrawerInfoItem(title = "Universal Language", subtitle = "Supports English, বাংলা (Bangla) & Emojis")
          DrawerInfoItem(title = "Quick Repeats & Recents", subtitle = "One-tap presets and local history")

          Spacer(modifier = Modifier.weight(1f))

          // WhatsApp Support Button inside Drawer
          Button(
            onClick = { openWhatsApp(context, WHATSAPP_PHONE_NUMBER) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = WhatsAppGreen,
              contentColor = PureWhite
            )
          ) {
            Text("💬 Chat on WhatsApp", fontWeight = FontWeight.Bold)
          }

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedButton(
            onClick = { shareAppInfo(context) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(
              brush = androidx.compose.ui.graphics.SolidColor(GreenPrimary)
            )
          ) {
            Icon(Icons.Default.Share, contentDescription = null, tint = GreenPrimary, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Share App", color = GreenPrimary, fontWeight = FontWeight.SemiBold)
          }

          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "Text Repeater • All-in-One Utility",
            fontSize = 11.sp,
            color = TextMuted,
            modifier = Modifier.align(Alignment.CenterHorizontally)
          )
        }
      }
    }
  ) {
    Scaffold(
      containerColor = PureWhite,
      topBar = {
        // TOP BAR
        TopAppBar(
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PureWhite,
            navigationIconContentColor = TextPrimary,
            titleContentColor = TextPrimary,
            actionIconContentColor = GreenPrimary
          ),
          navigationIcon = {
            IconButton(
              onClick = { scope.launch { drawerState.open() } },
              modifier = Modifier.testTag("home_menu_button")
            ) {
              Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Hamburger menu"
              )
            }
          },
          title = {
            Row(
              verticalAlignment = Alignment.CenterVertically
            ) {
              Image(
                painter = painterResource(id = R.drawable.img_app_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(9.dp))
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "Text Repeater",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = TextPrimary
              )
            }
          },
          actions = {
            IconButton(
              onClick = { shareAppInfo(context) },
              modifier = Modifier.testTag("home_share_button")
            ) {
              Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share App",
                tint = GreenPrimary
              )
            }
          }
        )
      },
      bottomBar = {
        // BOTTOM ACTION BAR (Green bottom bar)
        Surface(
          color = GreenPrimary,
          modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .windowInsetsPadding(WindowInsets.navigationBars)
              .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // LEFT: Copy icon + "Copy"
            Button(
              onClick = {
                val toCopy = uiState.repeatedResult.ifEmpty { uiState.inputText }
                if (toCopy.isNotBlank()) {
                  clipboard.setText(AnnotatedString(toCopy))
                  onShowToast("Copied!")
                } else {
                  onShowToast("Enter and repeat text first")
                }
              },
              modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .testTag("copy_button"),
              shape = RoundedCornerShape(14.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = PureWhite,
                contentColor = GreenPrimaryDark
              )
            ) {
              Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Copy",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
            }

            // RIGHT: "Send" + send icon
            Button(
              onClick = {
                val toSend = uiState.repeatedResult.ifEmpty { uiState.inputText }
                if (toSend.isNotBlank()) {
                  shareRepeatedText(context, toSend)
                } else {
                  onShowToast("Enter and repeat text first")
                }
              },
              modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .testTag("send_button"),
              shape = RoundedCornerShape(14.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = GreenContainer,
                contentColor = OnGreenContainer
              )
            ) {
              Text(
                text = "Send",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.width(8.dp))
              Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding),
        contentAlignment = Alignment.TopCenter
      ) {
        LazyColumn(
          modifier = Modifier
            .widthIn(max = 600.dp)
            .fillMaxSize(),
          contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          // 1. Attractive Hero / Illustration Area near the top
          item {
            IllustrationArea()
          }

          // 2. Text Input Card
          item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Aa Enter Text",
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextPrimary
                )
                if (uiState.inputText.isNotEmpty()) {
                  Text(
                    text = "${uiState.inputText.length} chars",
                    fontSize = 12.sp,
                    color = TextMuted
                  )
                }
              }

              OutlinedTextField(
                value = uiState.inputText,
                onValueChange = onInputChanged,
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("text_input_field"),
                placeholder = {
                  Text(
                    text = "Write or paste anything here... (e.g. I Love You ❤️ / ভালোবাসি 🌸)",
                    color = TextMuted,
                    fontSize = 14.sp
                  )
                },
                minLines = 3,
                maxLines = 6,
                shape = RoundedCornerShape(16.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                  color = TextPrimary,
                  fontSize = 15.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedTextColor = TextPrimary,
                  unfocusedTextColor = TextPrimary,
                  focusedBorderColor = GreenPrimary,
                  unfocusedBorderColor = CardBorder,
                  focusedContainerColor = PureWhite,
                  unfocusedContainerColor = PureWhite,
                  cursorColor = GreenPrimary
                ),
                trailingIcon = {
                  if (uiState.inputText.isNotEmpty()) {
                    IconButton(onClick = onClearClicked) {
                      Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear text",
                        tint = TextMuted
                      )
                    }
                  }
                }
              )
            }
          }

          // 3. Repetition & Repeat in New Line
          item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
              Text(
                text = "↔ Repetition",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )

              // Number input field with Minus and Plus
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                IconButton(
                  onClick = { onCountChanged((uiState.repetitionCount - 1).coerceAtLeast(1)) },
                  modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceGray)
                    .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
                ) {
                  Icon(Icons.Default.Remove, contentDescription = "Decrease count", tint = TextPrimary)
                }

                OutlinedTextField(
                  value = uiState.repetitionCount.toString(),
                  onValueChange = { newVal ->
                    val num = newVal.filter { it.isDigit() }.toIntOrNull() ?: 1
                    onCountChanged(num)
                  },
                  modifier = Modifier
                    .weight(1f)
                    .testTag("repetition_count_input"),
                  singleLine = true,
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                  shape = RoundedCornerShape(14.dp),
                  colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = CardBorder
                  )
                )

                IconButton(
                  onClick = { onCountChanged((uiState.repetitionCount + 1).coerceAtMost(10000)) },
                  modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceGray)
                    .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
                ) {
                  Icon(Icons.Default.Add, contentDescription = "Increase count", tint = TextPrimary)
                }
              }

              // Quick multiplier chips
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                listOf(10, 50, 100, 500, 1000).forEach { preset ->
                  val isSelected = uiState.repetitionCount == preset
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(10.dp))
                      .background(if (isSelected) GreenContainer else SurfaceGray)
                      .border(
                        1.dp,
                        if (isSelected) GreenPrimary else CardBorder,
                        RoundedCornerShape(10.dp)
                      )
                      .clickable { onCountChanged(preset) }
                      .padding(vertical = 7.dp),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = "$preset",
                      fontSize = 12.sp,
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                      color = if (isSelected) OnGreenContainer else TextSecondary
                    )
                  }
                }
              }

              // Checkbox: "Repeat in New line"
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(14.dp))
                  .background(SurfaceGray)
                  .clickable { onNewLineChanged(!uiState.repeatInNewLine) }
                  .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Checkbox(
                  checked = uiState.repeatInNewLine,
                  onCheckedChange = onNewLineChanged,
                  colors = CheckboxDefaults.colors(
                    checkedColor = GreenPrimary,
                    checkmarkColor = PureWhite
                  ),
                  modifier = Modifier.testTag("new_line_checkbox")
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Repeat in New line",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = TextPrimary
                )
              }
            }
          }

          // 4. Large Rounded "Repeat Text →" button & Green outlined "Saved Text ♥" button
          item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
              // Large green rounded "Repeat Text →" button
              Button(
                onClick = onRepeatClicked,
                modifier = Modifier
                  .fillMaxWidth()
                  .height(54.dp)
                  .testTag("repeat_action_button"),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = GreenPrimary,
                  contentColor = PureWhite
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.Center
                ) {
                  Text(
                    text = "Repeat Text",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(19.dp)
                  )
                }
              }

              // Green outlined rounded "Saved Text ♥" button
              OutlinedButton(
                onClick = { onToggleSavedDialog(true) },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(50.dp)
                  .testTag("home_saved_button"),
                shape = RoundedCornerShape(18.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                  brush = androidx.compose.ui.graphics.SolidColor(GreenPrimary),
                  width = 1.5.dp
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                  containerColor = PureWhite,
                  contentColor = GreenPrimary
                )
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.Center
                ) {
                  Text(
                    text = "Saved Text",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = GreenPrimaryDark
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = AccentHeart,
                    modifier = Modifier.size(17.dp)
                  )
                }
              }
            }
          }

          // 5. Result Area Card
          item {
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .testTag("result_area"),
              shape = RoundedCornerShape(18.dp),
              colors = CardDefaults.cardColors(containerColor = PureWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
              elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                      text = "Result Area",
                      fontWeight = FontWeight.Bold,
                      fontSize = 15.sp,
                      color = TextPrimary
                    )
                    if (uiState.repeatedResult.isNotEmpty()) {
                      Spacer(modifier = Modifier.width(8.dp))
                      Box(
                        modifier = Modifier
                          .clip(RoundedCornerShape(8.dp))
                          .background(GreenContainer)
                          .padding(horizontal = 6.dp, vertical = 2.dp)
                      ) {
                        Text(
                          text = "${uiState.repeatedResult.length} chars",
                          fontSize = 11.sp,
                          fontWeight = FontWeight.Bold,
                          color = OnGreenContainer
                        )
                      }
                    }
                  }

                  Row(verticalAlignment = Alignment.CenterVertically) {
                    // Favorite / Heart icon
                    IconButton(
                      onClick = onToggleFavorite,
                      modifier = Modifier.size(36.dp)
                    ) {
                      Icon(
                        imageVector = if (uiState.isCurrentSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Save favorite",
                        tint = if (uiState.isCurrentSaved) AccentHeart else TextSecondary
                      )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Outlined button: "🖌 Stylize Text"
                    OutlinedButton(
                      onClick = { onToggleStylizeDialog(true) },
                      shape = RoundedCornerShape(10.dp),
                      contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                      border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(GreenPrimary),
                        width = 1.dp
                      )
                    ) {
                      Text(
                        text = "🖌 Stylize Text",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GreenPrimary
                      )
                    }
                  }
                }

                // Scrollable container for repeated text
                Box(
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceGray)
                    .padding(12.dp)
                ) {
                  if (uiState.repeatedResult.isEmpty()) {
                    Column(
                      modifier = Modifier.fillMaxSize(),
                      horizontalAlignment = Alignment.CenterHorizontally,
                      verticalArrangement = Arrangement.Center
                    ) {
                      Text(
                        text = "Tap 'Repeat Text →' to generate output",
                        fontSize = 13.sp,
                        color = TextMuted
                      )
                      Spacer(modifier = Modifier.height(4.dp))
                      Text(
                        text = "Supports English, Bangla & Emoji",
                        fontSize = 11.sp,
                        color = TextMuted
                      )
                    }
                  } else {
                    val resultScrollState = rememberScrollState()
                    val displayPreview = if (uiState.repeatedResult.length > 50000) {
                      uiState.repeatedResult.take(50000) + "\n\n...[Full text of ${uiState.repeatedResult.length} characters ready for Copy & Send]..."
                    } else {
                      uiState.repeatedResult
                    }

                    Text(
                      text = displayPreview,
                      fontSize = 14.sp,
                      color = TextPrimary,
                      modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(resultScrollState)
                    )
                  }
                }
              }
            }
          }

          // 6. Section: "⚡ Quick Repeats"
          item {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "⚡ Quick Repeats",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Text(
                text = "Tap to load",
                fontSize = 12.sp,
                color = TextMuted
              )
            }
          }

          // 2-column rounded cards
          val displayedQuick = if (showAllQuickRepeats) uiState.quickRepeats else uiState.quickRepeats.take(6)
          val chunkedQuick = displayedQuick.chunked(2)
          items(chunkedQuick) { rowItems ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              rowItems.forEach { item ->
                QuickRepeatCard(
                  item = item,
                  modifier = Modifier.weight(1f),
                  onClick = { onSelectQuickRepeat(item) }
                )
              }
              if (rowItems.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
              }
            }
          }

          // Green outlined: "More →" button
          item {
            OutlinedButton(
              onClick = { showAllQuickRepeats = !showAllQuickRepeats },
              modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .testTag("home_more_button"),
              shape = RoundedCornerShape(14.dp),
              border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = androidx.compose.ui.graphics.SolidColor(GreenPrimary),
                width = 1.5.dp
              ),
              colors = ButtonDefaults.outlinedButtonColors(
                containerColor = PureWhite,
                contentColor = GreenPrimary
              )
            ) {
              Text(
                text = if (showAllQuickRepeats) "Less ↑" else "More →",
                color = GreenPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          // 7. Section: "🕘 Recents"
          item {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "🕘 Recents",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              if (uiState.recentRepeats.isNotEmpty()) {
                Text(
                  text = "${uiState.recentRepeats.size} items",
                  fontSize = 12.sp,
                  color = TextMuted
                )
              }
            }
          }

          if (uiState.recentRepeats.isEmpty()) {
            item {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(16.dp))
                  .background(SurfaceGray)
                  .padding(18.dp),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "Recently repeated texts will appear here",
                  fontSize = 13.sp,
                  color = TextMuted
                )
              }
            }
          } else {
            items(uiState.recentRepeats) { recent ->
              RecentRepeatCard(
                recent = recent,
                onClick = { onSelectRecentRepeat(recent) }
              )
            }
          }

          // 8. CONTACT US / WHATSAPP SECTION (KEPT EXACTLY AS IT IS)
          item {
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
              shape = RoundedCornerShape(18.dp),
              colors = CardDefaults.cardColors(containerColor = OffWhiteBg),
              border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "💬 Contact Us",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                  )
                }

                Text(
                  text = "Need help or have suggestions? Chat with us on WhatsApp:",
                  fontSize = 13.sp,
                  color = TextSecondary,
                  modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // WhatsApp Button: "Chat on WhatsApp"
                Button(
                  onClick = { openWhatsApp(context, WHATSAPP_PHONE_NUMBER) },
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("whatsapp_contact_button"),
                  shape = RoundedCornerShape(14.dp),
                  colors = ButtonDefaults.buttonColors(
                    containerColor = WhatsAppGreen,
                    contentColor = PureWhite
                  ),
                  elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                  ) {
                    Text(
                      text = "Chat on WhatsApp",
                      fontSize = 15.sp,
                      fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = WHATSAPP_PHONE_NUMBER,
                      fontSize = 13.sp,
                      color = PureWhite.copy(alpha = 0.9f)
                    )
                  }
                }
              }
            }
          }

          // Extra spacing to ensure comfortable scrolling above bottom bar
          item {
            Spacer(modifier = Modifier.height(26.dp))
          }
        }
      }
    }
  }

  // Stylize Dialog
  if (uiState.showStylizeDialog) {
    StylizeDialog(
      currentStyle = uiState.currentStyle,
      onDismiss = { onToggleStylizeDialog(false) },
      onSelectStyle = onSelectStyle
    )
  }

  // Saved Text / Favorites Dialog
  if (uiState.showSavedDialog) {
    SavedTextDialog(
      savedItems = uiState.savedRepeats,
      onDismiss = { onToggleSavedDialog(false) },
      onSelect = { item ->
        onToggleSavedDialog(false)
        onSelectQuickRepeat(QuickRepeatItem(item.id, item.text, "Saved", item.count, true))
      },
      onShowToast = onShowToast
    )
  }
}

@Composable
private fun QuickRepeatCard(
  item: QuickRepeatItem,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Card(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
      .clickable(onClick = onClick)
      .testTag("quick_repeat_${item.id}"),
    colors = CardDefaults.cardColors(containerColor = PureWhite),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(13.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = item.category,
          fontSize = 11.sp,
          color = TextMuted
        )
        // Repeat count badge (e.g. "x145")
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(GreenContainer)
            .padding(horizontal = 7.dp, vertical = 2.dp)
        ) {
          Text(
            text = "x${item.defaultCount}",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = OnGreenContainer
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = item.text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPrimary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        Text(
          text = "Repeat →",
          fontSize = 11.sp,
          color = GreenPrimary,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

@Composable
private fun RecentRepeatCard(
  recent: RecentRepeatItem,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
      .clickable(onClick = onClick),
    colors = CardDefaults.cardColors(containerColor = PureWhite),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(GreenContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.History,
            contentDescription = null,
            tint = GreenPrimary,
            modifier = Modifier.size(18.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Text(
            text = recent.text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = "${recent.timestamp} • ${if (recent.inNewLine) "New line" else "Continuous"}",
            fontSize = 11.sp,
            color = TextSecondary
          )
        }
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(GreenContainer)
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Text(
          text = "x${recent.count}",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = OnGreenContainer
        )
      }
    }
  }
}

@Composable
private fun DrawerInfoItem(title: String, subtitle: String) {
  Column(modifier = Modifier.padding(vertical = 8.dp)) {
    Text(
      text = title,
      fontWeight = FontWeight.SemiBold,
      fontSize = 13.sp,
      color = TextPrimary
    )
    Text(
      text = subtitle,
      fontSize = 12.sp,
      color = TextSecondary
    )
  }
}

@Composable
private fun StylizeDialog(
  currentStyle: TextStyleType,
  onDismiss: () -> Unit,
  onSelectStyle: (TextStyleType) -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("Done", color = GreenPrimary, fontWeight = FontWeight.Bold)
      }
    },
    title = {
      Text(
        text = "🖌 Choose Font Style",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = TextPrimary
      )
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        TextStyleType.entries.forEach { style ->
          val isSelected = style == currentStyle
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(if (isSelected) GreenContainer else SurfaceGray)
              .border(
                1.dp,
                if (isSelected) GreenPrimary else Color.Transparent,
                RoundedCornerShape(12.dp)
              )
              .clickable { onSelectStyle(style) }
              .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = style.label,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
              fontSize = 14.sp,
              color = if (isSelected) OnGreenContainer else TextPrimary
            )
            if (isSelected) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = GreenPrimary,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    },
    containerColor = PureWhite
  )
}

@Composable
private fun SavedTextDialog(
  savedItems: List<SavedTextItem>,
  onDismiss: () -> Unit,
  onSelect: (SavedTextItem) -> Unit,
  onShowToast: (String) -> Unit
) {
  val clipboard = LocalClipboardManager.current
  AlertDialog(
    onDismissRequest = onDismiss,
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("Close", color = GreenPrimary, fontWeight = FontWeight.Bold)
      }
    },
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Favorite, contentDescription = null, tint = AccentHeart)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Saved Text ♥", fontWeight = FontWeight.Bold, fontSize = 18.sp)
      }
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        if (savedItems.isEmpty()) {
          Text(
            text = "No saved texts yet. Tap the heart icon in the result area to save texts to favorites!",
            fontSize = 13.sp,
            color = TextSecondary
          )
        } else {
          savedItems.forEach { item ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceGray)
                .clickable { onSelect(item) }
                .padding(10.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = item.title,
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = TextPrimary
                )
                Text(
                  text = item.text,
                  fontSize = 12.sp,
                  color = TextSecondary,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
              }
              IconButton(
                onClick = {
                  clipboard.setText(AnnotatedString(item.text))
                  onShowToast("Copied to clipboard!")
                }
              ) {
                Icon(
                  imageVector = Icons.Default.ContentCopy,
                  contentDescription = "Copy saved text",
                  tint = GreenPrimary,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
          }
        }
      }
    },
    containerColor = PureWhite
  )
}

private fun openWhatsApp(context: Context, phoneNumber: String) {
  val cleanNumber = phoneNumber.replace("+", "").replace(" ", "").replace("-", "")
  val defaultMessage = "Hello from Text Repeater app!"
  try {
    val encoded = URLEncoder.encode(defaultMessage, "UTF-8")
    val uri = Uri.parse("https://api.whatsapp.com/send?phone=$cleanNumber&text=$encoded")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
      setPackage("com.whatsapp")
    }
    context.startActivity(intent)
  } catch (_: Exception) {
    try {
      val uri = Uri.parse("https://wa.me/$cleanNumber")
      val fallbackIntent = Intent(Intent.ACTION_VIEW, uri)
      context.startActivity(fallbackIntent)
    } catch (_: Exception) {
      Toast.makeText(
        context,
        "WhatsApp is not installed. Contact number: $phoneNumber",
        Toast.LENGTH_LONG
      ).show()
    }
  }
}

private fun shareAppInfo(context: Context) {
  val sendIntent = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(
      Intent.EXTRA_TEXT,
      "Text Repeater - Fast offline Text Repeater utility for Android! Supports English, বাংলা (Bangla) & Emojis! 🚀✨"
    )
    type = "text/plain"
  }
  val shareIntent = Intent.createChooser(sendIntent, "Share Text Repeater")
  context.startActivity(shareIntent)
}

private fun shareRepeatedText(context: Context, textToShare: String) {
  val sendIntent = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(Intent.EXTRA_TEXT, textToShare)
    type = "text/plain"
  }
  val shareIntent = Intent.createChooser(sendIntent, "Send Repeated Text via")
  context.startActivity(shareIntent)
}
