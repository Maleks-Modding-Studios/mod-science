package malek.mod_science.blocks;

import malek.mod_science.blocks.power.*;
import malek.mod_science.blocks.spircle_ore.SpircleOre;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import org.dimdev.matrix.Matrix;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;

import static malek.mod_science.ModScience.MOD_ID;

@Registrar(element = Block.class, modid = MOD_ID)
public final class ModBlocks {
    private static final FabricBlockSettings DEFAULT = FabricBlockSettings.of(Material.STONE).strength(0.3F, 0.3F);
    private static final FabricBlockSettings ORE = FabricBlockSettings.of(Material.STONE).strength(0.3F, 0.3F).breakByTool(FabricToolTags.PICKAXES);

    @RegistryEntry("aember_ore")
    public static final Block AEMBER_ORE = new Block(ORE);

    @RegistryEntry("multiblock_test")
    public static final Block TEST = new BlockMultiblockTest(DEFAULT);

    @RegistryEntry("shadowsilk_stone_ore")
    public static final Block SHADOWSILK_STONE_ORE = new ShadowSilkOre(ORE);

    @RegistryEntry("shadowsilk_deepslate_ore")
    public static final Block SHADOWSILK_DEEPSLATE_ORE = new ShadowSilkOre(ORE);
    
    @RegistryEntry("flourcane")
    public static final Block FLOURCANE_BLOCK = new FlourcaneBlock(FabricBlockSettings.of(Material.PLANT).strength(0.3F,0.5F).noCollision().ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());
    
    @RegistryEntry("spircle_ore")
    public static final Block SPIRCALE_ORE = new SpircleOre(ORE);
    
    @RegistryEntry("potato_ore")
    public static final Block POTATO_ORE = new PotatoOre(FabricBlockSettings.of(Material.SOIL).strength(1, 0.5F).sounds(BlockSoundGroup.GRAVEL).breakByTool(FabricToolTags.SHOVELS, 0));

    @RegistryEntry("matter_cavitation_chamber")
    public static final Block MATTER_CAVITATION_CHAMBER = new MatterCavitationChamberBlock(DEFAULT);

    @RegistryEntry("receiver")
    public static final Block FIRE_POWER_HOLDER = new FireReceiver(DEFAULT);

    @RegistryEntry("steam_pipe")
    public static final Block STEAM_PIPE = new SteamPipe(FabricBlockSettings.of(Material.GLASS).nonOpaque().strength(0.1F, 0.1F));

    @RegistryEntry("generator")
    public static final Block FIRE_POWER_GENERATOR = new FireGenerator(DEFAULT);

    @RegistryEntry("caldera_cauldron")
    public static final Block CALDERA_CAULDRON = new CalderaCauldron(FabricBlockSettings.of(Material.STONE).strength(0.3F, 0.3F).breakByTool(FabricToolTags.PICKAXES).nonOpaque().requiresTool());

    @RegistryEntry("transfusion_matrix")
    public static final Block TRANSFUSION_MATRIX = new TransfusionMatrixBlock(DEFAULT);

    @RegistryEntry("pipe")
    public static final Block PIPE = new PowerPipe(DEFAULT) {
        @Override
        public Efficiency getFireEfficiency() {
            return Efficiency.LOW;
        }

        @Override
        public Efficiency getLightEfficiency() {
            return Efficiency.LOW;
        }

        @Override
        public Efficiency getArcEfficiency() {
            return Efficiency.LOW;
        }

        @Override
        public Efficiency getTimeEfficiency() {
            return Efficiency.LOW;
        }

        @Override
        public Efficiency getFluidEfficiency() {
            return Efficiency.LOW;
        }

        @Override
        public Efficiency getSapEfficiency() {
            return Efficiency.LOW;
        }
    };

    public static void init() {
        Matrix.register(ModBlocks.class, Registry.BLOCK);
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {

    }

    class Never implements AbstractBlock.ContextPredicate {

        public static boolean test2(BlockState state, BlockView blockView, BlockPos pos) {
            return false;
        }
        @Override
        public boolean test(BlockState state, BlockView world, BlockPos pos) {
            return false;
        }
    }
}
