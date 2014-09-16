package com.arzeyt.theDarkness;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

public class RenderTickHandler {

	int counter=0;
	
	@SubscribeEvent
	public void onRenderTick(RenderTickEvent e){
		
		Minecraft minecraft = Minecraft.getMinecraft();
		if(minecraft.inGameHasFocus==false){return;}
		
		TDLocation playerLoc = new TDLocation((int)minecraft.thePlayer.posX, (int)minecraft.thePlayer.posY, (int)minecraft.thePlayer.posZ);
		World world = minecraft.theWorld;
		
		if(TheDarkness.renderManager.shouldRenderTowerRadiusBorder()==true){
			if(counter%ConfigValues.darknessWallRenderTickTime==0){
				for(TDLocation loc : TheDarkness.renderManager.getTowerRadiusBorderRenderingLocations()){
					renderTowerRadiusBorderAt(world, loc, playerLoc);
				}
			}
		//remember to stick with TDRender manager for references
		}else if(TheDarkness.renderManager.shouldRenderDarknessSmoke==true){
			if(counter%(ConfigValues.darknessSmokeRenderTickTime)==0){
				//TheDarkness.towerManager.createDarknessSmoke(Minecraft.getMinecraft().theWorld, playerLoc, 99, 6);
			}
		}
		if(TheDarkness.renderManager.shouldRenderOrbSphere){
			if(counter%60==0){
				TheDarkness.towerManager.createParticleSphere(world, minecraft.thePlayer.posX, minecraft.thePlayer.posY, minecraft.thePlayer.posZ, 10, "fireworksSpark");
			}
		}
		counter++;
	}
	
	public void renderTowerRadiusBorderAt(World world, TDLocation loc, TDLocation playerLoc){
		
		TheDarkness.towerManager.createSparklingTowerRadiusBorder(Minecraft.getMinecraft().theWorld, loc, 50, playerLoc.y, 6);
		TheDarkness.towerManager.createSmokeyTowerRadiusBorder(world, loc, 99);
		
		//TheDarkness.towerManager.createTrackingBorderSparkles(Minecraft.getMinecraft().theWorld, playerLoc, TheDarkness.renderManager.loc, 3, 5);
		/**
		playerLoc.x=playerLoc.x+1;
		TheDarkness.towerManager.createTrackingBorderSparkles(Minecraft.getMinecraft().theWorld, playerLoc, TheDarkness.renderManager.loc);
		playerLoc.x=playerLoc.x+1;
		TheDarkness.towerManager.createTrackingBorderSparkles(Minecraft.getMinecraft().theWorld, playerLoc, TheDarkness.renderManager.loc);
		playerLoc.x=playerLoc.x-3;
		TheDarkness.towerManager.createTrackingBorderSparkles(Minecraft.getMinecraft().theWorld, playerLoc, TheDarkness.renderManager.loc);
		playerLoc.x=playerLoc.x-1;
		TheDarkness.towerManager.createTrackingBorderSparkles(Minecraft.getMinecraft().theWorld, playerLoc, TheDarkness.renderManager.loc);
		playerLoc.z=playerLoc.z+1;
		TheDarkness.towerManager.createTrackingBorderSparkles(Minecraft.getMinecraft().theWorld, playerLoc, TheDarkness.renderManager.loc);
		playerLoc.z=playerLoc.z+1;
		TheDarkness.towerManager.createTrackingBorderSparkles(Minecraft.getMinecraft().theWorld, playerLoc, TheDarkness.renderManager.loc);
		playerLoc.z=playerLoc.z-3;
		TheDarkness.towerManager.createTrackingBorderSparkles(Minecraft.getMinecraft().theWorld, playerLoc, TheDarkness.renderManager.loc);
		playerLoc.z=playerLoc.z-1;
		TheDarkness.towerManager.createTrackingBorderSparkles(Minecraft.getMinecraft().theWorld, playerLoc, TheDarkness.renderManager.loc);
		*/
		TheDarkness.towerManager.createTrackingBorderSmoke(Minecraft.getMinecraft().theWorld, playerLoc, loc);

	}
	
	
}
