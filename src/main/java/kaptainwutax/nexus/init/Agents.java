package kaptainwutax.nexus.init;

import kaptainwutax.nexus.path.agent.Agent;
import kaptainwutax.nexus.path.agent.AgentDiagonal;
import kaptainwutax.nexus.path.agent.AgentLinear;
import kaptainwutax.nexus.path.agent.AgentStep;

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
    }

}
