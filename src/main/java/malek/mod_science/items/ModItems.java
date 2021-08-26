package malek.mod_science.items;

import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.event.ItemEntityTickEvent;
import malek.mod_science.items.armor.StridingBoots;
import malek.mod_science.items.item_nbt.ChargeableItem;
import malek.mod_science.items.orbs_of_power.MoltenCore;
import malek.mod_science.items.orbs_of_power.TempItem;
import malek.mod_science.items.orbs_of_power.ThermophilicPowerOrb;
import malek.mod_science.items.ore_magnet.OreMagnet;
import malek.mod_science.items.tools.IronShoeaxel;
import malek.mod_science.items.tools.ShearForce;
import malek.mod_science.tags.ModScienceTags;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.dimdev.matrix.Matrix;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;

import static malek.mod_science.ModScience.MOD_ID;

@Registrar(element = Item.class, modid = MOD_ID)
public final class ModItems {

    public static final ItemGroup MOD_SCIENCE = FabricItemGroupBuilder.create(new Identifier("modscience", "mod_science")).icon(() -> new ItemStack(Items.GLOW_ITEM_FRAME)).build();

    public static ArmorMaterial armorMaterial = new StridingBoots();

    public static final FabricItemSettings DEFAULT = new FabricItemSettings().group(MOD_SCIENCE);

