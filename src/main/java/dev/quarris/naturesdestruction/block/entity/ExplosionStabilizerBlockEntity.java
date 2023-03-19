package dev.quarris.naturesdestruction.block.entity;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.multiblock.IMultiblock;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticleStream;
import dev.quarris.naturesdestruction.setup.BlockSetup;
import dev.quarris.naturesdestruction.setup.MultiblockSetup;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExplosionStabilizerBlockEntity extends BlockEntity {

    private int storedAura = 0;
    private int particles;

    public ExplosionStabilizerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockSetup.BlockEntities.EXPLOSION_STABILIZER.get(), pos, state);
    }

    public boolean addAura(int aura) {
        int totalAura = this.storedAura + aura;
        if (totalAura > this.getMaxStoredAura()) {
            this.setAura(this.getMaxStoredAura());
            return false;
        } else {
            this.setAura(totalAura);
            return true;
        }
    }

    public boolean setAura(int aura) {
        if (aura <= this.getMaxStoredAura()) {
            this.storedAura = aura;
            return true;
        } else {
            this.storedAura = this.getMaxStoredAura();
            return false;
        }
    }

    public boolean isStructureComplete() {
        return MultiblockSetup.MECHANISTS_STABALIZER.isComplete(this.getLevel(), this.getBlockPos());
    }

    public int getDissipationRate() {
        return 1 + (int) ((float) (this.getBaseDissipationRate() * this.getSpeedMultiplier()));
    }

    private int getBaseDissipationRate() {
        return 100; // TODO Config
    }

    private int getSpeedMultiplier() {
        if (!this.isStructureComplete()) {
            return 1;
        }

        return this.getUpgradeBlock().is(BlockSetup.CONVERSION_CATALYST.get()) ? 2 : 1; // TODO Config
    }

    private int getCapacityMultiplier() {
        if (!this.isStructureComplete()) {
            return 1;
        }

        return this.getUpgradeBlock().is(BlockSetup.FLUCTUATION_CAPACITOR.get()) ? 5 : 1; // TODO Config
    }

    private int getBaseStorage() {
        return 200_000; // TODO Config
    }

    public int getMaxStoredAura() {
        return this.getBaseStorage() * this.getCapacityMultiplier();
    }

    public BlockState getUpgradeBlock() {
        return this.getLevel().getBlockState(this.getBlockPos().below());
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("StoredAura", this.storedAura);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.storedAura = nbt.getInt("StoredAura");
    }

    public void setParticles(int count) {
        this.particles = count;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ExplosionStabilizerBlockEntity generator) {
        if (generator.storedAura < 0) {
            generator.storedAura = 0;
        }

        IMultiblock multi = MultiblockSetup.MECHANISTS_STABALIZER;
        if (generator.particles > 0) {
            int tries = 5 + level.random.nextInt(10);
            if (tries > generator.particles) {
                tries = generator.particles;
            }

            for (int i = 0; i < tries; ++i) {
                PacketHandler.sendToAllAround(level, pos, 32, new PacketParticleStream(
                    (pos.getX() - multi.getWidth() / 2f - 1) + level.random.nextFloat() * (float) (multi.getWidth() + 1),
                    pos.getY() + (float) multi.getHeight() / 2.0F + level.random.nextFloat() * ((float) multi.getHeight() / 2.0F),
                    (pos.getZ() - multi.getDepth() / 2f - 1) + level.random.nextFloat() * (float) (multi.getDepth() + 1),
                    pos.getX() + 0.5F,
                    pos.getY() + 0.5F,
                    pos.getZ() + 0.5F,
                    0.5F, 0x472307, 1.0F + level.random.nextFloat() * 3.0F));
            }

            generator.particles -= tries;
        }

        if (generator.storedAura > 0) {
            BlockPos spot = NaturesAuraAPI.instance().getLowestAuraDrainSpot(level, pos, 30, pos);
            int dissipated = generator.getDissipationRate();
            if (generator.isStructureComplete() && IAuraChunk.getAuraInArea(level, spot, 30) < 3 * IAuraChunk.DEFAULT_AURA) {
                IAuraChunk.getAuraChunk(level, spot).storeAura(spot, dissipated);
            }

            generator.storedAura -= dissipated;
            level.updateNeighbourForOutputSignal(pos, generator.getBlockState().getBlock());

            // Dissipation Particles
            if (!generator.isStructureComplete()) {
                BlockPos posUp = pos.above();
                float xOff = level.random.nextFloat();
                float yOff = level.random.nextFloat() / 2.0F;
                float zOff = level.random.nextFloat();
                PacketHandler.sendToAllAround(level, posUp, 32, new PacketParticleStream(
                    posUp.getX() + xOff,
                    posUp.getY() + yOff,
                    posUp.getZ() + zOff,
                    posUp.getX() + xOff,
                    posUp.getY() + yOff + 1.0F + level.random.nextFloat(),
                    posUp.getZ() + zOff,
                    0.05F, 0x251A33, 1.0F + level.random.nextFloat() * 3.0F));
            } else {
                BlockPos[] tnts = new BlockPos[]{
                    pos.offset(3, 2, 0),
                    pos.offset(-3, 2, 0),
                    pos.offset(0, 2, 3),
                    pos.offset(0, 2, -3)
                };

                for (BlockPos tntPos : tnts) {
                    BlockPos posUp = tntPos.above();
                    float xOff = level.random.nextFloat();
                    float yOff = level.random.nextFloat() / 2.0F;
                    float zOff = level.random.nextFloat();
                    PacketHandler.sendToAllAround(level, tntPos, 32, new PacketParticleStream(
                        posUp.getX() + xOff,
                        posUp.getY() + yOff,
                        posUp.getZ() + zOff,
                        posUp.getX() + xOff,
                        posUp.getY() + yOff + 1.0F + level.random.nextFloat(),
                        posUp.getZ() + zOff,
                        0.05F, 0xFF2626, 1.0F + level.random.nextFloat() * 3.0F));
                }
            }
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, ExplosionStabilizerBlockEntity generator) {
        if (!generator.isStructureComplete() || level.getGameTime() % 4L != 0L) return;

        RandomSource rand = level.random;
        BlockPos abovePos = pos.above();
        BlockPos[] balancedPoses = new BlockPos[]{
            abovePos.offset(4, 0, 2),
            abovePos.offset(4, 0, -2),
            abovePos.offset(-4, 0, 2),
            abovePos.offset(-4, 0, -2),
            abovePos.offset(2, 0, 4),
            abovePos.offset(2, 0, -4),
            abovePos.offset(-2, 0, 4),
            abovePos.offset(-2, 0, -4)
        };

        for (BlockPos balancedPose : balancedPoses) {
            BlockPos p = balancedPose.above();
            NaturesAuraAPI.instance().spawnMagicParticle(
                p.getX() + rand.nextFloat(), p.getY() + 0.2F, p.getZ() + rand.nextFloat(),
                0.0, 0.0, 0.0, 0xFF7BBEE5,
                rand.nextFloat() * 3.0F + 1.0F, rand.nextInt(25) + 25, -0.05F, false, true);
        }
    }

    public int getStoredAura() {
        return this.storedAura;
    }
}
