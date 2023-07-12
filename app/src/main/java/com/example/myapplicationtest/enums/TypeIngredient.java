package com.example.myapplicationtest.enums;

public enum TypeIngredient {
    BOISSON("Boisson"),
    LEGUME("Légume"),
    FECULENT("Féculent"),
    POISSON("Poisson"),
    VIANDE("Viande"),
    FRUIT("Fruit"),
    SUCRE("Sucré"),
    OLEAGINEUX("Oléagineux"),
    LEGUMINEUSES("Légumineuses")
    ;

    private final String text;

    /**
     * @param text
     */
    TypeIngredient(final String text) {
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
