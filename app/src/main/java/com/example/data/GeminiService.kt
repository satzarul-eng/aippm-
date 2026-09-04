package com.example.data

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun generateSpeechWithAi(
        portfolio: String,
        party: String,
        agenda: String,
        speechType: SpeechType,
        speechLength: SpeechLength,
        aggression: AggressionLevel,
        controversy: ControversyLevel,
        target: String,
        objective: Objective,
        personalStyle: String,
        bestDelegate: Boolean,
        researchDepth: ResearchDepth
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            throw IllegalStateException("API_KEY_UNAVAILABLE")
        }

        val prompt = buildAippmPrompt(
            portfolio = portfolio,
            party = party,
            agenda = agenda,
            speechType = speechType,
            speechLength = speechLength,
            aggression = aggression,
            controversy = controversy,
            target = target,
            objective = objective,
            personalStyle = personalStyle,
            bestDelegate = bestDelegate,
            researchDepth = researchDepth
        )

        val requestJson = JSONObject().apply {
            val contentsArray = JSONArray()
            val contentObj = JSONObject().apply {
                val partsArray = JSONArray()
                partsArray.put(JSONObject().put("text", prompt))
                put("parts", partsArray)
            }
            contentsArray.put(contentObj)
            put("contents", contentsArray)

            val generationConfig = JSONObject().apply {
                put("temperature", 0.65)
                put("topP", 0.92)
                put("topK", 40)
            }
            put("generationConfig", generationConfig)

            val systemInstruction = JSONObject().apply {
                val parts = JSONArray()
                parts.put(
                    JSONObject().put(
                        "text",
                        """
                        You are the Senior Political Strategist in the AIPPM WAR ROOM for Model United Nations / Indian Parliamentary Simulations.
                        YOUR ABSOLUTE MANDATE:
                        1. Produce speeches that sound like an actual, seasoned Indian political leader speaking live in a high-stakes parliamentary session or AIPPM committee under intense pressure.
                        2. The speech must feel 100% naturally spoken by a live human delegate.
                           - Vary sentence lengths dynamically.
                           - Use occasional short, punchy sentences.
                           - Employ natural rhetorical questions and counter-questions.
                           - Use strategic, deliberate repetition when politically impactful ("Not yesterday. Not today. And never tomorrow.").
                           - Smooth conversational transitions.
                           - Interrupt rhythm deliberately.
                           - Never write like an essay, school speech, debate textbook, or ChatGPT answer.
                           - STRICTLY FORBIDDEN: "In today's rapidly changing world", "Furthermore", "Moreover", "In conclusion", "Honourable Chair, esteemed delegates", robotic numbered points, perfect symmetrical paragraphs, academic jargon.
                        3. FACTUAL INTEGRITY & REAL RESEARCH:
                           - NEVER invent dates, statistics, laws, constitutional Articles, Supreme Court judgments, or political quotes.
                           - If citing an Article or case law, state it naturally in spoken terms (e.g., "The Supreme Court in S.R. Bommai made it unequivocally clear...").
                           - Never present an unverified allegation as a proven court finding.
                        4. TONE & COMBAT:
                           - Direct, confident, argumentative, politically sharp, fact-heavy, spontaneous.
                           - Attack the opponent's documented contradictions, policy failures, and voting records rather than indulging in petty personal insults.
                        """.trimIndent()
                    )
                )
                put("parts", parts)
            }
            put("systemInstruction", systemInstruction)
        }

        val requestBody = requestJson.toString().toRequestBody(jsonMediaType)
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            val errorBody = response.body?.string() ?: "Unknown error"
            throw RuntimeException("Gemini API Error: ${response.code} - $errorBody")
        }

        val responseText = response.body?.string() ?: throw RuntimeException("Empty response from Gemini")
        val responseObj = JSONObject(responseText)
        val candidates = responseObj.optJSONArray("candidates")
        if (candidates != null && candidates.length() > 0) {
            val candidate = candidates.getJSONObject(0)
            val content = candidate.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            if (parts != null && parts.length() > 0) {
                return@withContext parts.getJSONObject(0).optString("text", "")
            }
        }
        throw RuntimeException("Failed to extract speech from Gemini response")
    }

    private fun buildAippmPrompt(
        portfolio: String,
        party: String,
        agenda: String,
        speechType: SpeechType,
        speechLength: SpeechLength,
        aggression: AggressionLevel,
        controversy: ControversyLevel,
        target: String,
        objective: Objective,
        personalStyle: String,
        bestDelegate: Boolean,
        researchDepth: ResearchDepth
    ): String {
        return """
        Generate an authentic, high-impact AIPPM parliamentary speech with the following parameters:

        PORTFOLIO: $portfolio
        PARTY / COALITION: $party
        AGENDA: $agenda
        SPEECH TYPE: ${speechType.label}
        TARGET TIME: ${speechLength.targetSeconds} seconds (Strict target word count: approximately ${speechLength.targetWords} words)
        AGGRESSION LEVEL: ${aggression.level}/5 (${aggression.label}) - ${aggression.description}
        CONTROVERSY LEVEL: ${controversy.level}/5 (${controversy.label}) - ${controversy.description}
        TARGET OF SPEECH: ${if (target.isBlank()) "General House / Opposing Narrative" else target}
        STRATEGIC OBJECTIVE: ${objective.label}
        PERSONAL STYLE NOTES: ${if (personalStyle.isBlank()) "Sharp, assertive Indian parliamentary orator" else personalStyle}
        BEST DELEGATE MODE: ${if (bestDelegate) "ENABLED (Optimize for memorable rhetoric, factual authority, agenda mastery, knockout delivery)" else "STANDARD"}
        RESEARCH DEPTH: ${researchDepth.label}

        STRUCTURE TO FOLLOW IN THE SPOKEN SPEECH:
        1. HOOK: A bold, attention-commanding opening that immediately seizes the room (direct question, political contradiction, constitutional paradox, or challenging documented fact).
        2. POSITION: Unapologetic stance representing the portfolio's core political doctrine.
        3. VERIFIED EVIDENCE: Accurate references to Indian law, constitutional provisions (Articles 14, 19, 21, 32, 226, 356, etc. where applicable), official gazette, Lok Sabha/Rajya Sabha records, or Supreme Court judgments.
        4. SURGICAL ATTACK / REBUTTAL: Pinpoint the opponent's recorded hypocrisy or policy failure with factual pressure.
        5. POLITICAL PUNCH: A memorable, high-impact line that forces the room to react.
        6. CONFRONTATION QUESTION: A direct, impossible-to-evade question to the target.
        7. ACTIONABLE SOLUTION: Concrete parliamentary policy resolution or legislative stance.
        8. UNFORGETTABLE CLOSING: Strong, resonant sign-off (NO generic 'Thank you').

        IMPORTANT:
        Output the speech in clean, spoken paragraphs ready for immediate floor delivery.
        After the speech, provide a section starting with '--- ANALYSIS DOSSIER ---' containing:
        - KEY ATTACKS: (3 bullet points)
        - KEY FACTS: (3 bullet points with verified sources)
        - CONSTITUTIONAL ARTICLES: (Relevant articles cited)
        - RELEVANT LAWS: (Statutes cited)
        - SHARP POIS: (2-3 razor-sharp Points of Information to ask on the floor)
        - KNOCKOUT CLOSING LINE: (The single most memorable one-liner)
        """.trimIndent()
    }
}
