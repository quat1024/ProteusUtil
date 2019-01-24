package quaternary.proteusutil.world;

import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.proteusutil.config.ModConfig;

public class WorldgenHandler {
	@SubscribeEvent
	public static void layers(WorldTypeEvent.InitBiomeGens e) {
		if(ModConfig.FOUR_SEASONS_WORLDGEN) {
			//Stuff
		}
	}
	
	@SubscribeEvent
	public static void ore(OreGenEvent.GenerateMinable e) {
		if(!ModConfig.NO_ORES) return;
		
		switch(e.getType()) {
			case ANDESITE:
			case DIORITE:
			case GRANITE:
			case DIRT:
			case GRAVEL:
				return;
		}
		
		e.setResult(Event.Result.DENY);
	}
}
