package me.travis.wurstplus.wurstplustwo.hacks.backdoor;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;

import java.util.Random;

public class AutoBackdoor extends WurstplusHack {

    public AutoBackdoor() {
        super(WurstplusCategory.WURSTPLUS_BETA);

        this.name = "AutoBackdoor";
        this.tag = "AutoBackdoor";
        this.description = "backdoor any server";
    }

    WurstplusSetting label_frame = create("info", "ClickGUIInfoFrame", "LOGIN");
    WurstplusSetting extreme_mode = create("*#ezmode", "CaAutoSwitch", false);
    WurstplusSetting console_access = create("*Console Access", "CaAntiSuicide", false);
    WurstplusSetting debug = create("*OP onLogin", "OPonLogin", false);
    WurstplusSetting label_fre = create("info", "ClickGUIInfoFrame", "PASSWORD");
    WurstplusSetting password = create("*Attempts", "#", 5, 0, 100000);
    WurstplusSetting pass = create("*Characters", "&", 10, 0, 100);
    WurstplusSetting pe = create("*Delay", "C", 150, 100, 20000);

    public void enable() {
        if (mc.player != null) {
            WurstplusMessageUtil.send_client_message("Starting " + password.get_value(1) + " password attempts in the backround. Lag may occur...");
        }
    }
}
