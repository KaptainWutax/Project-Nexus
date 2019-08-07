package kaptainwutax.nexus;

import kaptainwutax.nexus.path.Pathfinding;
import kaptainwutax.nexus.utility.Time;
import net.fabricmc.api.ClientModInitializer;

public class Nexus implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		System.out.println("[Project-Nexus] Hello world!");
	}

	public static void tick() {
		Time.updateTime();
		Pathfinding.tick();
	}

}
