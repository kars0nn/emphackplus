package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.Random;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AutoExcuse extends WurstplusHack {

    public AutoExcuse() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name        = "AutoExcuse";
        this.tag         = "AutoExcuse";
        this.description = "tell people why you died";
    }
    int diedTime = 0;

    @Override
    public void update() {
        if (this.diedTime > 0) {
            --this.diedTime;
        }
        if (AutoExcuse.mc.player.isDead) {
            this.diedTime = 500;
        }
        if (!AutoExcuse.mc.player.isDead && this.diedTime > 0) {
            Random rand = new Random();
            int randomNum = rand.nextInt(6) + 1;
            if (randomNum == 1) {
                AutoExcuse.mc.player.sendChatMessage("your ping is so good :(((( why are you targeting me");
            }
            if (randomNum == 2) {
                AutoExcuse.mc.player.sendChatMessage("i was in my inventoryyyyyyy");
            }
            if (randomNum == 3) {
                AutoExcuse.mc.player.sendChatMessage("i was configuring my settings bro im not ez i promise");
            }
            if (randomNum == 4) {
                AutoExcuse.mc.player.sendChatMessage("I was tabbed out of minecraft dude");
            }
            if (randomNum == 5) {
                AutoExcuse.mc.player.sendChatMessage("i was was deSynced :(");
            }
            if (randomNum == 6) {
                AutoExcuse.mc.player.sendChatMessage("youre hacking bro not cool :/");
            }
            this.diedTime = 0;
            return;
        }
    }
}
