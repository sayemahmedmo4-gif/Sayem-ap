package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.GreenContainer
import com.example.ui.theme.GreenLight
import com.example.ui.theme.GreenPrimary
import com.example.ui.theme.OnGreenContainer

@Composable
fun IllustrationArea(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(165.dp)
      .clip(RoundedCornerShape(22.dp))
      .background(
        Brush.linearGradient(
          colors = listOf(
            Color(0xFFF0FDF4),
            Color(0xFFDCFCE7),
            Color(0xFFECFDF5)
          )
        )
      )
      .border(1.dp, Color(0xFFBBF7D0), RoundedCornerShape(22.dp))
      .padding(14.dp)
  ) {
    // Decorative subtle backdrop circles
    Box(
      modifier = Modifier
        .size(100.dp)
        .offset(x = (-20).dp, y = (-20).dp)
        .clip(CircleShape)
        .background(Color(0x3386EFAC))
    )
    Box(
      modifier = Modifier
        .size(120.dp)
        .align(Alignment.BottomEnd)
        .offset(x = 25.dp, y = 25.dp)
        .clip(CircleShape)
        .background(Color(0x224ADE80))
    )

    // Main illustration content
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.Center
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Left Badge with official logo thumbnail
        Row(
          modifier = Modifier
            .shadow(3.dp, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 5.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Image(
            painter = painterResource(id = R.drawable.img_app_logo),
            contentDescription = null,
            modifier = Modifier
              .size(20.dp)
              .clip(RoundedCornerShape(5.dp))
          )
          Spacer(modifier = Modifier.width(7.dp))
          Text(
            text = "Text Repeater ✨",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = OnGreenContainer
          )
        }

        // Multiplier Pill
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(GreenPrimary)
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = "x 9999 Max",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Staggered Bumping Message Bubbles
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Bubble 1
        Box(
          modifier = Modifier
            .weight(1f)
            .shadow(2.dp, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomEnd = 14.dp, bottomStart = 4.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFF86EFAC), RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomEnd = 14.dp, bottomStart = 4.dp))
            .padding(horizontal = 10.dp, vertical = 7.dp)
        ) {
          Column {
            Text(
              text = "ভালোবাসি তোমাকে ❤️",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color(0xFF1E293B)
            )
            Text(
              text = "Bangla & Emoji",
              fontSize = 10.sp,
              color = Color(0xFF64748B)
            )
          }
        }

        // Repeat arrow badge
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(GreenPrimary)
            .border(2.dp, Color.White, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Repeat,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
          )
        }

        // Bubble 2
        Box(
          modifier = Modifier
            .weight(1f)
            .shadow(2.dp, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomStart = 14.dp, bottomEnd = 4.dp))
            .background(GreenContainer)
            .border(1.dp, GreenLight, RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomStart = 14.dp, bottomEnd = 4.dp))
            .padding(horizontal = 10.dp, vertical = 7.dp)
        ) {
          Column {
            Text(
              text = "Repeat 100x 🚀",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = OnGreenContainer
            )
            Text(
              text = "One-tap Copy & Send",
              fontSize = 10.sp,
              color = Color(0xFF0E9355)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Feature tags row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        listOf("⚡ Ultra Fast", "🔒 100% Offline", "✨ Stylize Text").forEach { tag ->
          Text(
            text = tag,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF0E9355),
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0x33BBF7D0))
              .padding(horizontal = 8.dp, vertical = 2.dp)
          )
        }
      }
    }
  }
}
