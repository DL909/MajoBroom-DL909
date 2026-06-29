package dev.dl909.majobroom.event;

import dev.dl909.majobroom.MajoBroom;
import dev.dl909.majobroom.init.ModEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 伤害降低处理器
 * 受伤时提前结算伤害降低增益的固定减免
 */
@EventBusSubscriber(modid = MajoBroom.MODID)
public class DamageDecreaseHandler {
    @SubscribeEvent
    public static void onLivingEntityDamagedPre(LivingDamageEvent.Pre event) {
        var effectInstance = event.getEntity().getEffect(ModEffects.DECREASE_DAMAGE_EFFECT);
        if (effectInstance != null) {
            event.setNewDamage(Math.max(event.getNewDamage() - (effectInstance.getAmplifier() + 1) * 2, 0));
        }
    }
}
