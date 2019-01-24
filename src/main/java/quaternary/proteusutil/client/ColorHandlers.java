package quaternary.proteusutil.client;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import quaternary.proteusutil.config.ModConfig;

import java.util.Random;

public class ColorHandlers {
	private static final NoiseGeneratorPerlin GRASS_VARIANCE = new NoiseGeneratorPerlin(new Random(123123), 2);
	
	public static IBlockColor GRASS_COLOR = ((state, world, pos, tintIndex) -> {
		if(world != null && pos != null) {
			int originalColor = BiomeColorHelper.getGrassColorAtPos(world, pos);
			
			int greenLevel = (originalColor >> 8) & 0xFF;
			
			greenLevel -= 15;
			if(ModConfig.GRASS_VARIANCE) {
				greenLevel += GRASS_VARIANCE.getValue(pos.getX() / 60d, pos.getZ() / 60d) * 25;
			} else {
				greenLevel += 5;
			}
			if(greenLevel > 255) greenLevel = 255;
			if(greenLevel < 0) greenLevel = 0;
			
			return (originalColor & 0xFF00FF) | (greenLevel << 8);
		} else return ColorizerGrass.getGrassColor(.5, 1); //default
	});
	
	public static IBlockColor DOUBLE_PLANT_COLOR = ((state, world, pos, tintIndex) -> {
		BlockDoublePlant.EnumPlantType type = state.getValue(BlockDoublePlant.VARIANT);
		if(world != null && pos != null && (type == BlockDoublePlant.EnumPlantType.FERN || type == BlockDoublePlant.EnumPlantType.GRASS)) {
			return GRASS_COLOR.colorMultiplier(state, world, state.getValue(BlockDoublePlant.HALF) == BlockDoublePlant.EnumBlockHalf.UPPER ? pos.down() : pos, tintIndex);
		}
		
		return 0xFFFFFF;
	});
	
	public static IBlockColor TALLGRASS_COLOR = ((state, world, pos, tintIndex) -> {
		if(state.getValue(BlockTallGrass.TYPE) == BlockTallGrass.EnumType.DEAD_BUSH) return 0xFFFFFF;
		else return GRASS_COLOR.colorMultiplier(state, world, pos, tintIndex);
	});
}
