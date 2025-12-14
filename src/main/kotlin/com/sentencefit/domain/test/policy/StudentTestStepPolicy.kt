package com.sentencefit.domain.test.policy

import com.sentencefit.domain.test.exception.StudentTestErrorCode
import com.sentencefit.domain.test.exception.StudentTestException
import com.sentencefit.domain.test.model.AnswerType
import com.sentencefit.domain.test.model.StepType

object StudentTestStepPolicy {

    private val stepDefinitions: Map<Int, Pair<StepType, AnswerType>> = mapOf(
        1 to (StepType.ARRANGE_WITH_HINT to AnswerType.TOKEN_ARRAY),
        2 to (StepType.WRITE_WITH_HINT to AnswerType.TEXT),
        3 to (StepType.WRITE_ONLY to AnswerType.TEXT),
    )

    fun validateStepNo(stepNo: Int) {
        if (!stepDefinitions.containsKey(stepNo)) {
            throw StudentTestException(StudentTestErrorCode.INVALID_STEP_NO)
        }
    }

    fun getStepType(stepNo: Int): StepType {
        validateStepNo(stepNo)
        return stepDefinitions.getValue(stepNo).first
    }

    fun getAnswerType(stepNo: Int): AnswerType {
        validateStepNo(stepNo)
        return stepDefinitions.getValue(stepNo).second
    }

    fun isLastStep(stepNo: Int): Boolean = stepNo == stepDefinitions.keys.maxOrNull()

    fun nextStepNo(stepNo: Int): Int? =
        if (isLastStep(stepNo)) null else stepNo + 1
}