package dev.dl909.majobroom.init;

import dev.dl909.majobroom.MajoBroom;
import dev.dl909.majobroom.config.ServerConfig;
import dev.dl909.majobroom.item.BroomItem;
import dev.dl909.majobroom.item.armor.MajoHatItem;
import dev.dl909.majobroom.item.armor.MajoClothItem;
import dev.dl909.majobroom.item.armor.MajoStockingItem;
import dev.dl909.majobroom.item.armor.MajoBootsItem;
import dev.dl909.majobroom.client.tooltip.ItemDescription;
import dev.dl909.majobroom.client.tooltip.TooltipModifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;


public final class ModItems {
    private ModItems() {}

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MajoBroom.MODID);

    public static final DeferredItem<Item> BROOM = ITEMS.register(
        "broom", () -> new BroomItem(new Item.Properties().stacksTo(1))
    );

    public static final DeferredItem<Item> MAJO_HAT = ITEMS.register(
        "majo_hat", () -> {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

            BuiltInRegistries.ATTRIBUTE
                .getHolder(ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "spell_resist"))
                .ifPresent(attr -> builder.add(
                        attr,
                        new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(MajoBroom.MODID, "majo_boots_spell_resist"),
                            ServerConfig.armorOverpower ? 0.5 : 0.1,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        ),
                        EquipmentSlotGroup.HEAD
                    )
                );

            BuiltInRegistries.ATTRIBUTE
                .getHolder(ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "spell_power"))
                .ifPresent(attr -> builder.add(
                        attr,
                        new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(MajoBroom.MODID, "majo_boots_spell_power"),
                            ServerConfig.armorOverpower ? 2.0 : 0.1,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        ),
                        EquipmentSlotGroup.HEAD
                    )
                );
                
            BuiltInRegistries.ATTRIBUTE
                .getHolder(ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "cooldown_reduction"))
                .ifPresent(attr -> builder.add(
                        attr,
                        new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(MajoBroom.MODID, "majo_boots_cooldown_reduction"),
                            ServerConfig.armorOverpower ? 0.8 : 0.1,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        ),
                        EquipmentSlotGroup.HEAD
                    )
                );
                
            BuiltInRegistries.ATTRIBUTE
                .getHolder(ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "mana_regen"))
                .ifPresent(attr -> builder.add(
                        attr,
                        new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(MajoBroom.MODID, "majo_boots_mana_regen"),
                            ServerConfig.armorOverpower ? 0.5 : 0.05,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        ),
                        EquipmentSlotGroup.HEAD
                    )
                );

            return new MajoHatItem(
                new Item.Properties()
                    .stacksTo(1)
                    .fireResistant()
                    .durability(ArmorItem.Type.HELMET.getDurability(40))
                    .attributes(builder.build())
            );
        }
    );

    public static final DeferredItem<Item> MAJO_CLOTH = ITEMS.register(
        "majo_cloth", () -> new MajoClothItem(
            new Item.Properties()
                .stacksTo(1)
                .fireResistant()
                .durability(ArmorItem.Type.CHESTPLATE.getDurability(40))
        )
    );

    public static final DeferredItem<Item> MAJO_STOCKING = ITEMS.register(
        "majo_stocking", () -> new MajoStockingItem(
            new Item.Properties()
                .stacksTo(1)
                .fireResistant()
                .durability(ArmorItem.Type.LEGGINGS.getDurability(40))
        )
    );

    public static final DeferredItem<Item> MAJO_BOOTS = ITEMS.register(
        "majo_boots", () -> new MajoBootsItem(
            new Item.Properties()
                .stacksTo(1)
                .fireResistant()
                .durability(ArmorItem.Type.BOOTS.getDurability(40))
        )
    );


    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    /**
     * 注册物品的 Tooltip 修饰器
     * 必须在物品注册完成后调用（在客户端设置阶段）
     */
    public static void registerTooltips() {
        // 为扫帚注册 Tooltip
        TooltipModifier.register(BROOM.get(), new ItemDescription.Modifier(BROOM.get()));
    }
}
