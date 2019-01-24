package quaternary.proteusutil.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.proteusutil.ProteusUtil;

@Mod.EventBusSubscriber(modid = ProteusUtil.MODID)
public class ModConfig {
	public static int CURRENT_CONFIG_VERSION = 1;
	private static Configuration config;
	
	public static boolean NUKE_OTHER_DIMS;
	public static boolean SPAWN_STUFF;
	
	public static boolean CANCEL_ENTITYSPAWN;
	public static int DAYNIGHT_SPEEDUP_FACTOR;
	
	public static boolean DISABLE_SPRINT;
	public static boolean DISABLE_HEALTH;
	
	public static double SLOWDOWN_FACTOR;
	
	//CLIENT
	public static boolean DO_FOG_COLOR;
	public static boolean DO_FOG_DENSITY;
	
	public static boolean CANCEL_GUIS;
	public static boolean CANCEL_OVERLAY;
	public static boolean CROSSHAIR;
	public static boolean VIGNETTE;
	public static boolean FIXED_FOV;
	
	public static boolean BLOCKCOLORS;
	public static boolean GRASS_VARIANCE;
	
	//WORLD
	public static boolean NO_ORES;
	
	public static boolean FOUR_SEASONS_WORLDGEN;
	
	public static void preinit(FMLPreInitializationEvent e) {
		config = new Configuration(e.getSuggestedConfigurationFile(), String.valueOf(CURRENT_CONFIG_VERSION));
		config.load();
		
		readConfig();
	}
	
	private static void readConfig() {
		NUKE_OTHER_DIMS = config.getBoolean("nukeOtherDims", "general", true,
			"If true dimensions that aren't the overworld will be forcibly unregistered"
		);
		
		SPAWN_STUFF = config.getBoolean("spawnStuff", "general", true, "Should u spawn off the shore of the island in adventure mode with villagers disabled etc. Does a lot of things");
		
		CANCEL_ENTITYSPAWN = config.getBoolean("cancelEntitySpawn", "general", true, "Should certain entities be prevented from joining the world, specifically items");
		
		DAYNIGHT_SPEEDUP_FACTOR = config.getInt("daynightSpeedupFactor", "general", 4, 0, 100000000, "Every n ticks, the sun will advance an extra tick, effectively speeding up the day/night cycle. Lower values happen faster. 0 is vanilla speed");
		
		DISABLE_SPRINT = config.getBoolean("disableSprint", "general", true, "no sprinting (works currently by always keeping players at 2 hunger lol)");
		
		DISABLE_HEALTH = config.getBoolean("disableHealth", "general", true, "Disable health mechanic by constantly healing everyone and removing attacking.");
		
		SLOWDOWN_FACTOR = config.getFloat("slowdownFactor", "general", 0.17f, 0, 1, "The percentage the player will be slowed down by. 0 = no slowdown, 1 = literally can't move");
		
		//client stuff////////////////////////////////////////////
		
		DO_FOG_COLOR = config.getBoolean("doFogColor", "client", true, "Should the fog color be changed");
		DO_FOG_DENSITY = config.getBoolean("doFogDensity", "client", true, "Should the fog density be changed");
		
		CANCEL_GUIS = config.getBoolean("cancelGuis", "client", true, "Should certain guis like the inventory screen be cancelled");
		CANCEL_OVERLAY = config.getBoolean("cancelOverlay", "client", true, "Should most game overlay stuff be cancelled? this will make the game look crappy if you turn it off but useful for debugging");
		CROSSHAIR = config.getBoolean("crosshair", "client", false, "Should the crosshair be drawn");
		VIGNETTE = config.getBoolean("vignette", "client", true, "Should the vignette effect appear");
		FIXED_FOV = config.getBoolean("fixedFov", "client", true, "Should the FOV always be set to the fov specified in the settings");
		
		BLOCKCOLORS = config.getBoolean("blockcolors", "client", true, "Should custom grass colors etc exist");
		GRASS_VARIANCE = config.getBoolean("grassVariance", "client", true, "Should grass colors not be exactly constant");
		
		//world stuff///////////////////////////////////////////////
		NO_ORES = config.getBoolean("noOres", "world", true, "Should ores not spawn");
		
		FOUR_SEASONS_WORLDGEN = config.getBoolean("fourSeasonsWorldgen", "world", true, "Should the 'four seasons' effect apply to the world. This makes worldgen repeat every 100,000 blocks with different biomes. NYI");
		
		if(config.hasChanged()) {
			config.save();
		}
	}
	
	@SubscribeEvent
	public static void changed(ConfigChangedEvent.OnConfigChangedEvent e) {
		if(e.getModID().equals(ProteusUtil.MODID)) {
			readConfig();
		}
	}
}
