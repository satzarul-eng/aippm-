package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ContradictionItem
import com.example.data.KnowledgeBase
import com.example.data.TimelineEvent
import com.example.ui.theme.*

@Composable
fun ContradictionTimelineScreen(
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableStateOf(0) } // 0: Contradictions, 1: Timelines
    var selectedTimelineTopic by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Switcher
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(WarRoomSurface, RoundedCornerShape(12.dp))
                .border(1.dp, WarRoomBorder, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { selectedSection = 0 }
                    .testTag("tab_contradictions"),
                color = if (selectedSection == 0) CrimsonDark else Color.Transparent
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.CompareArrows, contentDescription = "Contradictions", tint = if (selectedSection == 0) Color.White else TextMuted, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "CONTRADICTION FINDER", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (selectedSection == 0) Color.White else TextMuted)
                }
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { selectedSection = 1 }
                    .testTag("tab_timelines"),
                color = if (selectedSection == 1) BlueAuthority else Color.Transparent
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Timeline, contentDescription = "Timelines", tint = if (selectedSection == 1) Color.White else TextMuted, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "PARLIAMENTARY TIMELINES", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (selectedSection == 1) Color.White else TextMuted)
                }
            }
        }

        if (selectedSection == 0) {
            // CONTRADICTION LIBRARY
            Text(
                text = "DOCUMENTED POLITICAL FLIP-FLOPS & CONTRADICTIONS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = CrimsonLight,
                letterSpacing = 0.5.sp
            )

            KnowledgeBase.documentedContradictions.forEach { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(CrimsonDark)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(text = item.politicianOrParty, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }

                            Text(text = item.issue, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldAmber)
                        }

                        // Past vs Current comparison
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                color = WarRoomSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(text = "PAST STANCE (${item.pastDate})", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextSubtle)
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(text = item.pastPosition, fontSize = 11.sp, color = TextWhite)
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(text = "Source: ${item.pastSource}", fontSize = 9.sp, color = TextMuted)
                                }
                            }

                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                color = WarRoomSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, CrimsonDark)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(text = "CURRENT STANCE (${item.currentDate})", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = CrimsonLight)
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(text = item.currentPosition, fontSize = 11.sp, color = TextWhite)
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(text = "Source: ${item.currentSource}", fontSize = 9.sp, color = TextMuted)
                                }
                            }
                        }

                        // Confrontation question
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(WarRoomBlack)
                                .border(1.dp, GoldDark, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(text = "LETHAL FLOOR CONFRONTATION QUESTION", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldAmber)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "\"${item.confrontationQuestion}\"",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextWhite,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // TIMELINE SECTION
            val timelineTopics = listOf("Criminal Law Overhaul", "Federalism & Art 356", "Electoral Finance", "Data Privacy")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                timelineTopics.forEachIndexed { index, topic ->
                    val isSelected = selectedTimelineTopic == index
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { selectedTimelineTopic = index },
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) BlueAuthority else WarRoomSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) Color.White else WarRoomBorder)
                    ) {
                        Text(
                            text = topic,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else TextMuted,
                            modifier = Modifier.padding(vertical = 8.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            val events = when (selectedTimelineTopic) {
                0 -> listOf(
                    TimelineEvent("1860", "Indian Penal Code Enacted", "Macaulay draft instituted by British colonial administration.", "Historic", "British Crown Gazette"),
                    TimelineEvent("2020", "Criminal Law Reforms Committee Constituted", "Ministry of Home Affairs establishes expert committee headed by Prof. Ranbir Singh.", "Govt Action", "PIB Press Release"),
                    TimelineEvent("Aug 2023", "Introduction in Lok Sabha", "Three bills introduced and referred to Standing Committee on Home Affairs headed by Brij Lal.", "Parliament", "Lok Sabha Secretariat"),
                    TimelineEvent("Dec 2023", "Passed by Parliament", "Passed with revised provisions incorporating committee suggestions.", "Parliament", "Gazette of India"),
                    TimelineEvent("July 1, 2024", "Statutes Come Into Effect", "BNS, BNSS, and BSA replace IPC, CrPC, and Evidence Act nationwide.", "Implementation", "PIB Notification")
                )
                1 -> listOf(
                    TimelineEvent("1959", "First Major Controversy: Kerala", "First dismissal of elected government under Article 356 (EMS Namboodiripad govt).", "Crisis", "Sarkaria Commission"),
                    TimelineEvent("1988", "Sarkaria Commission Report", "Recommends Article 356 be used only as an extreme measure of last resort.", "Govt Inquiry", "Home Ministry Records"),
                    TimelineEvent("1994", "S.R. Bommai v. Union of India", "Nine-judge bench rules Article 356 is subject to judicial review and floor test is mandatory.", "Judicial", "(1994) 3 SCC 1"),
                    TimelineEvent("2016", "Arunachal Pradesh & Uttarakhand", "Supreme Court restores dissolved state governments, citing Bommai doctrine.", "Judicial", "Supreme Court Judgments"),
                    TimelineEvent("2023-24", "Governors' Assent Delays", "Supreme Court bench led by CJI Chandrachud sets strict guidelines on Governors holding back bills.", "Current Status", "State of Punjab v. Principal Secretary")
                )
                2 -> listOf(
                    TimelineEvent("1951", "Representation of the People Act", "Sets baseline expenditure limits and corrupt practices rules.", "Statute", "Act 43 of 1951"),
                    TimelineEvent("2017", "Finance Act 2017 Amendments", "Removes cap on corporate donations and introduces Electoral Bond Scheme.", "Govt Action", "Ministry of Finance"),
                    TimelineEvent("2018", "Notification of Electoral Bonds", "Interest-free bearer banking instruments introduced for anonymous donations.", "Govt Action", "Gazette Notification"),
                    TimelineEvent("Feb 15, 2024", "ADR v. Union of India Verdict", "Unanimous five-judge Constitution bench strikes down Electoral Bonds as unconstitutional.", "Judicial", "Writ Petition (Civil) No. 880/2017"),
                    TimelineEvent("March 2024", "SBI Disclosures Published", "ECI publishes comprehensive donor-recipient alpha-numeric data on official portal.", "Public Record", "Election Commission of India")
                )
                else -> listOf(
                    TimelineEvent("1954", "M.P. Sharma Case", "Eight-judge bench rules right to privacy is not guaranteed under Indian Constitution.", "Historic", "AIR 1954 SC 300"),
                    TimelineEvent("2017", "K.S. Puttaswamy (9 Judges)", "Unanimous declaration that Right to Privacy is a Fundamental Right under Article 21.", "Judicial", "(2017) 10 SCC 1"),
                    TimelineEvent("2018", "Justice Srikrishna Committee", "Drafts initial personal data protection framework report.", "Govt Inquiry", "MeitY Report"),
                    TimelineEvent("Aug 2023", "DPDP Act 2023 Passed", "Digital Personal Data Protection Act receives Presidential assent.", "Statute", "Act No. 22 of 2023"),
                    TimelineEvent("Current", "Rules Formulation & DPBI Setup", "MeitY drafts rules for Data Protection Board of India operationalization.", "Implementation", "MeitY Notification")
                )
            }

            events.forEach { ev ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(48.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(GoldAmber)
                        )
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(50.dp)
                                .background(WarRoomBorder)
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = WarRoomSurfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = ev.dateOrYear, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = GoldAmber)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(WarRoomBorder)
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(text = ev.category, fontSize = 8.sp, color = TextWhite)
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = ev.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                            Text(text = ev.description, fontSize = 11.sp, color = TextMuted)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "Citation: ${ev.sourceCitation}", fontSize = 9.sp, color = TextSubtle)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
