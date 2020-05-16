package pl.widulinski.scrapp.enums;

public enum Categories {

    BOOKS("Books"),
    ELECTRONICS("Electronics"),
    TRAVELS("Travels"),
    PERFUMES("Perfumes"),
    FASHION("Fashion"),
    COMPUTERS("Computers"),
    SMARTPHONES("Smartphones"),
    COMPUTER_ACCESSORIES("Computer Accessories"),
    BEAUTY("Beauty");

    private final String displayValue;

    Categories(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
