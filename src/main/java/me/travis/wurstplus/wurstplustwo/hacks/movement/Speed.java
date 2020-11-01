package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPlayerJump;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayer;


public class Speed extends WurstplusHack {
<<<<<<< HEAD
	WurstplusPlayerUtil util = new WurstplusPlayerUtil();
	private int jumpTicks = 0;

	public Speed() {
		super(WurstplusCategory.WURSTPLUS_MOVEMENT);

		this.name        = "Speed";
		this.tag         = "Speed";
		this.description = "its like running, but faster";
	}

	WurstplusSetting speed_mode = create("Mode", "StrafeMode", "Strafe", combobox("Strafe", "OnGround"));
	WurstplusSetting auto_jump = create("Auto Jump", "StrafeAutoJump", true);

	@EventHandler
	private Listener<WurstplusEventPlayerJump> on_jump = new Listener<>(event -> {
		event.cancel();
	});
	
	@Override
	public void update() {
		if(mc.player.isElytraFlying() || mc.player.isRiding() || (mc.player.moveForward == 0 && mc.player.moveStrafing == 0))return;
			
		if(speed_mode.in("Strafe")) {
			util.setSpeed(util.getSpeed());
			mc.player.speedInAir = 0.029F;
			util.setTimer(1.09F);
			if(jumpTicks > 0)jumpTicks--;
			
			if(auto_jump.get_value(true) && mc.player.onGround && jumpTicks <= 0) {
				mc.player.motionY = 0.41;
				if(mc.player.isSprinting())util.addSpeed(0.2);
				mc.player.isAirBorne = true;
				jumpTicks = 5;
			}
		}
		
		if(speed_mode.in("OnGround") && mc.player.onGround) {
			util.addSpeed(0.2);
			mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.4, mc.player.posZ, false));
		}
	}
	
	@Override
	public void disable() {
		mc.player.speedInAir = 0.02F;
		util.setTimer(1);
		jumpTicks = 0;
	}
=======
    WurstplusPlayerUtil util = new WurstplusPlayerUtil();
    private int jumpTicks = 0;

    public Speed() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

        this.name        = "Speed";
        this.tag         = "Speed";
        this.description = "its like running, but faster";
    }

    WurstplusSetting speed_mode = create("Mode", "StrafeMode", "Strafe", combobox("Strafe", "OnGround"));
    WurstplusSetting auto_jump = create("Auto Jump", "StrafeAutoJump", true);

    @EventHandler
    private Listener<WurstplusEventPlayerJump> on_jump = new Listener<>(event -> {
        event.cancel();
    });

    @Override
    public void update() {
        if(mc.player.isElytraFlying() || mc.player.isRiding() || (mc.player.moveForward == 0 && mc.player.moveStrafing == 0))return;

        if(speed_mode.in("Strafe")) {
            util.setSpeed(util.getSpeed());
            mc.player.jumpMovementFactor = 0.029F;
            util.setTimer(1.09F);
            if(jumpTicks > 0)jumpTicks--;

            if(auto_jump.get_value(true) && mc.player.onGround && jumpTicks <= 0) {
                mc.player.motionY = 0.41;
                if(mc.player.isSprinting())util.addSpeed(0.2);
                mc.player.isAirBorne = true;
                jumpTicks = 5;
            }
        }

        if(speed_mode.in("OnGround") && mc.player.onGround) {
            util.addSpeed(0.2);
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.4, mc.player.posZ, false));
        }
    }

    @Override
    public void disable() {
        mc.player.jumpMovementFactor = 0.02F;
        util.setTimer(1);
        jumpTicks = 0;
    }
>>>>>>> eaefe69... new modules and shit
}