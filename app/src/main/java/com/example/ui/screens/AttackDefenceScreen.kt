package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.WarRoomUiState
import com.example.ui.theme.*

@Composable
fun AttackDefenceScreen(
    state: WarRoomUiState,
    onUpdateAttackTarget: (String) -> Unit,
    onUpdateAttackPosition: (String) -> Unit,
    onUpdateAttackProve: (String) -> Unit,
    onGenerateAttack: () -> Unit,
    onGenerateDefence: () -> Unit,
    modifier: Modifier = Modifier
) {
    var modeSelected by remember { mutableStateOf(0) } // 0: Build My Attack, 1: Defend My Portfolio
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mode Selector (Attack vs Defence)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(WarRoomSurface, RoundedCornerShape(12.dp))
                .border(1.dp, WarRoomBorderGlow, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { modeSelected = 0 }
                    .testTag("tab_attack_mode"),
                color = if (modeSelected == 0) CrimsonDeep else Color.Transparent
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Gavel,
                        contentDescription = "Attack",
                        tint = if (modeSelected == 0) Color.White else TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "BUILD MY ATTACK",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (modeSelected == 0) Color.White else TextMuted
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { modeSelected = 1 }
                    .testTag("tab_defence_mode"),
                color = if (modeSelected == 1) BlueAuthority else Color.Transparent
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Defence",
                        tint = if (modeSelected == 1) Color.White else TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "DEFEND MY PORTFOLIO",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (modeSelected == 1) Color.White else TextMuted
                    )
                }
            }
        }

        if (modeSelected == 0) {
            // ==================== MODE 0: BUILD MY ATTACK ====================
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "SURGICAL OPPONENT CROSS-EXAMINATION",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = CrimsonLight,
                        letterSpacing = 0.5.sp
                    )

                    OutlinedTextField(
                        value = state.attackTarget,
                        onValueChange = onUpdateAttackTarget,
                        label = { Text("Who are you attacking?", color = TextSubtle) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            focusedBorderColor = CrimsonBright,
                            unfocusedBorderColor = WarRoomBorderGlow,
                            focusedContainerColor = WarRoomBlack,
                            unfocusedContainerColor = WarRoomBlack
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("attack_target_input")
                    )

                    OutlinedTextField(
                        value = state.attackPosition,
                        onValueChange = onUpdateAttackPosition,
                        label = { Text("What stance/claim did they take?", color = TextSubtle) },
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            focusedBorderColor = CrimsonBright,
                            unfocusedBorderColor = WarRoomBorderGlow,
                            focusedContainerColor = WarRoomBlack,
                            unfocusedContainerColor = WarRoomBlack
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("attack_position_input")
                    )

                    OutlinedTextField(
                        value = state.attackProve,
                        onValueChange = onUpdateAttackProve,
                        label = { Text("What specific vulnerability do you want to prove?", color = TextSubtle) },
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            focusedBorderColor = GoldAmber,
                            unfocusedBorderColor = WarRoomBorderGlow,
                            focusedContainerColor = WarRoomBlack,
                            unfocusedContainerColor = WarRoomBlack
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("attack_prove_input")
                    )

                    Button(
                        onClick = onGenerateAttack,
                        enabled = !state.isGeneratingAttack,
                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonDeep),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp).border(1.dp, CrimsonLight.copy(alpha = 0.35f), RoundedCornerShape(10.dp)).testTag("generate_attack_plan_button")
                    ) {
                        if (state.isGeneratingAttack) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("BUILDING ATTACK DOSSIER...", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Icon(imageVector = Icons.Default.Bolt, contentDescription = "Attack", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("DEPLOY SURGICAL ATTACK PLAN", fontSize = 12.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }

            // Attack Plan Output
            state.attackPlan?.let { plan ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CrimsonPrimary)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Whatshot, contentDescription = "Attack Vector", tint = CrimsonPrimary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "ATTACK BLUEPRINT: TARGETING ${plan.target.uppercase()}", fontSize = 13.sp, fontWeight = FontWeight.Black, color = TextWhite)
                        }

                        AttackResultItem(title = "1. PRIMARY ATTACK VECTOR", text = plan.strongestAttack, accentColor = CrimsonPrimary)
                        AttackResultItem(title = "2. UNDENIABLE PRIMARY EVIDENCE", text = plan.strongestEvidence, accentColor = EmeraldVerified)
                        AttackResultItem(title = "3. ANTICIPATED OPPONENT DEFENCE", text = plan.anticipatedDefence, accentColor = GoldAmber)
                        AttackResultItem(title = "4. COUNTER-TAKEDOWN TO DEFENCE", text = plan.counterToDefence, accentColor = BlueAuthority)
                        AttackResultItem(title = "5. SHARP FLOOR POI", text = plan.sharpPoi, accentColor = PurpleTactical)

                        // Knockout punchline
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(WarRoomSurfaceVariant)
                                .border(1.dp, GoldAmber, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(text = "KNOCKOUT PUNCHLINE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldAmber)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "\"${plan.knockoutPunchline}\"", fontSize = 13.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = TextWhite)
                            }
                        }
                    }
                }
            }
        } else {
            // ==================== MODE 1: DEFEND MY PORTFOLIO ====================
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "PORTFOLIO BULLETPROOF DEFENCE SYSTEM",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueAuthority,
                        letterSpacing = 0.5.sp
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = WarRoomSurfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "CURRENT PORTFOLIO UNDER ATTACK", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextSubtle)
                            Text(text = state.portfolio, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "AGENDA: ${state.agenda}", fontSize = 11.sp, color = TextMuted)
                        }
                    }

                    Button(
                        onClick = onGenerateDefence,
                        enabled = !state.isGeneratingDefence,
                        colors = ButtonDefaults.buttonColors(containerColor = BlueAuthority),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp).testTag("generate_defence_plan_button")
                    ) {
                        if (state.isGeneratingDefence) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("BUILDING BULLETPROOF DEFENCE...", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Icon(imageVector = Icons.Default.Shield, contentDescription = "Defend", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("GENERATE BULLETPROOF DEFENCE DOSSIER", fontSize = 12.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }

            // Defence Plan Output
            state.defencePlan?.let { plan ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BlueAuthority)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = "Defence", tint = EmeraldVerified, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "BULLETPROOF DEFENCE FOR ${plan.portfolio.uppercase()}", fontSize = 13.sp, fontWeight = FontWeight.Black, color = TextWhite)
                        }

                        AttackResultItem(title = "CORE PORTFOLIO DOCTRINE", text = plan.corePosition, accentColor = BlueAuthority)
                        AttackResultItem(title = "VULNERABLE AREA IDENTIFIED", text = plan.vulnerablePoint, accentColor = CrimsonBright)
                        AttackResultItem(title = "LIKELY OPPONENT ATTACK", text = plan.likelyAttack, accentColor = GoldAmber)
                        AttackResultItem(title = "BEST TACTICAL DEFENCE", text = plan.bestResponse, accentColor = EmeraldVerified)
                        AttackResultItem(title = "CONSTITUTIONAL JUSTIFICATION", text = plan.constitutionalJustification, accentColor = BlueAuthority)
                        AttackResultItem(title = "STATUTORY LEGAL JUSTIFICATION", text = plan.legalJustification, accentColor = PurpleTactical)
                        AttackResultItem(title = "DOCUMENTED EVIDENCE TO CITE", text = plan.documentedEvidence, accentColor = EmeraldVerified)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(WarRoomSurfaceVariant)
                                .border(1.dp, EmeraldVerified, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(text = "UNANSWERABLE FLOOR COMEBACK", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = EmeraldVerified)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "\"${plan.finalComeback}\"", fontSize = 13.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = TextWhite)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun AttackResultItem(
    title: String,
    text: String,
    accentColor: Color
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = WarRoomSurfaceVariant,
        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = accentColor, letterSpacing = 0.5.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, fontSize = 12.sp, color = TextWhite, lineHeight = 18.sp)
        }
    }
}
