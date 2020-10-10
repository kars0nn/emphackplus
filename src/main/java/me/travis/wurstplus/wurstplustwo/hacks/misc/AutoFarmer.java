package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.block.BlockCrops;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AutoFarmer extends WurstplusHack {

    public AutoFarmer() {
        super(WurstplusCategory.WURSTPLUS_MISC);

        this.name = "AutoFarmer";
        this.tag = "AutoFarmer";
        this.description = "i farm u";
    }

    WurstplusSetting range = create("Range", "AutoFarmRange", 4.5, 0, 10);
    WurstplusSetting tickDelay = create("TickDelay", "AutoFarmTickDelay", 5, 0, 100);

    int waitCounter = 0;

    public void update(){
        List<BlockPos> blockPosList = getSphere(getPlayerPos(), (float)range.get_value(1), 1, false, true, 0);
        blockPosList.stream()
                .sorted(Comparator.comparing(b -> mc.player.getDistance(b.getX(), b.getY(), b.getZ())))
                .forEach(blockPos -> {
                    if (mc.world.getBlockState(blockPos).getBlock() instanceof BlockCrops) {
                        if (waitCounter < tickDelay.get_value(1)) {
                            waitCounter++;
                        } else {
                            mc.playerController.clickBlock(blockPos, EnumFacing.UP);
                            mc.player.swingArm(EnumHand.MAIN_HAND);
                            waitCounter = 0;
                        }
                    }
                });
    }

    public void enable(){
        waitCounter = 0;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }
}

