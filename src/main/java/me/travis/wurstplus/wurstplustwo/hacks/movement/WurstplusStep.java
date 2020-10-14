package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;

public class WurstplusStep extends WurstplusHack {
	WurstplusPlayerUtil util = new WurstplusPlayerUtil();
    int ticks = 0;
	
    public WurstplusStep() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

		this.name        = "Step";
		this.tag         = "Step";
		this.description = "Move up / down block big";
    }

    WurstplusSetting mode = create("Mode", "StepMode", "Normal", combobox("Normal", "Reverse"));
    WurstplusSetting timer = create("Use Timer", "Timer", true);

    @Override
    public void update() {

        if (!mc.player.collidedHorizontally && mode.in("Normal")) return;
        if (!mc.player.onGround || mc.player.isOnLadder() || mc.player.isInWater() || mc.player.isInLava() || mc.player.movementInput.jump || mc.player.noClip) return;
        if (mc.player.moveForward == 0 && mc.player.moveStrafing == 0) return;

        final double n = get_n_normal();
        final boolean t = timer.get_value(true);

        if(mode.in("Normal")) {

            if (n < 0 || n > 2.5) return;

            if(ticks > 0) {
            	ticks--;
            } else {
            	util.setTimer(1);
            }
            
            if(n == 1) {
            	util.step(1, new double[] { 0.42, 0.753 }, t, 0.6F);
            	ticks = 1;
            }
            if(n == 1.5) {
            	util.step(1, new double[] { 0.42, 0.75, 1.0, 1.16, 1.23, 1.2 }, t, 0.35F);
            	ticks = 1;
            }
            if(n == 2.0) {
            	util.step(2, new double[] { 0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43 }, t, 0.25F);
            	ticks = 2;
            }
            if(n == 2.5) {
            	util.step(2, new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907 }, t, 0.15F);
            	ticks = 2;
            }
        }

        if(mode.in("Reverse")) {
            mc.player.motionY = -1;
        }

    }

    public double get_n_normal() {

        mc.player.stepHeight = 0.5f;

        double max_y = -1;

        final AxisAlignedBB grow = mc.player.getEntityBoundingBox().offset(0, 0.05, 0).grow(0.05);

        if (!mc.world.getCollisionBoxes(mc.player, grow.offset(0, 2, 0)).isEmpty()) return 100;

        for (final AxisAlignedBB aabb : mc.world.getCollisionBoxes(mc.player, grow)) {

            if (aabb.maxY > max_y) {
                max_y = aabb.maxY;
            }

        }

        return max_y - mc.player.posY;

    }

    @Override
	public void disable() {
		mc.timer.tickLength = 50F;
		ticks = 0;
	}
}