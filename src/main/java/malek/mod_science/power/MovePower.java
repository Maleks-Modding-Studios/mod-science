package malek.mod_science.power;

public final class MovePower {

    private MovePower() {
    }

    public static double moveFirePower(IPowerMovement from, IPowerMovement to, double maxAmount) {
        maxAmount = from.extractFirePower(maxAmount);
        maxAmount -= to.insertFirePower(maxAmount);
        double extracted = from.extractFirePower(maxAmount);

        if (Math.abs(extracted - maxAmount) > 1e-9) {
            throw new IllegalArgumentException("Returning negative move amount (Fire Power)");
        }
        return maxAmount;
    }

    public static double moveArcPower(IPowerMovement from, IPowerMovement to, double maxAmount) {
        maxAmount = from.extractArcPower(maxAmount);
        maxAmount -= to.insertArcPower(maxAmount);
        double extracted = from.extractArcPower(maxAmount);

        if (Math.abs(extracted - maxAmount) > 1e-9) {
            throw new IllegalArgumentException("Returning negative move amount (Arc Power)");
        }
        return maxAmount;
    }

    public static double moveTimePower(IPowerMovement from, IPowerMovement to, double maxAmount) {
        maxAmount = from.extractTimePower(maxAmount);
        maxAmount -= to.insertTimePower(maxAmount);
        double extracted = from.extractTimePower(maxAmount);

        if (Math.abs(extracted - maxAmount) > 1e-9) {
            throw new IllegalArgumentException("Returning negative move amount (Time Power)");
        }
        return maxAmount;
    }

    public static double moveLightPower(IPowerMovement from, IPowerMovement to, double maxAmount) {
        maxAmount = from.extractLightPower(maxAmount);
        maxAmount -= to.insertLightPower(maxAmount);
        double extracted = from.extractLightPower(maxAmount);

        if (Math.abs(extracted - maxAmount) > 1e-9) {
            throw new IllegalArgumentException("Returning negative move amount (Light Power)");
        }
        return maxAmount;
    }
}
