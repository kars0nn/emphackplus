package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket.ReceivePacket;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket.SendPacket;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPlayerTravel;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumHand;

public class BoatFly extends WurstplusHack {
	private WurstplusPlayerUtil util = new WurstplusPlayerUtil();
	private int ticks;
	
	public BoatFly() {
		super(WurstplusCategory.WURSTPLUS_MOVEMENT);

		name        = "BoatFly";
		tag         = "BoatFly";
		description = "fly with entities";
	}

	WurstplusSetting hSpeed = create("HSpeed", "HSpeed", 1F, 0.1F, 10F);
	WurstplusSetting vSpeed = create("VSpeed", "VSpeed", 1F, 0.1F, 10F);
	WurstplusSetting tickDelay = create("TickDelay", "TickDelay", 1, 0, 20);
	WurstplusSetting fixYaw = create("FixYaw", "FixYaw", true);
	WurstplusSetting bypass = create("Bypass", "Bypass", false);
	
	@EventHandler
	private Listener<SendPacket> onSendPacket = new Listener<>(event -> {
		if(mc.player == null || !mc.player.isRiding() || !bypass.get_value(true))return;
		
		if(event.get_packet() instanceof CPacketVehicleMove) {
			if(ticks++ >= tickDelay.get_value(1) + 1) {
				mc.player.connection.sendPacket(new CPacketUseEntity(mc.player.getRidingEntity(), EnumHand.MAIN_HAND));
				ticks = 0;
				return;
			}
		}
		
		if(event.get_packet() instanceof CPacketPlayer.Rotation || event.get_packet() instanceof CPacketInput)event.cancel();
	});
	
	@EventHandler
	private Listener<ReceivePacket> onReceivePacket = new Listener<>(event -> {
		if(mc.player == null || !mc.player.isRiding() || !bypass.get_value(true))return;
		
		/* cancelling SPacketPlayerPosLook fixes camera glitching */
		if(event.get_packet() instanceof SPacketMoveVehicle || event.get_packet() instanceof SPacketPlayerPosLook)event.cancel();
	});
	
	@EventHandler
	private Listener<WurstplusEventPlayerTravel> onTravel = new Listener<>(event -> {
		if(mc.player == null || !mc.player.isRiding())return;
		
		Entity e = mc.player.getRidingEntity();
		e.setNoGravity(true);
		
		if(fixYaw.get_value(true))e.rotationYaw = mc.player.rotationYaw;
		
		if(mc.player.movementInput.moveForward != 0 || mc.player.movementInput.moveStrafe != 0)util.setBoatSpeed(hSpeed.get_value(1D), e);
		e.motionY = mc.player.movementInput.sneak ? -vSpeed.get_value(1F) : mc.player.ticksExisted % 2 != 0 ? -0.04 : mc.player.movementInput.jump ? vSpeed.get_value(1F) : 0.04;
		
		event.cancel();
	});
}