package malek.mod_science.power;

public interface IPowerMovement extends IPower {

    default boolean allowInsertingFirePower() {
        return true;
    }
    default boolean allowInsertingArcPower() {
        return true;
    }
    default boolean allowInsertingTimePower() {
        return true;
    }
    default boolean allowInsertingLightPower() {
        return true;
    }

    default double insertFirePower(double amount) {
        return amount;
    }
    default double insertArcPower(double amount) {
        return amount;
    }
    default double insertTimePower(double amount) {
        return amount;
    }
    default double insertLightPower(double amount) {
        return amount;
    }

    default boolean allowExtractingFirePower() {
        return true;
    }
    default boolean allowExtractingArcPower() {
        return true;
    }
    default boolean allowExtractingLightPower() {
        return true;
    }
    default boolean allowExtractingTimePower() {
        return true;
    }

    default double extractFirePower(double maxAmount) {
        return 0;
    }
    default double extractArcPower(double maxAmount) {
        return 0;
    }
    default double extractTimePower(double maxAmount) {
        return 0;
    }
    default double extractLightPower(double maxAmount) {
        return 0;
    }
}
