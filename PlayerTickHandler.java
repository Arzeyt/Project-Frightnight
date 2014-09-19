package com.arzeyt.theDarkness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.arzeyt.theDarkness.proxies.PacketTheDarkness;
import com.arzeyt.theDarkness.tower.TileEntityTower;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlayerTickHandler {

	//if there's only one instance on the server, these variables (inDarkness) will prove to be troublesome. Probably needa create a set to hold 
	//their values
	
	private int locationCheckTick = ConfigValues.locationTickRate;
	private int counter = 0;
	private boolean inDarkness=false;
	private TDLocation lightingLoc = null;


	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) throws IOException{
	
		boolean isRemote = e.player.worldObj.isRemote;
		TDLocation playerLoc = new TDLocation((int)e.player.posX, (int)e.player.posY, (int)e.player.posZ);
		
		if(isRemote==false){
			executeServerPlayerTick(e, playerLoc);
		}else if(isRemote==true){
			executeClientPlayerTick(e, playerLoc);
		}
	}
	
	

	private void executeServerPlayerTick(PlayerTickEvent e, TDLocation playerLoc) throws IOException {
		TileEntityTower tower = TheDarkness.towerManager.getNearestTower((int)e.player.posX, (int)e.player.posZ);

		
		//update player in towerManager and set inDarkness. Should do this FIRST!
		if(counter%(locationCheckTick)==0){
			if(TheDarkness.towerManager.towers.size()<1){
				System.out.println("No Towers");
			}else{
				TheDarkness.towerManager.updatePlayerLocation(e.player);
				inDarkness = TheDarkness.towerManager.getTDPlayer(e.player).inDarkness;
				//System.out.println("inDarkness = "+ inDarkness+" players in map"+TheDarkness.towerManager.players.size());
				
				
			}
		}
		
		//render the light of the light orb
		if(counter%ConfigValues.lightOrbLightRenderRate==0){
			if(e.player.getHeldItem()!=null && e.player.getHeldItem().getItem().equals(Item.getItemFromBlock(TheDarkness.lightOrbBlock))){
				if(lightingLoc==null){
					lightingLoc = new TDLocation(0, 0, 0);
				}else if(playerLoc.equals(lightingLoc)==false && e.player.worldObj.getBlock(playerLoc.x, playerLoc.y, playerLoc.z).equals(Blocks.air)){
					System.out.println("holding light orb and not at same location");
					e.player.worldObj.setBlockToAir(lightingLoc.x, lightingLoc.y, lightingLoc.z);
					e.player.worldObj.setBlock(playerLoc.x, playerLoc.y, playerLoc.z, TheDarkness.lightingBlock);
					lightingLoc=playerLoc;
				}
			}
		}
		
		//send darkness visualization packets to players
		if(counter%(ConfigValues.darknessWallLocationCheckTime)==0){
			TDPlayer tdp = TheDarkness.towerManager.getTDPlayer(e.player);
			
			if(/**not by dark tower*/inDarkness==false && /**by another tower*/tdp.closeToTower==true && (/**not rendering darkness wall*/tdp.doDarknessWallRender()==false || /**not the same location as before*/ isSameLocation(tdp.darknessWallRenderLocation(), tower.getTDLocation())==false)){
				System.out.println("Show darkness wall");
				try {
					TheDarkness.channel.sendTo(PacketTheDarkness.createWallVisPacket(e.player, tower), (EntityPlayerMP) e.player);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				tdp.doDarknessWallRenderOnLocation(new TDLocation(tower.xCoord, tower.yCoord, tower.zCoord));
				
			}else if(/**by dark tower*/ inDarkness==true && /**rendering darkness wall*/ tdp.doDarknessWallRender()==true ){
					System.out.println("Stop showing darkness wall");
					TheDarkness.channel.sendTo(PacketTheDarkness.createWallVisOffPacket(e.player), (EntityPlayerMP) e.player);
				tdp.stopDarknessWallRender();
				
			}else if(inDarkness==true && tdp.justLoggedIn){
				TheDarkness.channel.sendTo(PacketTheDarkness.createWallVisOffPacket(e.player), (EntityPlayerMP) e.player);
				tdp.justLoggedIn=false;
			}
		}
		
		if(counter%ConfigValues.locationTickRate==0){
			int range = 5;
			EntityPlayer p = e.player;
			List<EntityLiving> mobs =  p.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(p.posX-range, p.posY-range, p.posZ-range, p.posX + range, p.posY + range, p.posZ + range));
			for(EntityLiving mob : mobs){
				if(ExtendedPeaceful.get(mob)!=null){
					ExtendedPeaceful exprop = ExtendedPeaceful.get(mob);
					if(exprop.hasDarkness){
						p.worldObj.spawnParticle("largesmoke", mob.posX, mob.posY, mob.posZ, 0.0D, 0.0D, 0.0D);
					}
				}
			}
		}
			
		counter++;
	}

	private void executeClientPlayerTick(PlayerTickEvent e, TDLocation playerLoc) {
		//smokey peaceful
	
		counter++;
	}
	
	
	public boolean isSameLocation(TDLocation loc1, TDLocation loc2){
		if(loc1==null || loc2==null){
			return false;
		}
		if(isSameLocation(loc1.x, loc1.y, loc1.z, loc2.x, loc2.y, loc2.z)){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isSameLocation(int x, int y, int z, int x2, int y2, int z2){
		if(x==x2 && y==y2 && z==z2){
			return true;
		}else{
			return false;
		}
	}
	
	
}
