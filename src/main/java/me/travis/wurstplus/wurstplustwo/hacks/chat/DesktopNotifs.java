package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

import java.awt.*;

public class DesktopNotifs extends WurstplusHack {

    public DesktopNotifs() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "DesktopNotifs";
        this.tag = "DesktopNotifs";
        this.description = "get notifications on your desktop";
    }

    public static void sendNotification(String message, TrayIcon.MessageType messageType){
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("emphack.png");
        TrayIcon icon = new TrayIcon(image, "Emphack+");
        icon.setImageAutoSize(true);
        icon.setToolTip("Emphack+");
        try { tray.add(icon); }
        catch (AWTException e) { e.printStackTrace(); }
        icon.displayMessage("Emphack+", message, messageType);
    }

}
