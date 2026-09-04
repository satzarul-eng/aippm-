package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.AippmViewModel
import com.example.ui.WarRoomScreen
import com.example.ui.components.TeleprompterDialog
import com.example.ui.components.WarRoomHeader
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.WarRoomBlack

class MainActivity : ComponentActivity() {

    private val viewModel: AippmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val context = LocalContext.current
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(uiState.toastMessage) {
                    uiState.toastMessage?.let { msg ->
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        viewModel.clearToast()
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(WarRoomBlack),
                    containerColor = WarRoomBlack,
                    contentWindowInsets = WindowInsets.systemBars,
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        WarRoomHeader(
                            currentScreen = uiState.currentScreen,
                            bestDelegateEnabled = uiState.bestDelegateEnabled,
                            onToggleBestDelegate = { viewModel.toggleBestDelegate(it) },
                            onSelectScreen = { viewModel.setScreen(it) }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(WarRoomBlack)
                    ) {
                        when (uiState.currentScreen) {
                            WarRoomScreen.SPEECH_LAB -> {
                                SpeechGeneratorScreen(
                                    state = uiState,
                                    onUpdatePortfolio = { viewModel.updatePortfolio(it) },
                                    onUpdateParty = { viewModel.updateParty(it) },
                                    onUpdateAgenda = { viewModel.updateAgenda(it) },
                                    onUpdateSpeechType = { viewModel.updateSpeechType(it) },
                                    onUpdateSpeechLength = { viewModel.updateSpeechLength(it) },
                                    onUpdateAggression = { viewModel.updateAggression(it) },
                                    onUpdateControversy = { viewModel.updateControversy(it) },
                                    onUpdateResearchDepth = { viewModel.updateResearchDepth(it) },
                                    onUpdateTarget = { viewModel.updateTarget(it) },
                                    onUpdateObjective = { viewModel.updateObjective(it) },
                                    onUpdatePersonalStyle = { viewModel.updatePersonalStyle(it) },
                                    onGenerateSpeech = { viewModel.generateSpeech() },
                                    onApplyModifier = { viewModel.applySpeechModifier(it) },
                                    onTabChange = { viewModel.setActiveOutputTab(it) },
                                    onOpenTeleprompter = { viewModel.toggleTeleprompter(true) }
                                )
                            }
                            WarRoomScreen.ATTACK_DEFENCE -> {
                                AttackDefenceScreen(
                                    state = uiState,
                                    onUpdateAttackTarget = { viewModel.updateAttackTarget(it) },
                                    onUpdateAttackPosition = { viewModel.updateAttackPosition(it) },
                                    onUpdateAttackProve = { viewModel.updateAttackProve(it) },
                                    onGenerateAttack = { viewModel.generateAttackPlan() },
                                    onGenerateDefence = { viewModel.generateDefencePlan() }
                                )
                            }
                            WarRoomScreen.RAPID_REBUTTAL -> {
                                RapidRebuttalScreen(
                                    state = uiState,
                                    onUpdateOpponentSpeech = { viewModel.updateOpponentSpeech(it) },
                                    onGenerateRapidRebuttal = { viewModel.generateRapidRebuttal() }
                                )
                            }
                            WarRoomScreen.CONTRADICTIONS_TIMELINE -> {
                                ContradictionTimelineScreen()
                            }
                            WarRoomScreen.DOSSIER -> {
                                DossierScreen()
                            }
                        }

                        // Teleprompter Live Dialog
                        if (uiState.isTeleprompterActive && uiState.speechResult != null) {
                            TeleprompterDialog(
                                speechResult = uiState.speechResult!!,
                                secondsLeft = uiState.teleprompterTimerSeconds,
                                isRunning = uiState.isTeleprompterRunning,
                                onStart = { viewModel.startTeleprompterTimer() },
                                onPause = { viewModel.stopTeleprompterTimer() },
                                onReset = { viewModel.resetTeleprompterTimer() },
                                onDismiss = { viewModel.toggleTeleprompter(false) }
                            )
                        }
                    }
                }
            }
        }
    }
}
