package com.arzeyt.theDarkness;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLightOrb extends Item {
	public final String unlocalizedName = "lightOrb(Old)";
	
	public ItemLightOrb(){
		setUnlocalizedName(TheDarkness.MODID+"_"+unlocalizedName);
		setCreativeTab(CreativeTabs.tabMisc);
		setTextureName(TheDarkness.MODID+":"+unlocalizedName);
		
		//drop
		//onDroppedByPlayer(new ItemStack(this), player)
	}
	
	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player,
			World world, int x, int y, int z,
			int p_77648_7_, float p_77648_8_, float p_77648_9_,
			float p_77648_10_) {
		
		/**use this to eventually replace block dependant interactions.
		if(world.getBlock(x, y, z).hasTileEntity(0)){
			TileEntityTower te = (TileEntityTower)world.getTileEntity(x, y, z);
			if(te.towerType==TowerType.DARK || (te.towerType==TowerType.EMPTY && player.capabilities.isCreativeMode)){
				te.addOrbs(1);
				return true;
			}
		}
		*/
		return true;
		
		
	}

}
