package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.WarRoomUiState
import com.example.ui.components.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeechGeneratorScreen(
    state: WarRoomUiState,
    onUpdatePortfolio: (String) -> Unit,
    onUpdateParty: (String) -> Unit,
    onUpdateAgenda: (String) -> Unit,
    onUpdateSpeechType: (SpeechType) -> Unit,
    onUpdateSpeechLength: (SpeechLength) -> Unit,
    onUpdateAggression: (AggressionLevel) -> Unit,
    onUpdateControversy: (ControversyLevel) -> Unit,
    onUpdateResearchDepth: (ResearchDepth) -> Unit,
    onUpdateTarget: (String) -> Unit,
    onUpdateObjective: (Objective) -> Unit,
    onUpdatePersonalStyle: (String) -> Unit,
    onGenerateSpeech: () -> Unit,
    onApplyModifier: (String) -> Unit,
    onTabChange: (Int) -> Unit,
    onOpenTeleprompter: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Central Command Live Dashboard
        CentralCommandDashboard(
            portfolio = state.portfolio,
            party = state.party,
            agenda = state.agenda,
            target = state.target,
            speechTimeSeconds = state.speechLength.targetSeconds,
            aggression = state.aggressionLevel,
            researchDepth = state.researchDepth,
            isGenerating = state.isGeneratingSpeech,
            progressText = state.researchProgressText
        )

        // SECTION 1: CORE PORTFOLIO & PARTY SELECTION
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Badge,
                        contentDescription = "Portfolio",
                        tint = GoldAmber,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "PORTFOLIO & PARTY DESIGNATION",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        letterSpacing = 0.5.sp
                    )
                }

                // Quick Portfolio Preset Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val presets = listOf(
                        "Narendra Modi (PM)",
                        "Rahul Gandhi (LoP)",
                        "Amit Shah (Home)",
                        "Mallikarjun Kharge",
                        "Arvind Kejriwal (AAP)",
                        "Mamata Banerjee",
                        "Opposition Frontbencher",
                        "Ruling-Party Senior MP",
                        "Independent MP"
                    )
                    presets.forEach { preset ->
                        val isSelected = state.portfolio.contains(preset.take(8))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) GoldDark else WarRoomSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) GoldAmber else WarRoomBorderGlow
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    onUpdatePortfolio(preset)
                                    if (preset.contains("Modi") || preset.contains("Shah") || preset.contains("Ruling")) {
                                        onUpdateParty("BJP / NDA")
                                    } else if (preset.contains("Gandhi") || preset.contains("Kharge") || preset.contains("Opposition")) {
                                        onUpdateParty("INC / INDIA Bloc")
                                    } else if (preset.contains("Kejriwal")) {
                                        onUpdateParty("Aam Aadmi Party")
                                    } else if (preset.contains("Mamata")) {
                                        onUpdateParty("Trinamool Congress")
                                    } else {
                                        onUpdateParty("Independent / Unaligned")
                                    }
                                }
                        ) {
                            Text(
                                text = preset,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else TextMuted,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                // Custom Portfolio Text Field
                OutlinedTextField(
                    value = state.portfolio,
                    onValueChange = onUpdatePortfolio,
                    label = { Text("Custom Portfolio Name", color = TextSubtle) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = GoldAmber,
                        unfocusedBorderColor = WarRoomBorderGlow,
                        focusedContainerColor = WarRoomBlack,
                        unfocusedContainerColor = WarRoomBlack
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("portfolio_input")
                )

                // Party Text Field
                OutlinedTextField(
                    value = state.party,
                    onValueChange = onUpdateParty,
                    label = { Text("Party / Coalition Alignment", color = TextSubtle) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = CrimsonBright,
                        unfocusedBorderColor = WarRoomBorderGlow,
                        focusedContainerColor = WarRoomBlack,
                        unfocusedContainerColor = WarRoomBlack
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("party_input")
                )
            }
        }

        // SECTION 2: AGENDA & SUB-TOPIC
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Topic,
                        contentDescription = "Agenda",
                        tint = CrimsonLight,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "PARLIAMENTARY AGENDA / CRISIS TOPIC",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        letterSpacing = 0.5.sp
                    )
                }

                // Sample Agenda Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    KnowledgeBase.sampleAgendas.forEach { sample ->
                        val isSelected = state.agenda == sample
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) CrimsonDeep else WarRoomSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) CrimsonBright else WarRoomBorderGlow
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onUpdateAgenda(sample) }
                        ) {
                            Text(
                                text = sample.take(36) + "...",
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else TextMuted,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                // Large Agenda Input Box
                OutlinedTextField(
                    value = state.agenda,
                    onValueChange = onUpdateAgenda,
                    label = { Text("Agenda / Debate Resolution / Breaking Scenario", color = TextSubtle) },
                    minLines = 2,
                    maxLines = 4,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = CrimsonBright,
                        unfocusedBorderColor = WarRoomBorderGlow,
                        focusedContainerColor = WarRoomBlack,
                        unfocusedContainerColor = WarRoomBlack
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("agenda_input")
                )
            }
        }

        // SECTION 3: SPEECH TYPE & TIME LIMIT
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.RecordVoiceOver,
                        contentDescription = "Speech Type",
                        tint = BlueAuthority,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "SPEECH FORMAT & FLOOR DURATION",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        letterSpacing = 0.5.sp
                    )
                }

                // Speech Type Chips
                Text(text = "SELECT SPEECH TYPE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSubtle)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SpeechType.entries.forEach { type ->
                        val isSelected = state.speechType == type
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) BlueAuthority else WarRoomSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) Color.White else WarRoomBorderGlow
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onUpdateSpeechType(type) }
                                .testTag("speech_type_${type.name.lowercase()}")
                        ) {
                            Text(
                                text = type.label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else TextMuted,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Speech Length Selector
                Text(text = "SPEECH TIME LIMIT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSubtle)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SpeechLength.entries.forEach { length ->
                        val isSelected = state.speechLength == length
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) CrimsonDeep else WarRoomSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) CrimsonBright else WarRoomBorderGlow
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onUpdateSpeechLength(length) }
                                .testTag("speech_length_${length.name.lowercase()}")
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = length.label,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else TextMuted
                                )
                                Text(
                                    text = "~${length.targetWords} words",
                                    fontSize = 9.sp,
                                    color = if (isSelected) GoldLight else TextSubtle
                                )
                            }
                        }
                    }
                }
            }
        }

        // SECTION 4: AGGRESSION & CONTROVERSY CONTROLS
        AggressionSliderCard(
            aggression = state.aggressionLevel,
            onAggressionChange = onUpdateAggression
        )

        ControversySliderCard(
            controversy = state.controversyLevel,
            onControversyChange = onUpdateControversy
        )

        // SECTION 5: TARGET & STRATEGIC OBJECTIVE
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Adjust,
                        contentDescription = "Tactical Objective",
                        tint = CrimsonLight,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "TACTICAL TARGET & STRATEGIC OBJECTIVE",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        letterSpacing = 0.5.sp
                    )
                }

                // Target Input
                OutlinedTextField(
                    value = state.target,
                    onValueChange = onUpdateTarget,
                    label = { Text("Cross-Examination Target (Person / Party / Policy)", color = TextSubtle) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = CrimsonBright,
                        unfocusedBorderColor = WarRoomBorderGlow,
                        focusedContainerColor = WarRoomBlack,
                        unfocusedContainerColor = WarRoomBlack
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("target_input")
                )

                // Objective Chips
                Text(text = "STRATEGIC OBJECTIVE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSubtle)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Objective.entries.forEach { obj ->
                        val isSelected = state.objective == obj
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) CrimsonDeep else WarRoomSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) CrimsonBright else WarRoomBorderGlow
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onUpdateObjective(obj) }
                        ) {
                            Text(
                                text = obj.label,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else TextMuted,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                // Personal Style Notes
                OutlinedTextField(
                    value = state.personalStyle,
                    onValueChange = onUpdatePersonalStyle,
                    label = { Text("Personal Style: 'Make me sound like...'", color = TextSubtle) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = GoldAmber,
                        unfocusedBorderColor = WarRoomBorderGlow,
                        focusedContainerColor = WarRoomBlack,
                        unfocusedContainerColor = WarRoomBlack
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Research Depth Protocol
        ResearchDepthSelector(
            selectedDepth = state.researchDepth,
            onSelectDepth = onUpdateResearchDepth
        )

        // GENERATE COMBATIVE SPEECH BUTTON (Sophisticated Dark Hero Action)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(CrimsonDeep, CrimsonBright)
                    )
                )
                .border(
                    1.dp,
                    CrimsonLight.copy(alpha = 0.35f),
                    RoundedCornerShape(12.dp)
                )
                .clickable(enabled = !state.isGeneratingSpeech) { onGenerateSpeech() }
                .padding(vertical = 14.dp)
                .testTag("generate_speech_button"),
            contentAlignment = Alignment.Center
        ) {
            if (state.isGeneratingSpeech) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "CONDUCTING PARLIAMENTARY RESEARCH PASS...",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Gavel,
                        contentDescription = "Generate",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "GENERATE COMBATIVE SPEECH",
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Black,
                        fontStyle = FontStyle.Italic,
                        color = Color.White,
                        letterSpacing = 2.sp
                    )
                }
            }
        }

        // Tactical Secondary Actions (from Design HTML: Defense Mode & Rapid Rebuttal)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onUpdateObjective(Objective.DEFEND_PORTFOLIO)
                        onUpdateSpeechType(SpeechType.DEFENCE_SPEECH)
                    }
                    .testTag("defense_mode_action_button"),
                shape = RoundedCornerShape(12.dp),
                color = WarRoomSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Defense Mode",
                        tint = GoldAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "DEFENSE MODE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextHighlight,
                        letterSpacing = 1.5.sp
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onUpdateObjective(Objective.REBUT_PREVIOUS)
                        onUpdateSpeechType(SpeechType.REBUTTAL)
                    }
                    .testTag("rapid_rebuttal_action_button"),
                shape = RoundedCornerShape(12.dp),
                color = WarRoomSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = "Rapid Rebuttal",
                        tint = GoldAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "RAPID REBUTTAL",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextHighlight,
                        letterSpacing = 1.5.sp
                    )
                }
            }
        }

        // GENERATED SPEECH OUTPUT DISPLAY CARD
        state.speechResult?.let { result ->
            SpeechDisplayCard(
                speechResult = result,
                activeTab = state.activeOutputTab,
                onTabChange = onTabChange,
                onApplyModifier = onApplyModifier,
                onRegenerate = onGenerateSpeech,
                onOpenTeleprompter = onOpenTeleprompter
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
