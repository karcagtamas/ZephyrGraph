package eu.karcags.ceg.generator

enum class TestType {
    ON,
    ON1,
    ON2,
    OFF,
    OFF1,
    OFF2,
    IN,
    IN1,
    IN2,
    ININ,
    OUT,
    OUT1,
    OUT2,
    TRUE,
    FALSE;

    fun expectation(): Boolean {
        return when (this) {
            ON -> true
            ON1 -> true
            ON2 -> true
            OFF -> false
            OFF1 -> false
            OFF2 -> false
            IN -> true
            IN1 -> true
            IN2 -> true
            ININ -> true
            OUT -> false
            OUT1 -> false
            OUT2 -> false
            TRUE -> true
            FALSE -> false
        }
    }
}