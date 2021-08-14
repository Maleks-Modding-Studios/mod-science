package malek.mod_science;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

public final class ModScienceRequirements {

    private ModScienceRequirements() {
    }

    @Contract("null, _ -> fail;  !null, _ -> param1")
    public static @NotNull String requireNonEmpty(String string, String reason) {
        if (requireNonNull(string).isEmpty()) {
            throw new IllegalArgumentException(reason);
        }

        return string;
    }
}
