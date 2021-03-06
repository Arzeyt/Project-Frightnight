// Date: 9/10/2014 3:02:57 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package com.arzeyt.theDarkness.lightOrb;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLightOrb extends ModelBase
{
  //fields
    ModelRenderer orb;
  
  public ModelLightOrb()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      orb = new ModelRenderer(this, 0, 0);
      orb.addBox(-3F, -3F, -3F, 6, 6, 6);
      orb.setRotationPoint(0F, 21F, 0F);
      orb.setTextureSize(64, 32);
      orb.mirror = true;
      setRotation(orb, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    orb.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

  public void renderModel(float f){
	  orb.render(f);
  }
}
