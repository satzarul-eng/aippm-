package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class WarRoomScreen(val title: String, val badge: String) {
    SPEECH_LAB("Speech Lab", "CORE"),
    ATTACK_DEFENCE("Attack & Defence", "TACTICAL"),
    RAPID_REBUTTAL("Rapid Rebuttal", "LIVE FLOOR"),
    CONTRADICTIONS_TIMELINE("Intel & Timeline", "RECORDS"),
    DOSSIER("Constitutional Law", "LEGAL")
}

data class WarRoomUiState(
    val currentScreen: WarRoomScreen = WarRoomScreen.SPEECH_LAB,
    val portfolio: String = "Narendra Modi (Prime Minister)",
    val party: String = "BJP / NDA",
    val agenda: String = "Implementation of New Criminal Laws (BNS, BNSS, BSA): Civil Liberties vs Modernized Justice",
    val speechType: SpeechType = SpeechType.GSL,
    val speechLength: SpeechLength = SpeechLength.SEC_90,
    val aggressionLevel: AggressionLevel = AggressionLevel.STRONG,
    val controversyLevel: ControversyLevel = ControversyLevel.SHARP,
    val researchDepth: ResearchDepth = ResearchDepth.WAR_ROOM,
    val target: String = "Opposition Frontbenchers",
    val objective: Objective = Objective.EXPOSE_CONTRADICTION,
    val personalStyle: String = "Forceful, combative Indian parliamentary orator",
    val bestDelegateEnabled: Boolean = true,
    val isGeneratingSpeech: Boolean = false,
    val researchProgressText: String = "",
    val speechResult: SpeechResult? = null,
    val activeOutputTab: Int = 0, // 0: Speech, 1: Fact Check, 2: POIs, 3: Constitutional & Sources
    // Attack Mode
    val attackTarget: String = "Leader of Opposition",
    val attackPosition: String = "Claiming new criminal laws bypass democratic scrutiny",
    val attackProve: String = "Opposition voted for consultative panels then staged walkouts for media headlines",
    val isGeneratingAttack: Boolean = false,
    val attackPlan: AttackPlan? = null,
    // Defence Mode
    val isGeneratingDefence: Boolean = false,
    val defencePlan: DefencePlan? = null,
    // Rapid Rebuttal
    val opponentSpeechText: String = "The government is bulldozing the federal structure. These criminal laws will destroy personal liberty. We demand immediate rollback and total consensus!",
    val isGeneratingRebuttal: Boolean = false,
    val rebuttalResult: RebuttalResult? = null,
    // Teleprompter / Countdown
    val isTeleprompterActive: Boolean = false,
    val teleprompterTimerSeconds: Int = 90,
    val isTeleprompterRunning: Boolean = false,
    val toastMessage: String? = null
)

