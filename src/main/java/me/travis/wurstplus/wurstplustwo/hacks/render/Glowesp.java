package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;
import java.util.stream.Collectors;

public class Glowesp extends WurstplusHack {

    public Glowesp() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "ChildESP";
        this.tag = "ChildESP";
        this.description = "they're over 18 donut worry";
    }


    WurstplusSetting players = create("Players", "GlowEspPlayers", true);
    WurstplusSetting passive = create("Passive", "GlowEspPassive", false);
    WurstplusSetting monsters = create("Monsters", "GlowEspMonsters", true);
    WurstplusSetting items = create("Items", "GlowEspItems", false);
    WurstplusSetting xpBottles = create("Xp Bottles", "GlowEspCrystals", false);
    WurstplusSetting crystals = create("Crystals", "GlowEspXpBottles", false);

    List<Entity> entities;

    public void update(){
        entities = mc.world.loadedEntityList.stream()
                .filter(e -> e != mc.player)
                .collect(Collectors.toList());
        entities.forEach(e -> {
            if(e instanceof EntityPlayer && players.get_value(true))
                e.setGlowing(true);

            if(isPassive(e) && passive.get_value(true))
                e.setGlowing(true);

            if(e instanceof EntityExpBottle && xpBottles.get_value(true))
                e.setGlowing(true);

            if(isMonster(e) && monsters.get_value(true))
                e.setGlowing(true);

            if(e instanceof EntityItem && items.get_value(true))
                e.setGlowing(true);

            if(e instanceof EntityEnderCrystal && crystals.get_value(true))
                e.setGlowing(true);
        });
    }

    public void disable(){
        entities.forEach(p -> p.setGlowing(false));
    }

    public static boolean isPassive(Entity e) {
        if (e instanceof EntityWolf && ((EntityWolf) e).isAngry()) return false;
        if (e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntityAmbientCreature || e instanceof EntitySquid)
            return true;
        if (e instanceof EntityIronGolem && ((EntityIronGolem) e).getRevengeTarget() == null) return true;
        return false;
    }

    public static boolean isMonster(Entity entity) {
        return entity.isCreatureType(EnumCreatureType.MONSTER, false);
    }

}

