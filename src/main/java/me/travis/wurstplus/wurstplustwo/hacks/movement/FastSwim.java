package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;

public class FastSwim extends WurstplusHack {
	private WurstplusPlayerUtil util = new WurstplusPlayerUtil();
	private int divider = 5;
	
	public FastSwim() {
		super(WurstplusCategory.WURSTPLUS_MOVEMENT);

		name= "FastSwim";
		tag = "FastSwim";
		description = "swimming go brrrrrrrrr";
	}

	WurstplusSetting up = create("Up", "Up", true);
	WurstplusSetting down = create("Down", "Down", true);
	WurstplusSetting water = create("Water", "Water", true);
	WurstplusSetting lava = create("lava", "lava", true);
	
	public void update() {
		if(mc.player.isInWater() || mc.player.isInLava()) {
			if(mc.player.movementInput.jump && up.get_value(true)) {
				mc.player.motionY = .0725 / divider;
			}
		}
		if(mc.player.isInWater() && water.get_value(true)) {
			if(mc.player.movementInput.moveForward != 0 || mc.player.movementInput.moveStrafe != 0)util.addSpeed(0.2 / 10);
		}
		if(mc.player.isInLava() && lava.get_value(true) && !mc.player.onGround) {
			if(mc.player.movementInput.moveForward != 0 || mc.player.movementInput.moveStrafe != 0)util.addSpeed(0.7 / 10);
		}

		if(mc.player.isInWater() && down.get_value(true) && !mc.player.onGround) {
			if(mc.player.movementInput.sneak) {
				int divider2 = divider * -1;
				mc.player.motionY = 2.2 / divider2;
			}
		}
		if(mc.player.isInLava() && down.get_value(true)) {
			if(mc.player.movementInput.sneak) {
				int divider2 = divider * -1;
				mc.player.motionY = .91 / divider2;
			}
		}
	}
}