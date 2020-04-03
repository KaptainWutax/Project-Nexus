package kaptainwutax.nexus.path;

import net.minecraft.client.options.KeyBinding;

import java.util.HashSet;
import java.util.Set;

public class KeyHelper {

	private static Set<KeyBinding> PRESSED_KEYS = new HashSet<>();

	public static boolean setKeyPressed(KeyBinding key, boolean state) {
		KeyBinding.setKeyPressed(key.getDefaultKeyCode(), state);
		if(state)return PRESSED_KEYS.add(key);
		else return PRESSED_KEYS.remove(key);
	}

	public static void unpressAll() {
		for(KeyBinding pressedKey: PRESSED_KEYS) {
			KeyBinding.setKeyPressed(pressedKey.getDefaultKeyCode(), false);
		}

		PRESSED_KEYS.clear();
	}

}
