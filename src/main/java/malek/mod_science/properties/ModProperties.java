package malek.mod_science.properties;

import malek.mod_science.blocks.power.Side;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.Direction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ModProperties {
    public static final BooleanProperty NORTH_CONNECTED;
    public static final BooleanProperty SOUTH_CONNECTED;
    public static final BooleanProperty EAST_CONNECTED;
    public static final BooleanProperty WEST_CONNECTED;
    public static final BooleanProperty UP_CONNECTED;
    public static final BooleanProperty DOWN_CONNECTED;
    public static final Set<BooleanProperty> CONNECTED_DIRECTIONS;


    public static final EnumProperty<Side> SIDE_NORTH;
    public static final EnumProperty<Side> SIDE_SOUTH;
    public static final EnumProperty<Side> SIDE_EAST;
    public static final EnumProperty<Side> SIDE_WEST;
    public static final EnumProperty<Side> SIDE_UP;
    public static final EnumProperty<Side> SIDE_DOWN;
    public static final Set<EnumProperty<Side>> SIDE_DIRECTIONS;
    static {
        NORTH_CONNECTED = BooleanProperty.of("north_connected");
        SOUTH_CONNECTED = BooleanProperty.of("south_connected");
        EAST_CONNECTED = BooleanProperty.of("east_connected");
        WEST_CONNECTED = BooleanProperty.of("west_connected");
        UP_CONNECTED = BooleanProperty.of("up_connected");
        DOWN_CONNECTED = BooleanProperty.of("down_connected");
        HashSet<BooleanProperty> temp = new HashSet<>();
        setConnectedDirections(temp);
        CONNECTED_DIRECTIONS = Collections.unmodifiableSet(temp);

        SIDE_NORTH = EnumProperty.of("side_north", Side.class);
        SIDE_SOUTH = EnumProperty.of("side_south", Side.class);
        SIDE_EAST = EnumProperty.of("side_east", Side.class);
        SIDE_WEST = EnumProperty.of("side_west", Side.class);
        SIDE_UP = EnumProperty.of("side_up", Side.class);
        SIDE_DOWN = EnumProperty.of("side_down", Side.class);

        HashSet<EnumProperty<Side>> temp2 = new HashSet<>();
        setConnectedSides(temp2);
        SIDE_DIRECTIONS = temp2;
    }
    private static void setConnectedSides(HashSet<EnumProperty<Side>> temp) {
        temp.add(SIDE_NORTH);
        temp.add(SIDE_SOUTH);
        temp.add(SIDE_UP);
        temp.add(SIDE_DOWN);
        temp.add(SIDE_EAST);
        temp.add(SIDE_WEST);
    }
    private static void setConnectedDirections(HashSet<BooleanProperty> temp) {
        temp.add(NORTH_CONNECTED);
        temp.add(SOUTH_CONNECTED);
        temp.add(EAST_CONNECTED);
        temp.add(WEST_CONNECTED);
        temp.add(UP_CONNECTED);
        temp.add(DOWN_CONNECTED);
    }
    public static Direction getConnectedDirection(BooleanProperty connectedDirection) throws Exception {
        return switch (connectedDirection.getName()) {
            case "north_connected" -> Direction.NORTH;
            case "south_connected" -> Direction.SOUTH;
            case "east_connected" -> Direction.EAST;
            case "west_connected" -> Direction.WEST;
            case "up_connected" -> Direction.UP;
            case "down_connected" -> Direction.DOWN;
            default -> throw new Exception("ModScience encountered and Error, attempted to pass a BooleanProperty that is not a direction");
        };
    }
    public static EnumProperty<Side> getSideFromDirection(Direction direction) {
        return switch (direction) {
            case UP -> SIDE_UP;
            case DOWN -> SIDE_DOWN;
            case WEST -> SIDE_WEST;
            case EAST -> SIDE_EAST;
            case NORTH -> SIDE_NORTH;
            case SOUTH -> SIDE_SOUTH;
        };
    }
}
