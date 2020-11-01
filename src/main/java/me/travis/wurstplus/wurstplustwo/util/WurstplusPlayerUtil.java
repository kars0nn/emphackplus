package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class WurstplusPlayerUtil {
    
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static BlockPos GetLocalPlayerPosFloored() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }
    
    public enum FacingDirection {
        North,
        South,
        East,
        West,
    }

    public static FacingDirection GetFacing() {
        switch (MathHelper.floor((double) (mc.player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7)
        {
            case 0:
            case 1:
                return FacingDirection.South;
            case 2:
            case 3:
                return FacingDirection.West;
            case 4:
            case 5:
                return FacingDirection.North;
            case 6:
            case 7:
                return FacingDirection.East;
        }
        return FacingDirection.North;
    }
    
    public double getMoveYaw() {
    	float strafe = 90 * mc.player.moveStrafing;
    	strafe *= mc.player.moveForward != 0 ? mc.player.moveForward * 0.5 : 1;
    	float yaw = mc.player.rotationYaw - strafe;
    	yaw -= mc.player.moveForward < 0 ? 180 : 0;
    	
    	return Math.toRadians(yaw);
    }
    
    public double getSpeed() {
    	return Math.hypot(mc.player.motionX, mc.player.motionZ);
    }
    
    public void setSpeed(Double speed) {
    	Double yaw = getMoveYaw();
    	mc.player.motionX = -Math.sin(yaw) * speed;
    	mc.player.motionZ = Math.cos(yaw) * speed;
    }
    
    public void setBoatSpeed(Double speed, Entity boat) {
    	Double yaw = getMoveYaw();
    	boat.motionX = -Math.sin(yaw) * speed;
    	boat.motionZ = Math.cos(yaw) * speed;
    }
    
    public void addSpeed(Double speed) {
    	Double yaw = getMoveYaw();
    	mc.player.motionX -= Math.sin(yaw) * speed;
    	mc.player.motionZ += Math.cos(yaw) * speed;
    }
    
    public void setTimer(float speed) {
    	mc.timer.tickLength = 50 / speed;
    }
    
    public void step(float height, double[] offset, boolean flag, float speed) {
    	if(flag)setTimer(speed);
    	for(int i = 0; i < offset.length; i++) {
    		 mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset[i], mc.player.posZ, mc.player.onGround));
    	}
    	mc.player.stepHeight = height;
    }
}