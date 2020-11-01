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
	WurstplusPlayerUtil util = new WurstplusPlayerUtil();
	
	public BoatFly() {
		super(WurstplusCategory.WURSTPLUS_MOVEMENT);

		name        = "BoatFly";
		tag         = "BoatFly";
		description = "fly with entities";
	}

	WurstplusSetting speed = create("Speed", "Speed", 1F, 0.1F, 10F);
	WurstplusSetting verticalSpeed = create("VerticalSpeed", "VerticalSpeed", 1F, 0.1F, 10F);
	WurstplusSetting glideSpeed = create("GlideSpeed", "GlideSpeed", 1F, 0F, 10F);
	WurstplusSetting fixYaw = create("FixYaw", "FixYaw", true);
	WurstplusSetting gravity = create("Gravity", "Gravity", true);
	WurstplusSetting bypass = create("Bypass", "Bypass", false);
	
	@EventHandler
	private Listener<SendPacket> onSendPacket = new Listener<>(event -> {
		if(mc.player == null)return; //gay crash fix
		if(!mc.player.isRiding() || !bypass.get_value(true))return;
		if(event.get_packet() instanceof CPacketVehicleMove) {
			if(mc.player.ticksExisted % 2 == 0) {
				mc.player.connection.sendPacket(new CPacketUseEntity(mc.player.getRidingEntity(), EnumHand.MAIN_HAND));
				return;
			}
		}
		if(event.get_packet() instanceof CPacketPlayer.Rotation || event.get_packet() instanceof CPacketInput)event.cancel();
	});
	
	@EventHandler
	private Listener<ReceivePacket> onReceivePacket = new Listener<>(event -> {
		if(mc.player == null)return; //gay crash fix
		if(!mc.player.isRiding() || !bypass.get_value(true))return;
		if(event.get_packet() instanceof SPacketMoveVehicle || event.get_packet() instanceof SPacketPlayerPosLook)event.cancel();
	});
	
	@EventHandler
	private Listener<WurstplusEventPlayerTravel> onTravel = new Listener<>(event -> {
		if(mc.player == null)return; //gay crash fix
		if(!mc.player.isRiding())return;
		Entity riding = mc.player.getRidingEntity();
		if(fixYaw.get_value(true))riding.rotationYaw = mc.player.rotationYaw;
		riding.setNoGravity(!gravity.get_value(true));
		riding.motionY = -glideSpeed.get_value(1F) / 5000;
		if(mc.player.movementInput.moveForward != 0 || mc.player.movementInput.moveStrafe != 0)util.setBoatSpeed(speed.get_value(1D), riding);
		if(mc.player.movementInput.jump)riding.motionY = verticalSpeed.get_value(1F);
		if(mc.player.movementInput.sneak)riding.motionY = -verticalSpeed.get_value(1F);
		event.cancel();
	});
}