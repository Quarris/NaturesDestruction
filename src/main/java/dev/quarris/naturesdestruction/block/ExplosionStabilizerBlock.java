package dev.quarris.naturesdestruction.block;

import dev.quarris.naturesdestruction.block.entity.ExplosionStabilizerBlockEntity;
import dev.quarris.naturesdestruction.setup.BlockSetup;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ExplosionStabilizerBlock extends BaseEntityBlock {

    public ExplosionStabilizerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState p_60457_) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof ExplosionStabilizerBlockEntity generator)) {
            return super.getAnalogOutputSignal(state, level, pos);
        }

        return Mth.ceil((float)generator.getStoredAura() / (float)generator.getMaxStoredAura() * 15.0F);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ExplosionStabilizerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ?
            createTickerHelper(type, BlockSetup.BlockEntities.EXPLOSION_STABILIZER.get(), ExplosionStabilizerBlockEntity::clientTick) :
            createTickerHelper(type, BlockSetup.BlockEntities.EXPLOSION_STABILIZER.get(), ExplosionStabilizerBlockEntity::serverTick);
    }
}
