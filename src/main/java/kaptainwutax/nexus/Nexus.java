package kaptainwutax.nexus;

import kaptainwutax.nexus.path.PathFinder;
import kaptainwutax.nexus.path.player.BasePlayerPathFinder;
import kaptainwutax.nexus.utility.Time;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class Nexus implements ClientModInitializer {

	private static Nexus INSTANCE = new Nexus();

	public FastWorld world = new FastWorld();
	public BasePlayerPathFinder pathFinder = new BasePlayerPathFinder(this.world);

	public static Nexus getInstance() {
		return INSTANCE;
	}

	@Override
	public void onInitializeClient() {
	}

	public void tick() {
		Time.updateTime();
		this.pathFinder.update();

		if(MinecraftClient.getInstance().player != null) {
			this.world.tick(MinecraftClient.getInstance().player.getBlockPos());
		}
	}

}
