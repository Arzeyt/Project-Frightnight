package com.arzeyt.theDarkness;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class TDEventHandler {

	@SubscribeEvent
	public void playerInteract(PlayerInteractEvent event){
		
	}
	
	@SubscribeEvent
	public void playerDrop(PlayerDropsEvent event){
		
	}
	
	@SubscribeEvent
	public void playerLogIn(PlayerLoggedInEvent event){
		TheDarkness.towerManager.addPlayer(event.player);
	}

	public void playerLogOut(PlayerLoggedOutEvent event){
		TheDarkness.towerManager.removePlayer(event.player);
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if (isPeaceful(event.entity) && ExtendedPeaceful.get((EntityLiving) event.entity) == null){
			ExtendedPeaceful XPeaceful = new ExtendedPeaceful((EntityLiving)event.entity);
			boolean inDarkness = TheDarkness.towerManager.inDarkness((int)event.entity.posX, (int) event.entity.posZ);
			if(inDarkness){
				XPeaceful.setEntityInDarkness();
			}else{
				XPeaceful.setEntityNotInDarkness();
			}
			ExtendedPeaceful.register((EntityLiving) event.entity);
		}
		
	}

	private boolean isPeaceful(Entity entity) {
		if(entity instanceof EntityAmbientCreature || entity instanceof EntityPig || entity instanceof EntityChicken || entity instanceof EntityCow 
				|| entity instanceof EntityMooshroom || entity instanceof EntityHorse){
			return true;
		}
		return false;
	}
}
