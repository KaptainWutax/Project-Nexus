package kaptainwutax.nexus.init;

import kaptainwutax.nexus.path.agent.*;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.util.math.Direction.*;

public class Agents {

    public static List<Agent> AGENTS = new ArrayList<>();

    public static Agent LINEAR_NORTH = new AgentLinear(NORTH);
    public static Agent LINEAR_SOUTH = new AgentLinear(SOUTH);
    public static Agent LINEAR_EAST = new AgentLinear(EAST);
    public static Agent LINEAR_WEST = new AgentLinear(WEST);

    public static Agent DIAGONAL_NORTH_EAST = new AgentDiagonal(NORTH, EAST);
    public static Agent DIAGONAL_NORTH_WEST = new AgentDiagonal(NORTH, WEST);
    public static Agent DIAGONAL_SOUTH_EAST = new AgentDiagonal(SOUTH, EAST);
    public static Agent DIAGONAL_SOUTH_WEST = new AgentDiagonal(SOUTH, WEST);

    public static Agent STEP_NORTH = new AgentStep(NORTH);
    public static Agent STEP_SOUTH = new AgentStep(SOUTH);
    public static Agent STEP_EAST = new AgentStep(EAST);
    public static Agent STEP_WEST = new AgentStep(WEST);

    public static Agent FALL_NORTH = new AgentFall(NORTH);
    public static Agent FALL_SOUTH = new AgentFall(SOUTH);
    public static Agent FALL_EAST = new AgentFall(EAST);
    public static Agent FALL_WEST = new AgentFall(WEST);

    public static Agent PLACE = new AgentPlace();

    static {
        AGENTS.add(LINEAR_NORTH);
        AGENTS.add(LINEAR_SOUTH);
        AGENTS.add(LINEAR_EAST);
        AGENTS.add(LINEAR_WEST);

        AGENTS.add(DIAGONAL_NORTH_EAST);
        AGENTS.add(DIAGONAL_NORTH_WEST);
        AGENTS.add(DIAGONAL_SOUTH_EAST);
        AGENTS.add(DIAGONAL_SOUTH_WEST);

        AGENTS.add(STEP_NORTH);
        AGENTS.add(STEP_SOUTH);
        AGENTS.add(STEP_EAST);
        AGENTS.add(STEP_WEST);

        AGENTS.add(FALL_NORTH);
        AGENTS.add(FALL_SOUTH);
        AGENTS.add(FALL_EAST);
        AGENTS.add(FALL_WEST);

        AGENTS.add(PLACE);
    }

}
