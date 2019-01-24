package quaternary.proteusutil.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.proteusutil.ProteusUtil;
import quaternary.proteusutil.config.ModConfig;

@Mod.EventBusSubscriber(modid = ProteusUtil.MODID, value = Side.CLIENT)
public class ClientEvents {
	@SubscribeEvent
	public static void fog(EntityViewRenderEvent.FogColors e) {
		if(!ModConfig.DO_FOG_COLOR) return;
		
		e.setRed(e.getRed() * 0.93f);
	}
	
	@SubscribeEvent
	public static void fogDensity(EntityViewRenderEvent.FogDensity e) {
		if(!ModConfig.DO_FOG_DENSITY) return;
		
		float time = Minecraft.getMinecraft().player.ticksExisted + Minecraft.getMinecraft().getRenderPartialTicks();
		time = MathHelper.clamp(time, 0, 150);
		float fogDensity = ((1 - (time / 150f)) / 10f) + .02f;
		
		e.setDensity(fogDensity);
		e.setCanceled(true);
	}
	
	@SubscribeEvent
	public static void openGui(GuiOpenEvent e) {
		GuiScreen ui = e.getGui();
		//lalala gui replacements
		
		if(ModConfig.CANCEL_GUIS) {
			if(ui instanceof GuiInventory) {
				Minecraft.getMinecraft().player.closeScreen();
				e.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void overlay(RenderGameOverlayEvent e) {
		if(ModConfig.CANCEL_OVERLAY) {
			switch(e.getType()) {
				case FOOD:
				case HELMET:
				case ARMOR:
				case HEALTH:
				case HOTBAR:
				case PORTAL:
				case JUMPBAR:
				case BOSSINFO:
				case BOSSHEALTH:
				case EXPERIENCE:
				case HEALTHMOUNT:
				case POTION_ICONS:
					e.setCanceled(true);
			}
		}
		
		//configure option for this
		if(!ModConfig.CROSSHAIR && e.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) e.setCanceled(true);
		
		//and this
		if(!ModConfig.VIGNETTE && e.getType() == RenderGameOverlayEvent.ElementType.VIGNETTE) e.setCanceled(true);
		
		//center/rerender this?
		if(e.getType() == RenderGameOverlayEvent.ElementType.AIR) {
			//stuff
		}
	}
	
	@SubscribeEvent
	public static void hand(RenderHandEvent e) {
		if(ModConfig.CANCEL_OVERLAY) e.setCanceled(true);
	}
	
	@SubscribeEvent
	public static void blockcolors(ColorHandlerEvent.Block e) {
		if(ModConfig.BLOCKCOLORS) {
			e.getBlockColors().registerBlockColorHandler(ColorHandlers.GRASS_COLOR, Blocks.GRASS);
			e.getBlockColors().registerBlockColorHandler(ColorHandlers.DOUBLE_PLANT_COLOR, Blocks.DOUBLE_PLANT);
			e.getBlockColors().registerBlockColorHandler(ColorHandlers.TALLGRASS_COLOR, Blocks.TALLGRASS);
		}
	}
	
	@SubscribeEvent
	public static void fov(EntityViewRenderEvent.FOVModifier e) {
		if(ModConfig.FIXED_FOV) {
			e.setFOV(Minecraft.getMinecraft().gameSettings.fovSetting);
		}
	}
}
