package quaternary.proteusutil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import quaternary.proteusutil.config.ModConfig;

@Mod.EventBusSubscriber(modid = ProteusUtil.MODID)
public class CommonEvents {
	@SubscribeEvent
	public static void joinWorld(EntityJoinWorldEvent e) {
		if(ModConfig.CANCEL_ENTITYSPAWN) {
			Entity ent = e.getEntity();
			if(ent instanceof EntityItem) {
				e.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void worldTick(TickEvent.WorldTickEvent e) {
		World world = e.world;
		//speed up the day/night cycle a little bit
		if(ModConfig.DAYNIGHT_SPEEDUP_FACTOR != 0 && world.getTotalWorldTime() % ModConfig.DAYNIGHT_SPEEDUP_FACTOR == 0) {
			world.setWorldTime(world.getWorldTime() + 1);
		}
	}
	
	@SubscribeEvent
	public static void playerTick(TickEvent.PlayerTickEvent e) {
		EntityPlayer player = e.player;
		if(ModConfig.DISABLE_SPRINT) {
			player.getFoodStats().setFoodLevel(4); //too low to sprint
		}
		
		if(ModConfig.DISABLE_HEALTH) {
			player.getFoodStats().setFoodSaturationLevel(100);
			player.heal(10000);
			player.setEntityInvulnerable(true);
			
			//this incidentally makes players immune to void damage, so let's fix that
			if(player.posY < -30) {
				player.setEntityInvulnerable(false);
				player.setHealth(0.01f);
				player.attackEntityFrom(DamageSource.OUT_OF_WORLD, 1337f);
			}
		}
		
		if(ModConfig.SLOWDOWN_FACTOR != 0) {
			double attributeSpeed = 0.1 * (1 - ModConfig.SLOWDOWN_FACTOR);
			player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(attributeSpeed);
		}
	}
	
	@SubscribeEvent
	public static void damage(LivingDamageEvent e) {
		if(ModConfig.DISABLE_HEALTH && !e.getSource().canHarmInCreative()) {
			e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void attack(LivingAttackEvent e) {
		if(ModConfig.DISABLE_HEALTH && !e.getSource().canHarmInCreative()) {
			e.setCanceled(true);
		}
	}
}
