package quaternary.proteusutil;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import quaternary.proteusutil.config.ModConfig;
import quaternary.proteusutil.world.WorldgenHandler;

@Mod(modid = ProteusUtil.MODID, name = ProteusUtil.NAME, version = ProteusUtil.VERSION)
public class ProteusUtil {
	public static final String MODID = "proteusutil";
	public static final String NAME = "Proteus Util";
	public static final String VERSION = "GRADLE:VERSION";
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent e) {
		ModConfig.preinit(e);
		
		MinecraftForge.TERRAIN_GEN_BUS.register(WorldgenHandler.class);
		MinecraftForge.ORE_GEN_BUS.register(WorldgenHandler.class);
	}
	
	@Mod.EventHandler
	public static void serverAboutToStart(FMLServerAboutToStartEvent e) {
		//No nether and end for u
		if(ModConfig.NUKE_OTHER_DIMS) {
			
			for(DimensionType t : DimensionType.values()) {
				for(int id : DimensionManager.getDimensions(t)) {
					if(id != 0) {
						DimensionManager.unregisterDimension(id);
					}
				}
			}
			
		}
	}
	
	@Mod.EventHandler
	public static void serverStarting(FMLServerStartingEvent e) {
		MinecraftServer server = e.getServer();
		//commands here (emergency commands, lol)
		
		//general settings
		if(ModConfig.SPAWN_STUFF) {
			server.setGameType(GameType.ADVENTURE);
			server.setCanSpawnNPCs(false);
			server.setDifficultyForAllWorlds(EnumDifficulty.PEACEFUL);
			
			World world = server.getEntityWorld(); //overworld
			
			//find the ocean
			BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(0, world.getSeaLevel(), 0);
			do {
				world.getBlockState(pos); //loads the chunk
				pos.move(EnumFacing.NORTH, 3);
			} while(!BiomeManager.oceanBiomes.contains(world.getBiome(pos)));
			
			pos.move(EnumFacing.NORTH, 10); //out a bit more...
			world.getBlockState(pos); //loads the chunk
			
			//set the spawn there
			world.setSpawnPoint(world.getHeight(pos));
		}
	}
}
