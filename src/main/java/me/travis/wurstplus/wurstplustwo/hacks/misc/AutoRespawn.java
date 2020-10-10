package me.travis.wurstplus.wurstplustwo.hacks.misc;


import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventGUIScreen;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.gui.GuiGameOver;

public class AutoRespawn extends WurstplusHack {

    public AutoRespawn() {
        super(WurstplusCategory.WURSTPLUS_MISC);

        this.name = "AutoRespawn";
        this.tag = "AutoRespawn";
        this.description = "AutoRespawn";
    }

    WurstplusSetting coords = create("DeathCoords", "AutoRespawnDeathCoords", true);


    @EventHandler
    private Listener<WurstplusEventGUIScreen> listener = new Listener<>(event -> {
        if(event.get_guiscreen() instanceof GuiGameOver) {
            if(coords.get_value(true))
                WurstplusMessageUtil.send_client_message(String.format("You died at x%d y%d z%d", (int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ));
            if(mc.player != null)
                mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    });

    public void enable(){
        WurstplusEventBus.EVENT_BUS.subscribe(this);
    }

    public void disable(){
        WurstplusEventBus.EVENT_BUS.unsubscribe(this);
    }
}

