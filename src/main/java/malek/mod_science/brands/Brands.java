package malek.mod_science.brands;

public enum Brands {
    // TODO make the tooltips funny :tnypto:
    EPITOMIC_ELECTRONIC("Epitomic Electronic", "High tech electronics"),
    ANTI_Q("Anti-Q", "Ye olde, ye bolde, ye Covergirl"), // Terrible but I couldn't think of a 3rd part lol
    WIZRD("WizRD", "Unstable magitech devices"),
    NCOMPASS_INC("N-Compass Inc.", "Transdimensional stuff"),
    IKEA("Inbus Key Engineering Academy", "Furniture and inspiration for a better everyday life at home"), // IKEA slogan
    ACME("ACME", "Quality is our #1 dream"), // ACME slogan
    TECH_2X4("2x4 Tech", "Wood based machines"),
    CLOUD_BANK("Cloud Bank", "Reliable, scalable, and inexpensive cloud storage services."), // AWS style slogan
    HETERODYNES("Heterodynes", "Steampunk magitech"),
    BOXMORE("Boxmore", "Impractical weaponry");

    private final String brand;
    private final String defaultTooltip;

    Brands(String brandName, String tooltip) {
        this.brand = brandName;
        this.defaultTooltip = tooltip;
    }

    @Override
    public String toString() {
        return brand + ": " + defaultTooltip;
    }

    public String getBrand() {
        return brand;
    }

    public String getDefaultTooltip() {
        return defaultTooltip;
    }
}
