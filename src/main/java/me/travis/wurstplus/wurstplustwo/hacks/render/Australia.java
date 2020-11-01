package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import org.lwjgl.opengl.GL11;

public class Australia extends WurstplusHack {


    // WIP


    public Australia() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "WIP";
        this.tag = "Australia";
        this.description = "oi ya fuckin cunt";
    }

    public void enable(EntityViewRenderEvent.CameraSetup event) {
        GL11.glScalef(1, -1, 1);
        mc.player.setFire(1);
    }

}
