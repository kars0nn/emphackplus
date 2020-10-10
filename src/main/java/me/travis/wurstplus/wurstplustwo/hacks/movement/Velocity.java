package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusModuleManager;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends WurstplusHack {

    public Velocity() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

        this.name        = "Velocity";
        this.tag         = "Velocity";
        this.description = "limits knockback";
    }

    public void onEnable(){
        WurstplusEventBus.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        WurstplusEventBus.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> receiveListener = new Listener<>(event -> {
        if(event.get_packet() instanceof SPacketEntityVelocity){
            if(((SPacketEntityVelocity) event.get_packet()).getEntityID() == mc.player.getEntityId())
                event.cancel();
        }
        if(event.get_packet() instanceof SPacketExplosion)
            event.cancel();
    });
}
