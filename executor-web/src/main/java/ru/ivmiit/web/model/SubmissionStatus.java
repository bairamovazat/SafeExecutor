package ru.ivmiit.web.model;

public enum SubmissionStatus {
    CREATED, PROCESSED, CORRECT,
    WRONG_ANSWER, EMPTY_TEST, NO_OUTPUT,
    TIME_LIMIT, MEMORY_LIMIT, OUTPUT_LIMIT,
    COMPILER_ERROR, RUN_ERROR, COMPARE_ERROR,
    INTERNAL_ERROR;

    public static SubmissionStatus getStatusFromDomjudgeStatus(String status) {
        switch (status) {
            case "correct":
                return CORRECT;
            case "compiler-error":
                return COMPILER_ERROR;
            case "timelimit":
                return TIME_LIMIT;
            case "run-error":
                return RUN_ERROR;
            case "no-output":
                return NO_OUTPUT;
            case "wrong-answer":
                return WRONG_ANSWER;
            case "memory-limit'":
                return MEMORY_LIMIT;
            case "output-limit":
                return OUTPUT_LIMIT;
            case "compare-error":
                return COMPARE_ERROR;
            case "internal-error":
                return INTERNAL_ERROR;
            default:
                return null;
        }
    }
}
