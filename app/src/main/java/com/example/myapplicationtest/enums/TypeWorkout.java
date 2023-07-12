package com.example.myapplicationtest.enums;

public enum TypeWorkout {
    HAUTDUCORPS("Haut du corps"),
    BASDUCORPS("Bas du corps"),
    FULLBODY("Full body"),
    CARDIO("Cardio"),
    SUPERSET("Superset"),
    TOUT("Tout")
    ;

    private final String text;

    /**
     * @param text
     */
    TypeWorkout(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
