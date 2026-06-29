package dev.dl909.majobroom.config;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 服务端配置（SERVER类型）
 * - 配置文件存储在: world/serverconfig/majobroom-server.toml
 * - 会自动从服务端同步到客户端
 * - 适用于游戏规则、平衡性等需要统一的设置
 */
public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // 飞行设置
    static {
        BUILDER.comment("Flight Settings").push("flight");
        MAX_SPEED_SPEC = BUILDER
                .comment("Max Horizontal Speed (blocks/tick) (Acceleration also needed to change)")
                .defineInRange("maxSpeed", 0.9D, 0.3D, 2.0D);
        ACCELERATION_SPEC = BUILDER
                .comment("Horizontal Acceleration")
                .defineInRange("acceleration", 0.08D, 0.04D, 0.1D);
        MAX_VERTICAL_SPEED_SPEC = BUILDER
                .comment("Max Vertical Speed (blocks/tick）")
                .defineInRange("maxVerticalSpeed", 0.5D, 0.1D, 2.0D);
        VERTICAL_ACCELERATION_SPEC = BUILDER
                .comment("Vertical Acceleration")
                .defineInRange("verticalAcceleration", 0.05D, 0.001D, 0.5D);
        MOMENTUM_SPEC = BUILDER
                .comment("Momentum (Larger will make broom stop faster)")
                .defineInRange("momentum", 0.92D, 0.5D, 0.99D);
        GROUND_CHECK_OFFSET_SPEC = BUILDER
                .comment("Ground Check Offset (blocks)")
                .defineInRange("groundCheckOffset", 0.02D, 0.0D, 0.1D);
        GROUND_REPULSION_SPEC = BUILDER
                .comment("Ground Repulsion (The force of ground pushing the broom back)")
                .defineInRange("groundRepulsion", 0.005D, 0.0D, 0.1D);
        NO_ARMOR_SPEED_PENALTY_SPEC = BUILDER
                .comment("No Armor Speed Penalty (1.0 means no penalty, 0.6 means speed reduced to 60%)")
                .defineInRange("noArmorSpeedPenalty", 0.38D, 0.1D, 1.0D);
        BUILDER.pop();
        BUILDER.comment("Majo Armor Settings").push("armor");
        ARMOR_OVERPOWER_SPEC = BUILDER
                .comment("Whether to enable Majo armor stat enhancement (including iron's spellbooks attributes)")
                .define("armorOverpower", false);
        ARMOR_IMMORTAL_SPEC = BUILDER
                .comment("Whether to enable Majo armor to not take damage")
                .define("armorImmortal", true);
        ARMOR_BLESS_SPEC = BUILDER
                .comment("Whether to enable armor bless\n" +
                         "(which is a series of effect and enhancement provided to players(and entities) who wear full Majo armor.)")
                .define("armorBless", false);
        BLESS_EFFECT_LIST_SPEC = BUILDER
                .comment("""
                        Effects given to blessed entity
                        Due to some problem, you must set value in pattern like "{namespace}:{effect_name}:{level}"
                        i.e. minecraft:speed:2""")
                .define("blessEffectList", new ArrayList<>(Collections.singleton("majobroom:decrease_damage:3")));
        BLESS_IMMUNE_PROJECTILE = BUILDER
                .comment("Whether a blessed entity is allowed to be immune to projectiles.\n" +
                         " Immune projectiles will pass directly through the entity."
                )
                .define("blessImmuneProjectile", false);
        BLESS_IMMUNE_PROJECTILE_WHITELIST = BUILDER
                .comment("use bless_immune_projectile_list as whitelist")
                .define("blessImmuneProjectileWhitelist", false);
        BLESS_IMMUNE_PROJECTILE_LIST = BUILDER
                .define("blessImmuneProjectileList", new ArrayList<>());

        BUILDER.pop();
    }

    // 配置规范
    public static final ModConfigSpec SPEC = BUILDER.build();

    // ============ ModConfigSpec 值（配置定义） ============
    private static final ModConfigSpec.DoubleValue MAX_SPEED_SPEC;
    private static final ModConfigSpec.DoubleValue ACCELERATION_SPEC;
    private static final ModConfigSpec.DoubleValue MAX_VERTICAL_SPEED_SPEC;
    private static final ModConfigSpec.DoubleValue VERTICAL_ACCELERATION_SPEC;
    private static final ModConfigSpec.DoubleValue MOMENTUM_SPEC;
    private static final ModConfigSpec.DoubleValue GROUND_CHECK_OFFSET_SPEC;
    private static final ModConfigSpec.DoubleValue GROUND_REPULSION_SPEC;
    private static final ModConfigSpec.DoubleValue NO_ARMOR_SPEED_PENALTY_SPEC;
    private static final ModConfigSpec.BooleanValue ARMOR_OVERPOWER_SPEC;
    private static final ModConfigSpec.BooleanValue ARMOR_IMMORTAL_SPEC;
    private static final ModConfigSpec.BooleanValue ARMOR_BLESS_SPEC;
    private static final ModConfigSpec.ConfigValue<List<String>> BLESS_EFFECT_LIST_SPEC;
    private static final ModConfigSpec.BooleanValue BLESS_IMMUNE_PROJECTILE;
    private static final ModConfigSpec.BooleanValue BLESS_IMMUNE_PROJECTILE_WHITELIST;
    private static final ModConfigSpec.ConfigValue<List<String>> BLESS_IMMUNE_PROJECTILE_LIST;


    // ============ 缓存值（实际使用，性能优化） ============
    // 飞行参数
    public static double maxSpeed;
    public static double acceleration;
    public static double maxVerticalSpeed;
    public static double verticalAcceleration;
    public static double momentum;
    public static double groundCheckOffset;
    public static double groundRepulsion;
    public static double noArmorSpeedPenalty;
    public static boolean armorOverpower;
    public static boolean armorImmortal;
    public static boolean armorBless;
    public static List<effect> blessEffectList;
    public static boolean blessImmuneProjectile;
    public static boolean blessImmuneProjectileWhitelist;
    @SuppressWarnings("rawtypes")
    public static List<EntityType> blessImmuneProjectileList;


    /**
     * 将配置值烘焙到缓存变量中（由 ConfigEvents 调用）
     * 这样在运行时使用缓存值，避免每次都调用 .get()
     */
    public static void bake() {
        // 飞行参数
        maxSpeed = MAX_SPEED_SPEC.get();
        acceleration = ACCELERATION_SPEC.get();
        maxVerticalSpeed = MAX_VERTICAL_SPEED_SPEC.get();
        verticalAcceleration = VERTICAL_ACCELERATION_SPEC.get();
        momentum = MOMENTUM_SPEC.get();
        groundCheckOffset = GROUND_CHECK_OFFSET_SPEC.get();
        groundRepulsion = GROUND_REPULSION_SPEC.get();
        noArmorSpeedPenalty = NO_ARMOR_SPEED_PENALTY_SPEC.get();
        // 盔甲参数
        armorOverpower = ARMOR_OVERPOWER_SPEC.get();
        armorImmortal = ARMOR_IMMORTAL_SPEC.get();
        // 盔甲祝福
        armorBless = ARMOR_BLESS_SPEC.get();
        blessEffectList = new ArrayList<>();
        blessImmuneProjectile = BLESS_IMMUNE_PROJECTILE.get();
        blessImmuneProjectileWhitelist = BLESS_IMMUNE_PROJECTILE_WHITELIST.get();
        blessImmuneProjectileList = new ArrayList<>();
        if (armorBless) {
            for (String i : BLESS_EFFECT_LIST_SPEC.get()) {
                var t = i.split(":");
                if (t.length != 3) {
                    LogUtils.getLogger().warn("failed to process blessEffectList part:\"{}\"(not enough part)", i);
                }
                try {
                    var effect = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.fromNamespaceAndPath(t[0], t[1])).orElseThrow();
                    var amplifier = Integer.parseInt(t[2]);
                    assert amplifier > 0;
                    assert amplifier <= 256;
                    blessEffectList.add(new effect(effect, amplifier - 1));
                } catch (Exception e) {
                    LogUtils.getLogger().warn("failed to process blessEffectList part:{}\n{}", i, e.getMessage());
                }
            }
            if (blessImmuneProjectile) {
                for (String i : BLESS_IMMUNE_PROJECTILE_LIST.get()) {
                    var t = i.split(":");
                    if (t.length != 2) {
                        LogUtils.getLogger().warn("failed to process blessImmuneProjectileList part:\"{}\"(not enough part)", i);
                    }
                    try {
                        var entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath(t[0], t[1]));
                        blessImmuneProjectileList.add(entityType);
                    } catch (Exception e) {
                        LogUtils.getLogger().warn("failed to process blessImmuneProjectileList part:{}\n{}", i, e.getMessage());
                    }
                }
            }
        }

    }

    public record effect(Holder<MobEffect> x, int y) {
    }
}
