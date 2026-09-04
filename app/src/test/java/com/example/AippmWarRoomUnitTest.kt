package com.example

import com.example.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AippmWarRoomUnitTest {

    private lateinit var repository: AippmRepository

    @Before
    fun setUp() {
        repository = AippmRepository()
    }

    @Test
    fun testSynthesizedSpeechGeneration_containsHookAndClosing() {
        val result = repository.generateSynthesizedSpeech(
            portfolio = "Narendra Modi (Prime Minister)",
            party = "BJP / NDA",
            agenda = "Implementation of New Criminal Laws (BNS, BNSS, BSA)",
            speechType = SpeechType.GSL,
            speechLength = SpeechLength.SEC_90,
            aggression = AggressionLevel.STRONG,
            controversy = ControversyLevel.SHARP,
            target = "Opposition Frontbenchers",
            objective = Objective.EXPOSE_CONTRADICTION,
            personalStyle = "Assertive Indian parliamentary orator",
            bestDelegate = true,
            researchDepth = ResearchDepth.WAR_ROOM
        )

        assertNotNull(result)
        assertTrue(result.fullSpeech.isNotBlank())
        assertTrue(result.wordCount > 50)
        assertTrue(result.estimatedSeconds > 20)
        assertFalse(result.keyAttacks.isEmpty())
        assertFalse(result.keyArticles.isEmpty())
        assertFalse(result.keyLaws.isEmpty())
        assertFalse(result.possiblePois.isEmpty())
        assertTrue(result.bestClosingLine.isNotBlank())
    }

    @Test
    fun testRapidRebuttalGeneration() = runBlocking {
        val rebuttal = repository.generateRapidRebuttal(
            opponentSpeech = "The government is bulldozing the federal structure and everyone agrees this is unconstitutional!",
            myPortfolio = "Ruling-Party MP",
            myParty = "NDA",
            aggression = AggressionLevel.AGGRESSIVE
        )

        assertNotNull(rebuttal)
        assertTrue(rebuttal.rapidSpeech.isNotBlank())
        assertFalse(rebuttal.opponentClaimSummaries.isEmpty())
        assertFalse(rebuttal.logicalFallacies.isEmpty())
        assertFalse(rebuttal.targetPoiList.isEmpty())
        assertTrue(rebuttal.knockoutClosingLine.isNotBlank())
    }

    @Test
    fun testAttackPlanGeneration() = runBlocking {
        val attackPlan = repository.buildAttackPlan(
            target = "Leader of Opposition",
            opponentPosition = "Opposing the new criminal laws",
            whatToProve = "Standing committee voting records contradict public outrage",
            aggression = AggressionLevel.RUTHLESS
        )

        assertNotNull(attackPlan)
        assertEquals("Leader of Opposition", attackPlan.target)
        assertTrue(attackPlan.strongestAttack.isNotBlank())
        assertTrue(attackPlan.strongestEvidence.isNotBlank())
        assertTrue(attackPlan.anticipatedDefence.isNotBlank())
        assertTrue(attackPlan.counterToDefence.isNotBlank())
        assertTrue(attackPlan.sharpPoi.isNotBlank())
        assertTrue(attackPlan.knockoutPunchline.isNotBlank())
    }

    @Test
    fun testDefencePlanGeneration() = runBlocking {
        val defencePlan = repository.buildDefencePlan(
            portfolio = "Minister of Home Affairs",
            agenda = "Federalism & Centre-State Relations"
        )

        assertNotNull(defencePlan)
        assertEquals("Minister of Home Affairs", defencePlan.portfolio)
        assertTrue(defencePlan.constitutionalJustification.isNotBlank())
        assertTrue(defencePlan.legalJustification.isNotBlank())
        assertTrue(defencePlan.finalComeback.isNotBlank())
    }

    @Test
    fun testKnowledgeBaseIntegrity() {
        assertTrue(KnowledgeBase.constitutionalArticles.size >= 6)
        assertTrue(KnowledgeBase.indianStatutes.size >= 6)
        assertTrue(KnowledgeBase.landmarkJudgments.size >= 5)
        assertTrue(KnowledgeBase.documentedContradictions.size >= 4)
    }
}
