package com.arzeyt.theDarkness.lightOrb;

import java.util.Random;

import com.arzeyt.theDarkness.tower.TileEntityTower;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLightOrb extends TileEntity {

	private static String NBT_LIFETIME = "lifetime";
	public int lifetime;
	private static float NORMAL_ROTATION_SPEED = 0.001F;
	
	public double startY;
	public double targetY;
	public float rotationSpeed=0;
	

	
	public void updateEntity(){
	
		//fireworks particles
		if(worldObj.isRemote==true){
			Random rand = new Random();
			this.getWorldObj().spawnParticle("fireworksSpark", xCoord+rand.nextDouble(), yCoord+rand.nextDouble(), zCoord+rand.nextDouble(), rand.nextDouble()-0.5, rand.nextDouble()-0.5, rand.nextDouble()-0.5);
		}
		//determine rotation
		if(worldObj.isRemote==true){
			if(isOnTower()){
				this.rotationSpeed=NORMAL_ROTATION_SPEED;
			}
		}
		
		lifetime++;
	}
		
	public void readFromNBT(NBTTagCompound nbt){
	    	super.readFromNBT(nbt);
	    	lifetime = nbt.getInteger(NBT_LIFETIME);
	    	markDirty();
	    	updateContainingBlockInfo();
	    	
	 }
	    
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setInteger(NBT_LIFETIME, lifetime);
    }
	
	@Override
    public Packet getDescriptionPacket() {
	    NBTTagCompound tag = new NBTTagCompound();
	    this.writeToNBT(tag);
	    return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }
    	
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
    	readFromNBT(packet.func_148857_g());
    }
    
    public boolean isOnTower(){
    	if(worldObj.getTileEntity(xCoord, yCoord-1, zCoord)!=null && worldObj.getTileEntity(xCoord, yCoord-1, zCoord) instanceof TileEntityTower){
    		return true;
    	}else{
    		return false;
    	}
    }
}


