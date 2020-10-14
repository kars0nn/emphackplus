package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusModuleManager;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class IceSpeed extends WurstplusHack {

    public IceSpeed() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

        this.name        = "IceSpeed";
        this.tag         = "IceSpeed";
        this.description = "IceSpeed";
    }

    WurstplusSetting slipperiness = create("Slipperiness", "Slipperiness", 0.2f, 0f, 10f);
    @Override
    public void update() {
        Blocks.ICE.slipperiness = this.slipperiness.get_value(1);
        Blocks.PACKED_ICE.slipperiness = this.slipperiness.get_value(1);
        Blocks.FROSTED_ICE.slipperiness = this.slipperiness.get_value(1);
    }

    @Override
    public void disable() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
    }
}
