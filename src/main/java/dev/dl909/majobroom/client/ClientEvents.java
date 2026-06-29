package dev.dl909.majobroom.client;

import dev.dl909.majobroom.MajoBroom;
import dev.dl909.majobroom.client.input.KeyBindings;
import dev.dl909.majobroom.client.renderer.entity.BroomGeoRenderer;
import dev.dl909.majobroom.client.tooltip.TooltipModifier;
import dev.dl909.majobroom.init.ModEntities;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

/**
 * 客户端事件处理
 * 注册实体渲染器、按键绑定和 Tooltip
 */
@EventBusSubscriber(modid = MajoBroom.MODID, value = Dist.CLIENT)
public final class ClientEvents {
    private ClientEvents() {
    }

    /**
     * 注册实体渲染器
     */
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BROOM.get(), BroomGeoRenderer::new);
    }

    /**
     * 注册按键映射
     */
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        KeyBindings.register(event);
    }

    /**
     * Tooltip 事件监听器
     * 在游戏事件总线上监听
     */
    @EventBusSubscriber(modid = MajoBroom.MODID, value = Dist.CLIENT)
    public static class GameEventHandler {

        /**
         * 添加物品 Tooltip
         */
        @SubscribeEvent
        public static void onItemTooltip(ItemTooltipEvent event) {
            if (event.getEntity() == null) {
                return;
            }

            Item item = event.getItemStack().getItem();
            TooltipModifier modifier = TooltipModifier.get(item);
            if (modifier != null && modifier != TooltipModifier.EMPTY) {
                modifier.modify(event);
            }
        }
    }
}