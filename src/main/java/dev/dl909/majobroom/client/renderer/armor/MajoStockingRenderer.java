package dev.dl909.majobroom.client.renderer.armor;

import dev.dl909.majobroom.MajoBroom;
import dev.dl909.majobroom.item.armor.MajoStockingItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MajoStockingRenderer extends GeoArmorRenderer<MajoStockingItem> {
    public MajoStockingRenderer() {
        super(new DefaultedItemGeoModel<>(
                ResourceLocation.fromNamespaceAndPath(MajoBroom.MODID, "armor/majo_stocking")
        ));
    }
}
