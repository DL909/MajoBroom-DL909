package dev.dl909.majobroom;

import dev.dl909.majobroom.item.armor.MajoBootsItem;
import dev.dl909.majobroom.item.armor.MajoClothItem;
import dev.dl909.majobroom.item.armor.MajoHatItem;
import dev.dl909.majobroom.item.armor.MajoStockingItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public final class Utils {
    private Utils() { // 私有构造，防止实例化
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean wearFullMajoArmor(LivingEntity livingEntity) {
        var list = new ArrayList<ItemStack>();
        livingEntity.getArmorSlots().forEach(list::add);
        return list.get(0).getItem() instanceof MajoBootsItem
                && list.get(1).getItem() instanceof MajoStockingItem
                && list.get(2).getItem() instanceof MajoClothItem
                && list.get(3).getItem() instanceof MajoHatItem;
    }

    public static boolean wearFullMajoArmor(Entity entity){
        return entity instanceof LivingEntity livingEntity && wearFullMajoArmor(livingEntity);
    }
}