package dev.dl909.majobroom.event;

import dev.dl909.majobroom.MajoBroom;
import dev.dl909.majobroom.Utils;
import dev.dl909.majobroom.config.ServerConfig;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

@EventBusSubscriber(modid = MajoBroom.MODID)
public class ImmuneProjectile {
    @SubscribeEvent
    public static void onProjectileImpactEventFired(ProjectileImpactEvent event) {
        if (ServerConfig.armorBless && ServerConfig.blessImmuneProjectile &&
                event.getRayTraceResult() instanceof EntityHitResult result &&
                Utils.wearFullMajoArmor(result.getEntity())
                ) {
            if (ServerConfig.blessImmuneProjectileWhitelist == ServerConfig.blessImmuneProjectileList.contains(event.getProjectile().getType())
            ){
                event.setCanceled(true);
            }
        }
    }
}
