/*
 * This code is directly taken from Create-Refabricated
 * Check it out at https://github.com/Create-Fabric/Create-Refabricated
 */
package malek.mod_science.util.general;

import org.jetbrains.annotations.Contract;

public final class MixinUtil {
    /**
     * A simple utility method that casts an object to a type.
     * <p>
     * This is intended to use with accessor Mixins.
     * </p>
     *
     * @param in
     *         the object to cast
     * @param <T>
     *         the type to cast to
     * @return the casted object
     */
    @Contract(value = "_->param1", pure = true)
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object in) {
        return (T) in;
    }
}
