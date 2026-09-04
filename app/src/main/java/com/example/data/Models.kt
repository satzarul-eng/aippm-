package com.example.data

enum class SpeechType(val label: String, val shortDesc: String) {
    GSL("General Speakers List (GSL)", "Comprehensive agenda stance and broad policy vision"),
    OPENING("Opening Speech", "Sets high-stakes narrative and political agenda"),
    MOD_CAUCUS("Moderated Caucus", "Focused, surgical intervention on a specific sub-agenda"),
    UNMOD_POINTS("Unmoderated Caucus Points", "Conversational, backroom lobbying & coalition-building leverage"),
    REPLY_SPEECH("Reply Speech", "Countering opposition narrative directly"),
    ATTACK_SPEECH("Attack Speech", "Aggressive deconstruction of opponent records & policies"),
    DEFENCE_SPEECH("Defence Speech", "Bulletproof justification and record defense"),
    CLOSING_SPEECH("Closing Speech", "Resonant summation and historic framing"),
    REBUTTAL("Rapid Rebuttal", "20-45s live floor takedown of previous speaker"),
    POI_RESPONSE("POI Response", "Deflecting and turning traps back on the questioner"),
    EMERGENCY_STATEMENT("Emergency Statement", "Breaking development crisis declaration")
}

enum class SpeechLength(val label: String, val targetSeconds: Int, val targetWords: Int) {
    SEC_30("30 seconds", 30, 65),
    SEC_45("45 seconds", 45, 95),
    SEC_60("60 seconds", 60, 130),
    SEC_75("75 seconds", 75, 160),
    SEC_90("90 seconds", 90, 195),
    MIN_2("2 minutes", 120, 260),
    CUSTOM("Custom Length", 90, 200)
}

enum class AggressionLevel(val level: Int, val label: String, val description: String) {
    DIPLOMATIC(1, "Diplomatic", "Statesmanlike, measured, firm parliamentary restraint"),
    FIRM(2, "Firm", "Clear assertion, controlled tension, strategic questions"),
    STRONG(3, "Strong", "Hard-hitting rhetoric, demanding direct answers"),
    AGGRESSIVE(4, "Aggressive", "Confrontational, exposing hypocrisy, high political pressure"),
    RUTHLESS(5, "Ruthless Parliamentary", "Combative, devastating rhetoric, relentless live cross-examination")
}

enum class ControversyLevel(val level: Int, val label: String, val description: String) {
    SAFE(1, "Safe", "Consensus-oriented, non-divisive arguments"),
    BOLD(2, "Bold", "Directly challenges status quo without taboos"),
    SHARP(3, "Sharp", "Highlights uncomfortable contradictions and omissions"),
    CONTROVERSIAL(4, "Controversial", "Tackles hyper-sensitive political battles with verified facts"),
    HIGHLY_CONFRONTATIONAL(5, "Highly Confrontational", "Unfiltered political combat, leaves opponent nowhere to hide")
}

enum class ResearchDepth(val label: String, val subtitle: String) {
    QUICK("QUICK", "Verified base research"),
    STANDARD("STANDARD", "Constitution + Laws + Political record"),
    DEEP("DEEP", "Judgments + Parliamentary records + Gazette"),
    WAR_ROOM("WAR ROOM", "Full Opponent intel + Contradiction hunting + Rebuttal prep")
}

enum class TargetCategory(val label: String) {
    PERSON("Specific Person"),
    PARTY("Political Party"),
    GOVERNMENT("Ruling Government"),
    OPPOSITION("Opposition Bloc"),
    POLICY("Specific Policy / Bill"),
    INSTITUTION("Institution / Commission"),
    COUNTRY("Foreign Country / Stance"),
    NO_TARGET("No Specific Target")
}

enum class Objective(val label: String) {
    DEFEND_PORTFOLIO("Defend Portfolio Record"),
    ATTACK_OPPONENT("Attack Opponent Position"),
    EXPOSE_CONTRADICTION("Expose Documented Contradiction"),
    DEMAND_ACCOUNTABILITY("Demand Immediate Accountability"),
    DEFEND_GOVT_RECORD("Defend Government Record"),
    ATTACK_GOVT_RECORD("Attack Government Record"),
    FORCE_ANSWER("Force Opponent To Answer"),
    CREATE_HEADLINE("Create Headline Moment"),
    BUILD_COALITION("Build Cross-Party Coalition"),
    PRESENT_SOLUTIONS("Present Actionable Solutions"),
    REBUT_PREVIOUS("Rebut Previous Speaker")
}

