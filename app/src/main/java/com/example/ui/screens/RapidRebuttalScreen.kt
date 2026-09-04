package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.WarRoomUiState
import com.example.ui.theme.*

@Composable
fun RapidRebuttalScreen(
    state: WarRoomUiState,
    onUpdateOpponentSpeech: (String) -> Unit,
    onGenerateRapidRebuttal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var copied by remember { mutableStateOf(false) }

    LaunchedEffect(copied) {
        if (copied) {
            kotlinx.coroutines.delay(2000)
            copied = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Banner
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.FlashOn, contentDescription = "Rapid Rebuttal", tint = GoldAmber, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "RAPID REBUTTAL ENGINE", fontSize = 14.sp, fontWeight = FontWeight.Black, color = TextWhite, letterSpacing = 1.sp)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(CrimsonDeep)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = "LIVE FLOOR COMBAT", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
                Text(
                    text = "When an opponent delivers an aggressive speech on the floor, paste what they just argued. The engine will instantly isolate their claims, uncover fallacies, and craft a surgical 20-45s live floor takedown.",
                    fontSize = 11.sp,
                    color = TextMuted,
                    lineHeight = 16.sp
                )
            }
        }

        // Input Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "OPPONENT JUST SAID...",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAmber,
                    letterSpacing = 0.5.sp
                )

                OutlinedTextField(
                    value = state.opponentSpeechText,
                    onValueChange = onUpdateOpponentSpeech,
                    label = { Text("Paste opponent's arguments, quotes, or claims here...", color = TextSubtle) },
                    minLines = 3,
                    maxLines = 6,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = CrimsonBright,
                        unfocusedBorderColor = WarRoomBorderGlow,
                        focusedContainerColor = WarRoomBlack,
                        unfocusedContainerColor = WarRoomBlack
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("opponent_speech_input")
                )

                Button(
                    onClick = onGenerateRapidRebuttal,
                    enabled = !state.isGeneratingRebuttal,
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonDeep),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp).border(1.dp, CrimsonLight.copy(alpha = 0.35f), RoundedCornerShape(10.dp)).testTag("generate_rapid_rebuttal_button")
                ) {
                    if (state.isGeneratingRebuttal) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("DISSECTING CLAIMS & CRAFTING REBUTTAL...", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    } else {
                        Icon(imageVector = Icons.Default.Bolt, contentDescription = "Rebuttal", modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("TAKEDOWN OPPONENT ON THE FLOOR", fontSize = 12.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }

        // Output Card
        state.rebuttalResult?.let { result ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Header with copy button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "SURGICAL REBUTTAL DOSSIER", fontSize = 13.sp, fontWeight = FontWeight.Black, color = TextWhite)
                            Text(text = "${result.wordCount} words • ~${result.speakingTimeSeconds}s delivery time", fontSize = 11.sp, color = GoldAmber)
                        }

                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Rebuttal", result.rapidSpeech)
                                clipboard.setPrimaryClip(clip)
                                copied = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = if (copied) EmeraldVerified else CrimsonDeep),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            modifier = Modifier.height(34.dp).testTag("copy_rebuttal_button")
                        ) {
                            Icon(imageVector = if (copied) Icons.Default.Check else Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = if (copied) "COPIED" else "COPY", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Opponent claims isolated
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = WarRoomSurfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "CLAIMS ISOLATED FROM OPPONENT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSubtle)
                            Spacer(modifier = Modifier.height(4.dp))
                            result.opponentClaimSummaries.forEach { claim ->
                                Text(text = "• $claim", fontSize = 11.sp, color = TextWhite)
                            }
                        }
                    }

                    // Logical fallacies identified
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = WarRoomSurfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "LOGICAL FALLACIES DETECTED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CrimsonLight)
                            Spacer(modifier = Modifier.height(4.dp))
                            result.logicalFallacies.forEach { fallacy ->
                                Text(text = "• $fallacy", fontSize = 11.sp, color = TextHighlight)
                            }
                        }
                    }

                    // Spoken Rebuttal Speech (Ready to deliver immediately)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(WarRoomBlack)
                            .border(1.dp, WarRoomBorderGlow, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(text = "LIVE FLOOR REBUTTAL (DELIVER IMMEDIATELY)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = EmeraldVerified, letterSpacing = 0.5.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = result.rapidSpeech,
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                color = TextWhite
                            )
                        }
                    }

                    // Knockout POIs
                    Text(text = "FOLLOW-UP TRAP POIS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldAmber)
                    result.targetPoiList.forEach { poi ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = WarRoomSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldDark)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = "\"${poi.question}\"", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = "Trap: ${poi.rhetoricalTrap}", fontSize = 10.sp, color = GoldLight)
                            }
                        }
                    }

                    // Knockout line
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(WarRoomSurfaceVariant)
                            .border(1.dp, GoldAmber, RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(text = "KNOCKOUT PUNCH: \"${result.knockoutClosingLine}\"", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = GoldLight)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
