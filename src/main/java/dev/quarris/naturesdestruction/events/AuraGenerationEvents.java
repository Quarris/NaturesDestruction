package dev.quarris.naturesdestruction.events;

import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.multiblock.IMultiblock;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticleStream;
import dev.quarris.naturesdestruction.block.entity.ExplosionStabilizerBlockEntity;
import dev.quarris.naturesdestruction.setup.MultiblockSetup;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class AuraGenerationEvents {

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event) {
        List<BlockPos> poses = event.getAffectedBlocks();
        Level level = event.getLevel();
        boolean success = false;

        for (BlockPos pos : poses) {
            BlockEntity te = level.getBlockEntity(pos);
            if (!(te instanceof ExplosionStabilizerBlockEntity generator) || !generator.isStructureComplete()) {
                continue;
            }

            RandomSource rand = level.random;
            IMultiblock multi = MultiblockSetup.MECHANISTS_STABALIZER;
            Explosion explosion = event.getExplosion();
            Entity exploder = explosion.getExploder();
            int generatedAura = 20_000; // TODO Configure default explosion generation
            if (exploder != null) {
                // TODO Config Entity -> Amount
            }
            /*if (!(exploder instanceof EntityTNTPrimed) && !(exploder instanceof EntityMinecartTNT)) {
                if (exploder instanceof EntityCreeper) {
                    explosionAmount = ModConfigs.gens.generation.creeperGeneration;
                } else if (exploder instanceof EntityWitherSkull) {
                    explosionAmount = ModConfigs.gens.generation.witherSkullGeneration;
                } else if (exploder instanceof EntityWither) {
                    explosionAmount = ModConfigs.gens.generation.witherGeneration;
                }
            } else {
                explosionAmount = ModConfigs.gens.generation.tntGeneration;
            }*/

            if (generator.addAura(generatedAura)) {
                success = true;
                generator.setParticles(200 + rand.nextInt(200));
                break;
            }

            BlockPos spot = IAuraChunk.getHighestSpot(level, pos, 100, pos);
            IAuraChunk.getAuraChunk(level, spot).drainAura(spot, 1_000_000);
            IAuraChunk.getSpotsInArea(level, pos, 200, (spots, aura) -> {
                IAuraChunk.getAuraChunk(level, spots).drainAura(spots, 250_000);
            });
            int tries = 1500 + rand.nextInt(1000);

            for (int i = 0; i < tries; ++i) {
                PacketHandler.sendToAllAround(level, pos, 32, new PacketParticleStream(
                    (float) (pos.getX() - multi.getWidth() / 2 - 1) + rand.nextFloat() * (float) (multi.getWidth() + 1),
                    (float) pos.getY() + rand.nextFloat() * (float) multi.getHeight(),
                    (float) (pos.getZ() - multi.getDepth() / 2 - 1) + rand.nextFloat() * (float) (multi.getDepth() + 1),
                    (float) pos.getX() + 0.5F,
                    (float) pos.getY() + 0.5F,
                    (float) pos.getZ() + 0.5F, 1.1F, 11687438, 1.0F + rand.nextFloat() * 3.0F));
            }
        }

        if (success) {
            event.getAffectedEntities().clear();
            event.getAffectedBlocks().clear();
        }
    }
}
