package org.bukkit.craftbukkit.v1_20_R1;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;
import org.bukkit.Art;

import java.util.HashMap;
import java.util.Map;

public class CraftArt {
    private static final Map<Holder<PaintingVariant>, Art> artwork;
    private static final Map<Art, Holder<PaintingVariant>> artworkInverse;

    static {
        Map<Holder<PaintingVariant>, Art> artworkBuilder = new HashMap<>();
        Map<Art, Holder<PaintingVariant>> artworkInverseBuilder = new HashMap<>();
        for (ResourceKey<PaintingVariant> key : BuiltInRegistries.PAINTING_VARIANT.registryKeySet()) {
            Art art = Art.getByName(key.location().getPath());
            if (art == null) {
                continue;
            }
            Holder<PaintingVariant> holder = BuiltInRegistries.PAINTING_VARIANT.getHolderOrThrow(key);
            artworkBuilder.put(holder, art);
            if (!artworkInverseBuilder.containsKey(art) || key.location().getNamespace().equals("minecraft")) {
                artworkInverseBuilder.put(art, holder);
            }
        }

        artwork = ImmutableMap.copyOf(artworkBuilder);
        artworkInverse = ImmutableMap.copyOf(artworkInverseBuilder);
    }

    public static Art NotchToBukkit(Holder<PaintingVariant> art) {
        Art bukkit = artwork.get(art);
        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static Holder<PaintingVariant> BukkitToNotch(Art art) {
        Holder<PaintingVariant> nms = artworkInverse.get(art);
        Preconditions.checkArgument(nms != null);
        return nms;
    }
}
