package dev.quarris.naturesdestruction.setup;

import dev.quarris.naturesdestruction.ModRef;
import dev.quarris.naturesdestruction.block.ExplosionStabilizerBlock;
import dev.quarris.naturesdestruction.block.entity.ExplosionStabilizerBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockSetup {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModRef.ID);

    public static final RegistryObject<Block> EXPLOSION_STABILISER = BLOCKS.register("explosion_stabilizer", () -> new ExplosionStabilizerBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f, 10).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CONVERSION_CATALYST = BLOCKS.register("conversion_catalyst", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f, 10).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> FLUCTUATION_CAPACITOR = BLOCKS.register("fluctuation_capacitor", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f, 10).requiresCorrectToolForDrops()));

    public static void init(IEventBus modBus) {
        BLOCKS.register(modBus);
        BlockEntities.init(modBus);
    }

    public static class BlockEntities {

        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModRef.ID);

        public static final RegistryObject<BlockEntityType<ExplosionStabilizerBlockEntity>> EXPLOSION_STABILIZER = BLOCK_ENTITIES.register(BlockSetup.EXPLOSION_STABILISER.getId().getPath(), () -> BlockEntityType.Builder.of(ExplosionStabilizerBlockEntity::new, BlockSetup.EXPLOSION_STABILISER.get()).build(null));

        public static void init(IEventBus modBus) {
            BLOCK_ENTITIES.register(modBus);
        }

    }
}
