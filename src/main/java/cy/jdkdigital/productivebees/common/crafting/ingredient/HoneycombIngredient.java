/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cy.jdkdigital.productivebees.common.crafting.ingredient;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cy.jdkdigital.productivebees.ProductiveBees;
import cy.jdkdigital.productivebees.init.ModDataComponents;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

import java.util.Arrays;
import java.util.stream.Stream;

public class HoneycombIngredient extends DataComponentIngredient
{
    public static final MapCodec<HoneycombIngredient> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(
                            HolderSetCodec.create(Registries.ITEM, BuiltInRegistries.ITEM.holderByNameCodec(), false).fieldOf("items").forGetter(HoneycombIngredient::items),
                            DataComponentPredicate.CODEC.fieldOf("components").forGetter(HoneycombIngredient::components)
                    )
                    .apply(builder, HoneycombIngredient::new));


    public HoneycombIngredient(HolderSet<Item> items, DataComponentPredicate components) {
        super(items, components, false);
    }

    @Override
    public IngredientType<?> getType() {
        return ProductiveBees.HONEYCOMB_INGREDIENT_TYPE.get();
    }

    public static Ingredient of(ItemStack stack) {
        var predicate = DataComponentPredicate.allOf(DataComponentMap.builder().set(ModDataComponents.BEE_TYPE, stack.get(ModDataComponents.BEE_TYPE)).build());
        return of(predicate, stack.getItem());
    }

    public static Ingredient of(DataComponentPredicate predicate, ItemLike... items) {
        return of(predicate, HolderSet.direct(Arrays.stream(items).map(ItemLike::asItem).map(Item::builtInRegistryHolder).toList()));
    }

    public static Ingredient of(DataComponentPredicate predicate, HolderSet<Item> items) {
        return new HoneycombIngredient(items, predicate).toVanilla();
    }
}
