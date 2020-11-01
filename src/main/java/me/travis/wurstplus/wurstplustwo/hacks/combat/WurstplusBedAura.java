package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;

public class WurstplusBedAura extends WurstplusHack {

    public WurstplusBedAura() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);

        this.name        = "BedBomb";
        this.tag         = "AutoSelfWeb";
        this.description = "places fuckin webs at ur feet";
    }

    WurstplusSetting rotate = create("Rotate", "BedAuraRotate", true);
    WurstplusSetting dimensionCheck = create("DimensionCheck", "BedAuraDimensionCheck", true);
    WurstplusSetting refill = create("RefillHotbar", "BedAuraRefillHotbar", false);
    WurstplusSetting range = create("Range", "BedAuraRange", 4.5, 0, 10);


    boolean moving = false;

    public void update() {
        if(refill.get_value(true)) {
            //search for empty hotbar slots
            int slot = -1;
            for (int i = 0; i < 9; i++) {
                if (mc.player.inventory.getStackInSlot(i) == ItemStack.EMPTY) {
                    slot = i;
                    break;
                }
            }

            if (moving && slot != -1) {
                mc.playerController.windowClick(0, slot + 36, 0, ClickType.PICKUP, mc.player);
                moving = false;
                slot = -1;
            }

            if (slot != -1 && !(mc.currentScreen instanceof GuiContainer) && mc.player.inventory.getItemStack().isEmpty()) {
                //search for beds in inventory
                int t = -1;
                for (int i = 0; i < 45; i++) {
                    if (mc.player.inventory.getStackInSlot(i).getItem() == Items.BED && i >= 9) {
                        t = i;
                        break;
                    }
                }
                //click bed item
                if (t != -1) {
                    mc.playerController.windowClick(0, t, 0, ClickType.PICKUP, mc.player);
                    moving = true;
                }
            }
        }

        mc.world.loadedTileEntityList.stream()
                .filter(e -> e instanceof TileEntityBed)
                .filter(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()) <= range.get_value(1))
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ())))
                .forEach(bed -> {

                    if(dimensionCheck.get_value(true) && mc.player.dimension == 0) return;

                    if(rotate.get_value(true)) WurstplusBlockUtil.faceVectorPacketInstant(new Vec3d(bed.getPos().add(0.5, 0.5, 0.5)));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bed.getPos(), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));

                });
    }
}
