package dev.quarris.naturesdestruction.setup;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.multiblock.IMultiblock;
import de.ellpeck.naturesaura.api.multiblock.Matcher;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import dev.quarris.naturesdestruction.ModRef;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

public class MultiblockSetup {

    public static final IMultiblock MECHANISTS_STABALIZER = NaturesAuraAPI.instance().createMultiblock(ModRef.res("mechanists_stabilizer"),
        new String[][]{
            {"G       G", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "G       G"},
            {"N       N", "    T    ", "         ", "         ", " T     T ", "         ", "         ", "    T    ", "N       N"},
            {"N C   C N", "    N    ", "C       C", "         ", " N     N ", "         ", "C       C", "    N    ", "N C   C N"},
            {"N L   L N", "    N    ", "L       L", "         ", " N  0  N ", "         ", "L       L", "    N    ", "N L   L N"},
            {" R RRR R ", "RNNR RNNR", " NONNNON ", "RRNONONRR", "R NN NN R", "RRNONONRR", " NONNNON ", "RNNR RNNR", " R RRR R "}
        },
        'G', Matcher.tag(Blocks.GLOWSTONE, TagSetup.Blocks.GLOWSTONE),
        'N', Blocks.NETHER_BRICKS,
        'T', Blocks.TNT,
        'C', Blocks.CLAY,
        'L', ModBlocks.INFUSED_STONE,
        'R', Blocks.RED_NETHER_BRICKS,
        'O', Matcher.tag(Blocks.OBSIDIAN, Tags.Blocks.OBSIDIAN),
        '0', BlockSetup.EXPLOSION_STABILISER.get(),
        ' ', Matcher.any());


    public static void init() {}
}
