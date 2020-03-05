package mods.battlegear2.items.arrows;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
/**
 * An arrow which is effective against fire immune creatures
 * @author Seitenca
 *
 */
public class EntityIceArrow extends AbstractMBArrow{

	public EntityIceArrow(World par1World) {
        super(par1World);
    }

    public EntityIceArrow(World par1World, EntityLivingBase par2EntityLivingBase, float par3) {
        super(par1World, par2EntityLivingBase, par3);
    }

    public EntityIceArrow(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5) {
        super(par1World, par2EntityLivingBase, par3EntityLivingBase, par4, par5);
    }

    @Override
    public void onUpdate() {
        Vec3 a = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 b = Vec3.createVectorHelper(this.posX + this.motionX*1.5, this.posY + this.motionY*1.5, this.posZ + this.motionZ*1.5);
        MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(a, b, false, true, true);

        if (ticksInGround == 0 && movingobjectposition != null && movingobjectposition.entityHit == null){
            ticksInGround ++;
            onHitGround(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
        }
        super.onUpdate();
    }
    
    @Override
	public boolean onHitEntity(Entity entityHit, DamageSource source, float amount) {
		boolean flag = false;
		if(entityHit.isImmuneToFire()) {
			entityHit.attackEntityFrom(new EntityDamageSourceIndirect("frost", null, shootingEntity).setProjectile().setDamageBypassesArmor(), amount + 10);
		}
		if(entityHit instanceof EntityLivingBase){
			entityHit.attackEntityFrom(new EntityDamageSourceIndirect("default", null, shootingEntity).setProjectile(), amount - 2);
			((EntityLivingBase)entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 2));
	        ((EntityLivingBase)entityHit).setArrowCountInEntity(((EntityLivingBase) entityHit).getArrowCountInEntity()+1);
			flag = true;
		}
		setDead();
		return flag;
	}

	@Override
	public void onHitGround(int x, int y, int z) {
		setDead();
	}

}
