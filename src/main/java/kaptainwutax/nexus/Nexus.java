package kaptainwutax.nexus;

import kaptainwutax.nexus.path.Pathfinding;
import kaptainwutax.nexus.utility.Time;
import kaptainwutax.nexus.world.NavWorld;
import net.fabricmc.api.ClientModInitializer;

public class Nexus implements ClientModInitializer {

	private static Nexus INSTANCE = new Nexus();

	private NavWorld world = new NavWorld();

	public static Nexus getInstance() {
		return INSTANCE;
	}

	@Override
	public void onInitializeClient() {
		System.out.println("[Project-Nexus] Hello world!");
	}

	public void tick() {
		Time.updateTime();
		Pathfinding.tick();
	}

	public NavWorld getWorld() {
		return this.world;
	}

}