    @RegistryEntry("golem_repair_kit")
    public static final Item GOLEM_REPAIR_KIT = new GolemRepairKit(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("scythe")
    public static final Item TEST_SCYTHE = new ScytheItem(ToolMaterials.IRON, 1, -2.8F, new FabricItemSettings().group(MOD_SCIENCE));


    //NO!!! NO!!!!!!!! WHAT MAKES YOU THINK THIS IS A GOOD IDEA!!!!! ALWAYS, AND I MEAN ALWAYS!!!!!!!!!!!!!!!!!! PUT THE ITEMS INTO FabricItemSettings.group(MOD_SCIENCE)!!!!!
    // IT TAKES .5 SECONDS GOOD LORD!!!! CLEAN UP YOUR DAMN CODE!!!!!!!!!!!!!!
    // I will do it this time but the next I... I dont know what I will do but it will be painful. PLEASE
    @RegistryEntry("void_dust")
    public static final Item VOID_DUST = new Item(new FabricItemSettings().group(MOD_SCIENCE));
    
    // aember items start
    @RegistryEntry("aember")
    public static final Item AEMBER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("blue_aember")
    public static final Item BLUE_AEMBER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("golden_aember")
    public static final Item GOLDEN_AEMBER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("jet_black_aember")
    public static final Item JET_BLACK_AEMBER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("white_aember")
    public static final Item WHITE_AEMBER = new Item(new FabricItemSettings().group(MOD_SCIENCE));
    // aember items end
    
    @RegistryEntry("shear_force")
    public static final Item SHEAR_FORCE = new ShearForce(new FabricItemSettings().group(MOD_SCIENCE));

    //start metal crafting items
    @RegistryEntry("amalgametal")
    public static final Item AMALGAMETAL = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("singulite_ingot")
    public static final Item SINGULITE_INGOT = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("ethaerial_ingot")
    public static final Item ETHAREAIL_INGOT = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("ethaerium_rod")
    public static final Item ETHAERIUM_ROD = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("beathril_bar")
    public static final Item BEATHRIL_BAR = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("darkwyn_ingot")
    public static final Item DARKWYN_INGOT = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("octiron_ingot")
    public static final Item OCTIRON_INGOT = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("moonsilver")
    public static final Item MOONSILVER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("impervium_chunk")
    public static final Item IMPERVIUM_CHUNK = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("admintium_ingot")
    public static final Item ADMINTIUM_INGOT = new Item(new FabricItemSettings().group(MOD_SCIENCE));
    //end metal crafting items

    @RegistryEntry("gravitic_nucleus")
    public static final Item GRAVITIC_NUCLEUS = new Item(new FabricItemSettings().maxCount(1).group(MOD_SCIENCE));

    @RegistryEntry("molten_core")
    public static final Item MOLTEN_CORE = new MoltenCore(new FabricItemSettings().group(MOD_SCIENCE).fireproof());

    @RegistryEntry("ender_star")
    public static final Item ENDER_STAR = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("thermophilic_power_orb")
    public static final Item THERMOPHILIC_POWER_ORB = new ThermophilicPowerOrb(new FabricItemSettings().group(MOD_SCIENCE).fireproof());

    @RegistryEntry("calamity_star")
    public static final Item CALAMITY_STAR = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("leyden_jar")
    public static final Item LEYDEN_JAR = new TempItem(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("heart_star")
    public static final Item HEART_STAR = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("void_star")
    public static final Item VOID_STAR = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("sap_capsule")
    public static final Item SAP_CAPSULE = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("keystone")
    public static final Item KEYSTONE = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("mass_hammer")
    public static final Item MASS_HAMMER = new MassHammer(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("ore_magnet")
    public static final Item ORE_MAGNET = new OreMagnet(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("default_ore_magnet")
    public static final Item DEFAULT_ORE_MAGNET = new OreMagnet(new FabricItemSettings());

    @RegistryEntry("boots_of_striding")
    public static final Item BOOTS_OF_STRIDING = new ArmorItem(armorMaterial, EquipmentSlot.FEET, new FabricItemSettings().group(MOD_SCIENCE));
    //.maxDamage(100) is the durability of the item
    @RegistryEntry("quicksilver_timepiece")
    public static final Item QUICKSILVER_TIMEPIECE = new QuicksilverTimepiece(new FabricItemSettings().maxDamage(100).group(MOD_SCIENCE));

    @RegistryEntry("iron_shoeaxel")
    public static final Item IRON_SHOEAXEL = new IronShoeaxel(ToolMaterials.IRON, 1, -2.8f, DEFAULT);

    @RegistryEntry("glimmerroot")
    public static final Item GLIMMERROOT = new AliasedBlockItem(ModBlocks.GLIMMERROOT, new FabricItemSettings().group(MOD_SCIENCE).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1200, 0), 1.0F).build()));

    @RegistryEntry("clockwork")
    public static final Item CLOCKWORK = new ClockworkItem(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("base_slime")
    public static final Item BASE_SLIME = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("bits")
    public static final Item BITS = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("cats_eye_emerald")
    public static final Item CATS_EYE_EMERALD = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("chrystalis")
    public static final Item CHRYSTALIS = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("conflagratorite_shard")
    public static final Item CONFLAGRATORITE_SHARD = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("cryostasili_wisp")
    public static final Item CRYOSTASILI = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("ducttape")
    public static final Item DUCTTAPE = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("exotic_materials")
    public static final Item EXOTIC_MATERIALS = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("freyl")
    public static final Item FREYL = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("glint")
    public static final Item GLINT = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("levicite")
    public static final Item LEVICITE = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("liminated_paper")
    public static final Item LIMINATED_PAPER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("lump_of_galgador")
    public static final Item LUMP_OF_GALGADOR = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("lump_of_zamonium")
    public static final Item LUMP_OF_ZAMONIUM = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("mundane_powder")
    public static final Item MUNDANE_POWDER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("neuchronium")
    public static final Item NEUCHRONIUM = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("orb_of_potential")
    public static final Item ORB_OF_POTENTIAL = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("rubber_chunk")
    public static final Item RUBBER_CHUNK = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("scissors")
    public static final Item SCISSORS = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("shadow")
    public static final Item SHADOW = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("vulgar_quartz")
    public static final Item VULGAR_QUARTZ = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("fractal")
    public static final Item FRACTAL = new Item(DEFAULT);

    private static void registerEvents() {
        ItemEntityTickEvent.EVENT.register(itemEntity -> {
            if (itemEntity.getStack().isOf(MOLTEN_CORE)
                && !ChargeableItem.isCharged(itemEntity.getStack())
                && itemEntity.getEntityWorld().getBlockState(itemEntity.getBlockPos()).getBlock() == Blocks.LAVA) {
                    ChargeableItem.setCharged(itemEntity.getStack(), true);
                    itemEntity.getEntityWorld().setBlockState(itemEntity.getBlockPos(), Blocks.AIR.getDefaultState(), 3);
            }
            return ActionResult.PASS;
        });
        ItemEntityTickEvent.EVENT.register(itemEntity -> {
            if (itemEntity.getStack().isIn(ModScienceTags.BURNS_IN_SUNLIGHT)) {
                if (itemEntity.getEntityWorld().isSkyVisible(itemEntity.getBlockPos())) {
                    if (!itemEntity.isOnFire()) itemEntity.setOnFireFor(3);
                } else if (itemEntity.isOnFire()) {
                    itemEntity.setOnFire(false);
                }
            }
            return ActionResult.PASS;
        });
    }

    public static void init() {
        Matrix.register(ModItems.class, Registry.ITEM);
        registerEvents();
    }

}