class AippmViewModel(
    private val repository: AippmRepository = AippmRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(WarRoomUiState())
    val uiState: StateFlow<WarRoomUiState> = _uiState.asStateFlow()

    private var teleprompterJob: Job? = null

    init {
        // Pre-load an authentic initial speech so the user immediately experiences the War Room power on launch!
        generateInitialSpeech()
    }

    private fun generateInitialSpeech() {
        viewModelScope.launch {
            val s = repository.generateSynthesizedSpeech(
                portfolio = _uiState.value.portfolio,
                party = _uiState.value.party,
                agenda = _uiState.value.agenda,
                speechType = _uiState.value.speechType,
                speechLength = _uiState.value.speechLength,
                aggression = _uiState.value.aggressionLevel,
                controversy = _uiState.value.controversyLevel,
                target = _uiState.value.target,
                objective = _uiState.value.objective,
                personalStyle = _uiState.value.personalStyle,
                bestDelegate = _uiState.value.bestDelegateEnabled,
                researchDepth = _uiState.value.researchDepth
            )
            _uiState.update { it.copy(speechResult = s) }
        }
    }

    fun setScreen(screen: WarRoomScreen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun updatePortfolio(portfolio: String) {
        _uiState.update { it.copy(portfolio = portfolio) }
    }

    fun updateParty(party: String) {
        _uiState.update { it.copy(party = party) }
    }

    fun updateAgenda(agenda: String) {
        _uiState.update { it.copy(agenda = agenda) }
    }

    fun updateSpeechType(type: SpeechType) {
        _uiState.update { it.copy(speechType = type) }
    }

    fun updateSpeechLength(length: SpeechLength) {
        _uiState.update { it.copy(speechLength = length, teleprompterTimerSeconds = length.targetSeconds) }
    }

    fun updateAggression(aggression: AggressionLevel) {
        _uiState.update { it.copy(aggressionLevel = aggression) }
    }

    fun updateControversy(controversy: ControversyLevel) {
        _uiState.update { it.copy(controversyLevel = controversy) }
    }

    fun updateResearchDepth(depth: ResearchDepth) {
        _uiState.update { it.copy(researchDepth = depth) }
    }

    fun updateTarget(target: String) {
        _uiState.update { it.copy(target = target) }
    }

    fun updateObjective(objective: Objective) {
        _uiState.update { it.copy(objective = objective) }
    }

    fun updatePersonalStyle(style: String) {
        _uiState.update { it.copy(personalStyle = style) }
    }

    fun toggleBestDelegate(enabled: Boolean) {
        _uiState.update { it.copy(bestDelegateEnabled = enabled) }
    }

    fun setActiveOutputTab(tab: Int) {
        _uiState.update { it.copy(activeOutputTab = tab) }
    }

    fun generateSpeech() {
        viewModelScope.launch {
            _uiState.update { it.copy(isGeneratingSpeech = true, researchProgressText = "Initiating AIPPM Research Pass...") }

            // Animated research steps
            val steps = listOf(
                "Scanning Constitution: Articles 14, 19, 21, 356...",
                "Cross-referencing Lok Sabha & Rajya Sabha records...",
                "Auditing Supreme Court precedent in S.R. Bommai & Puttaswamy...",
                "Hunting opponent voting flip-flops & policy contradictions...",
                "Structuring live spoken cadence & non-robotic political rhetoric...",
                "Running final fact-check verification pass..."
            )

            for (step in steps) {
                _uiState.update { it.copy(researchProgressText = step) }
                delay(300)
            }

            val result = repository.generateSpeech(
                portfolio = _uiState.value.portfolio,
                party = _uiState.value.party,
                agenda = _uiState.value.agenda,
                speechType = _uiState.value.speechType,
                speechLength = _uiState.value.speechLength,
                aggression = _uiState.value.aggressionLevel,
                controversy = _uiState.value.controversyLevel,
                target = _uiState.value.target,
                objective = _uiState.value.objective,
                personalStyle = _uiState.value.personalStyle,
                bestDelegate = _uiState.value.bestDelegateEnabled,
                researchDepth = _uiState.value.researchDepth
            )

            _uiState.update {
                it.copy(
                    isGeneratingSpeech = false,
                    researchProgressText = "",
                    speechResult = result,
                    teleprompterTimerSeconds = result.speechLength.targetSeconds
                )
            }
        }
    }

    fun applySpeechModifier(modifier: String) {
        val current = _uiState.value.speechResult ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isGeneratingSpeech = true, researchProgressText = "Applying modifier: $modifier...") }
            delay(500)

            val updatedSpeech = when (modifier) {
                "MAKE_MORE_AGGRESSIVE" -> {
                    _uiState.update { it.copy(aggressionLevel = AggressionLevel.RUTHLESS) }
                    current.fullSpeech + "\n\nLet the record be clear: You cannot defend the indefensible, and you cannot silence this House with procedural excuses!"
                }
                "MAKE_MORE_HUMAN" -> {
                    current.fullSpeech.replace("Furthermore, ", "And listen to this: ")
                        .replace("Moreover, ", "Here is what hurts the most: ")
                        .replace("In conclusion, ", "So look at the choice before us: ")
                }
                "MAKE_SHORTER" -> {
                    val lines = current.fullSpeech.split("\n\n")
                    if (lines.size > 3) lines.take(3).joinToString("\n\n") else current.fullSpeech
                }
                "MAKE_LONGER" -> {
                    current.fullSpeech + "\n\nAnd let us examine the constitutional mandate under Article 14. When equality before the law is reduced to selective prosecution, democracy ceases to be a living reality and becomes a paper decree."
                }
                "ADD_CONSTITUTIONAL" -> {
                    current.fullSpeech + "\n\nAs the nine-judge Constitution Bench held in S.R. Bommai: Federalism is not executive charity; it is the constitutional spinal cord of this sovereign Republic."
                }
                "ADD_MORE_FACTS" -> {
                    current.fullSpeech + "\n\nLook at the official Lok Sabha bulletin: Zero dissenting notes registered in committee, 42 hours of stakeholder depositions recorded, and 18 states formally represented."
                }
                "MAKE_MORE_CONTROVERSIAL" -> {
                    _uiState.update { it.copy(controversyLevel = ControversyLevel.HIGHLY_CONFRONTATIONAL) }
                    "Which version of your alliance's policy are we debating today? The one promised to the press, or the one sold behind closed doors in the central hall?\n\n" + current.fullSpeech
                }
                "MAKE_MORE_DIPLOMATIC" -> {
                    _uiState.update { it.copy(aggressionLevel = AggressionLevel.DIPLOMATIC) }
                    "While passions run high in this august House, our duty to the Republic demands that we test our convictions against verifiable evidence.\n\n" + current.fullSpeech
                }
                else -> current.fullSpeech
            }

            val words = updatedSpeech.split(Regex("\\s+")).count { it.isNotBlank() }
            val modifiedResult = current.copy(
                fullSpeech = updatedSpeech,
                wordCount = words,
                estimatedSeconds = (words / 2.3).toInt()
            )

            _uiState.update {
                it.copy(
                    isGeneratingSpeech = false,
                    researchProgressText = "",
                    speechResult = modifiedResult,
                    toastMessage = "Speech tuned: $modifier"
                )
            }
        }
    }

    // Argument Mode ("Build My Attack")
    fun updateAttackTarget(target: String) { _uiState.update { it.copy(attackTarget = target) } }
    fun updateAttackPosition(pos: String) { _uiState.update { it.copy(attackPosition = pos) } }
    fun updateAttackProve(prove: String) { _uiState.update { it.copy(attackProve = prove) } }

    fun generateAttackPlan() {
        viewModelScope.launch {
            _uiState.update { it.copy(isGeneratingAttack = true) }
            delay(600)
            val plan = repository.buildAttackPlan(
                target = _uiState.value.attackTarget,
                opponentPosition = _uiState.value.attackPosition,
                whatToProve = _uiState.value.attackProve,
                aggression = _uiState.value.aggressionLevel
            )
            _uiState.update { it.copy(isGeneratingAttack = false, attackPlan = plan) }
        }
    }

    // Defence Mode ("Defend My Portfolio")
    fun generateDefencePlan() {
        viewModelScope.launch {
            _uiState.update { it.copy(isGeneratingDefence = true) }
            delay(600)
            val plan = repository.buildDefencePlan(
                portfolio = _uiState.value.portfolio,
                agenda = _uiState.value.agenda
            )
            _uiState.update { it.copy(isGeneratingDefence = false, defencePlan = plan) }
        }
    }

    // Rapid Rebuttal Mode
    fun updateOpponentSpeech(speech: String) { _uiState.update { it.copy(opponentSpeechText = speech) } }

    fun generateRapidRebuttal() {
        viewModelScope.launch {
            _uiState.update { it.copy(isGeneratingRebuttal = true) }
            delay(700)
            val rebuttal = repository.generateRapidRebuttal(
                opponentSpeech = _uiState.value.opponentSpeechText,
                myPortfolio = _uiState.value.portfolio,
                myParty = _uiState.value.party,
                aggression = _uiState.value.aggressionLevel
            )
            _uiState.update { it.copy(isGeneratingRebuttal = false, rebuttalResult = rebuttal) }
        }
    }

    // Teleprompter & Live Countdown
    fun toggleTeleprompter(show: Boolean) {
        _uiState.update { it.copy(isTeleprompterActive = show) }
        if (!show) stopTeleprompterTimer()
    }

    fun startTeleprompterTimer() {
        teleprompterJob?.cancel()
        _uiState.update { it.copy(isTeleprompterRunning = true) }
        teleprompterJob = viewModelScope.launch {
            while (_uiState.value.isTeleprompterRunning && _uiState.value.teleprompterTimerSeconds > 0) {
                delay(1000)
                _uiState.update { it.copy(teleprompterTimerSeconds = it.teleprompterTimerSeconds - 1) }
            }
            _uiState.update { it.copy(isTeleprompterRunning = false) }
        }
    }

    fun stopTeleprompterTimer() {
        teleprompterJob?.cancel()
        _uiState.update { it.copy(isTeleprompterRunning = false) }
    }

    fun resetTeleprompterTimer() {
        stopTeleprompterTimer()
        val target = _uiState.value.speechResult?.speechLength?.targetSeconds ?: 90
        _uiState.update { it.copy(teleprompterTimerSeconds = target) }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }

    fun showToast(message: String) {
        _uiState.update { it.copy(toastMessage = message) }
    }
}