enum class SourceTier(val label: String, val badgeColor: Long) {
    PRIMARY_GOVT("Tier 1 — Govt / PIB / Gazette", 0xFF059669),
    PARLIAMENT("Tier 1 — Parliament / Lok Sabha", 0xFF0284C7),
    COURTS("Tier 1 — Supreme Court / High Courts", 0xFF7C3AED),
    POLITICAL("Tier 1 — Party Manifesto / Speeches", 0xFFD97706),
    RESEARCH("Tier 2 — PRS / Legal Research", 0xFF2563EB),
    JOURNALISM("Tier 3 — The Hindu / IE / PTI", 0xFF475569)
}

enum class ClaimCategory(val label: String) {
    FACT("Verified Fact"),
    ALLEGATION("Allegation (Unproven)"),
    POLITICAL_CLAIM("Political Claim"),
    COURT_FINDING("Judicial Finding"),
    OFFICIAL_POSITION("Official Government/Party Position"),
    MEDIA_REPORT("Media Report"),
    OPINION("Speaker Opinion")
}

data class FactCheckItem(
    val claim: String,
    val source: String,
    val tier: SourceTier,
    val category: ClaimCategory,
    val date: String,
    val isVerified: Boolean = true,
    val confidencePercent: Int = 96,
    val trustworthyReason: String
)

data class SourceItem(
    val title: String,
    val url: String,
    val tier: SourceTier,
    val consultedTopic: String
)

data class PoiItem(
    val question: String,
    val target: String,
    val objective: String,
    val rhetoricalTrap: String
)

data class SpeechResult(
    val id: String = java.util.UUID.randomUUID().toString(),
    val portfolio: String,
    val party: String,
    val agenda: String,
    val speechType: SpeechType,
    val speechLength: SpeechLength,
    val aggressionLevel: AggressionLevel,
    val controversyLevel: ControversyLevel,
    val researchDepth: ResearchDepth,
    val bestDelegateEnabled: Boolean,
    val fullSpeech: String,
    val hook: String,
    val position: String,
    val keyAttacks: List<String>,
    val keyFacts: List<String>,
    val keyArticles: List<String>,
    val keyLaws: List<String>,
    val keyQuestions: List<String>,
    val possiblePois: List<PoiItem>,
    val bestClosingLine: String,
    val factChecks: List<FactCheckItem>,
    val sources: List<SourceItem>,
    val wordCount: Int,
    val estimatedSeconds: Int,
    val generatedAt: Long = System.currentTimeMillis()
)

data class AttackPlan(
    val target: String,
    val opponentPosition: String,
    val coreVulnerability: String,
    val strongestAttack: String,
    val strongestEvidence: String,
    val anticipatedDefence: String,
    val counterToDefence: String,
    val sharpPoi: String,
    val knockoutPunchline: String,
    val verifiedFacts: List<FactCheckItem>
)

data class DefencePlan(
    val portfolio: String,
    val corePosition: String,
    val vulnerablePoint: String,
    val likelyAttack: String,
    val bestResponse: String,
    val constitutionalJustification: String,
    val legalJustification: String,
    val documentedEvidence: String,
    val finalComeback: String
)

data class RebuttalResult(
    val opponentClaimSummaries: List<String>,
    val logicalFallacies: List<String>,
    val contradictionIdentified: String,
    val rapidSpeech: String,
    val targetPoiList: List<PoiItem>,
    val knockoutClosingLine: String,
    val wordCount: Int,
    val speakingTimeSeconds: Int
)

data class ContradictionItem(
    val politicianOrParty: String,
    val issue: String,
    val pastPosition: String,
    val pastDate: String,
    val pastSource: String,
    val currentPosition: String,
    val currentDate: String,
    val currentSource: String,
    val politicalImpact: String,
    val confrontationQuestion: String
)

data class TimelineEvent(
    val dateOrYear: String,
    val title: String,
    val description: String,
    val category: String, // "Government Action", "Court Judgment", "Opposition Protest", "Parliamentary Debate"
    val sourceCitation: String
)

data class ConstitutionalArticle(
    val articleNumber: String,
    val title: String,
    val corePrinciple: String,
    val parliamentaryApplication: String,
    val typicalMisuseOrControversy: String
)

data class IndianStatute(
    val shortName: String,
    val officialTitle: String,
    val year: String,
    val legalType: String, // Act, Ordinance, Rule, Bill
    val coreProvisions: String,
    val parliamentaryAngle: String
)

data class LandmarkJudgment(
    val caseTitle: String,
    val year: String,
    val benchSize: String,
    val coreHolding: String,
    val constitutionalProvision: String,
    val relevanceToAippm: String
)
