package com.example.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KnowledgeBase
import com.example.ui.theme.*

@Composable
fun DossierScreen(
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(0) } // 0: Constitution, 1: Laws, 2: SC Judgments
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Category Selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(WarRoomSurface, RoundedCornerShape(12.dp))
                .border(1.dp, WarRoomBorder, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val categories = listOf("CONSTITUTION", "INDIAN STATUTES", "SC JUDGMENTS")
            categories.forEachIndexed { index, cat ->
                val isSelected = selectedCategory == index
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { selectedCategory = index }
                        .testTag("dossier_tab_$index"),
                    color = if (isSelected) BlueAuthority else Color.Transparent
                ) {
                    Text(
                        text = cat,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else TextMuted,
                        modifier = Modifier.padding(vertical = 10.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        when (selectedCategory) {
            0 -> {
                // CONSTITUTIONAL ARTICLES
                Text(
                    text = "CONSTITUTIONAL PROVISIONS LAYER (ARTICLES 14, 19, 21, 356)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAmber,
                    letterSpacing = 0.5.sp
                )

                KnowledgeBase.constitutionalArticles.forEach { article ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(BlueAuthority)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(text = article.articleNumber, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                                Text(text = article.title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                            }

                            Text(text = article.corePrinciple, fontSize = 11.sp, color = TextWhite, lineHeight = 16.sp)

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = WarRoomSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(text = "PARLIAMENTARY APPLICATION IN AIPPM", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldAmber)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = article.parliamentaryApplication, fontSize = 10.sp, color = TextHighlight, lineHeight = 14.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "Common Pitfall: ${article.typicalMisuseOrControversy}", fontSize = 9.sp, color = TextMuted)
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                // INDIAN STATUTES
                Text(
                    text = "CRIMINAL, ELECTORAL & FINANCIAL STATUTES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CrimsonLight,
                    letterSpacing = 0.5.sp
                )

                KnowledgeBase.indianStatutes.forEach { statute ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                                    Text(text = statute.shortName, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                                Text(text = statute.legalType, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldAmber)
                            }

                            Text(text = statute.officialTitle, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                            Text(text = statute.coreProvisions, fontSize = 11.sp, color = TextHighlight, lineHeight = 16.sp)

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = WarRoomSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(text = "FLOOR DEBATE DYNAMICS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = CrimsonLight)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = statute.parliamentaryAngle, fontSize = 10.sp, color = TextWhite, lineHeight = 14.sp)
                                }
                            }
                        }
                    }
                }
            }

            2 -> {
                // SUPREME COURT JUDGMENTS
                Text(
                    text = "BINDING SUPREME COURT CONSTITUTION BENCH PRECEDENTS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldVerified,
                    letterSpacing = 0.5.sp
                )

                KnowledgeBase.landmarkJudgments.forEach { judgment ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(EmeraldDark)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(text = "${judgment.year} • ${judgment.benchSize}", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = EmeraldVerified)
                                }
                                Text(text = judgment.constitutionalProvision, fontSize = 9.sp, color = GoldLight)
                            }

                            Text(text = judgment.caseTitle, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                            Text(text = "Holding: ${judgment.coreHolding}", fontSize = 11.sp, color = TextHighlight, lineHeight = 16.sp)

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = WarRoomSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(text = "AIPPM STRATEGIC RELEVANCE", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = EmeraldVerified)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = judgment.relevanceToAippm, fontSize = 10.sp, color = TextWhite, lineHeight = 14.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
