package com.example.myapplicationtest.enums;

public enum TypeExercice {
    ABDOS("Abdos"),
    DORSAUX("Dorsaux"),
    BICEPS("Biceps"),
    MOLLET("Mollet"),
    PECTORAUX("Pectoraux"),
    AVANTBRAS("Avant-Bras"),
    JAMBES("Jambes"),
    EPAULES("Epaules"),
    TRICEPS("Triceps"),
    TOUT("Tout")
    ;

    private final String text;

    /**
     * @param text
     */
    TypeExercice(final String text) {
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
