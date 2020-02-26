package mods.battlegear2.items.arrows;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
/**
 * 
 * @author Seitenca
 *
 */
public class EntityIceArrow extends AbstractMBArrow{

	public EntityIceArrow(World par1World) {
        super(par1World);
        isImmuneToFire = false;
    }
    
    public EntityIceArrow(World par1World, EntityLivingBase par2EntityLivingBase, float par3) {
        super(par1World, par2EntityLivingBase, par3);
        isImmuneToFire = false;
    }

    public EntityIceArrow(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5) {
        super(par1World, par2EntityLivingBase, par3EntityLivingBase, par4, par5);
        isImmuneToFire = false;
    }

    @Override
    public boolean onHitEntity(Entity entityHit, DamageSource source, float ammount) {
    	if (iseffective == true){
	        source.setDamageIsAbsolute();
	        source.setDamageAllowedInCreativeMode();
	        source.setDamageBypassesArmor();
    	}
    	return false;
    }

    @Override
    public void onHitGround(int x, int y, int z) {
        if (worldObj.isAirBlock(x, y+1, z) && Blocks.ice.canPlaceBlockAt(worldObj, x, y+1, z)){
            worldObj.setBlock(x, y+1, z, Blocks.ice);
        }
    }
}
