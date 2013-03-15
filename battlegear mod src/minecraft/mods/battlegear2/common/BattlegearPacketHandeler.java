package mods.battlegear2.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;


import mods.battlegear2.common.inventory.InventoryPlayerBattle;
import mods.battlegear2.common.utils.EnumBGAnimations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class BattlegearPacketHandeler implements IPacketHandler {
	
	public static final String guiPackets = "MB-GUI";
	public static final String syncBattlePackets = "MB-SyncAllItems";
	public static final String mbAnimation = "MB-animation";

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		
		if(packet.channel.equals(syncBattlePackets)){
			processBattleItemsSync(packet, (EntityPlayer)player);
		}else if(packet.channel.equals(guiPackets)){
			processBattlegearGUIPacket(packet, (EntityPlayer)player);
		}else if (packet.channel.equals(mbAnimation)){
			processOffHandAnimationPacket(packet, ((EntityPlayer)player).worldObj);
		}
		
	}

	public static Packet250CustomPayload generateGUIPacket(int equipid) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(4);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(equipid);
		}catch (Exception ex) {
	        ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = guiPackets;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	private void processBattlegearGUIPacket(Packet250CustomPayload packet, EntityPlayer player) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		int windowID = 0;
		try{
			windowID = inputStream.readInt();
		}catch (IOException e) {
            e.printStackTrace();
            return;
		}
		
		player.openGui(BattleGear.instance, windowID, player.worldObj, 0, 0, 0);
	}
	
	public static Packet250CustomPayload generateSyncBattleItemsPacket(String user, InventoryPlayer inventory){
		ByteArrayOutputStream bos = new ByteArrayOutputStream(5120);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
			Packet.writeString(user, outputStream);
			outputStream.writeInt(inventory.currentItem);
			for(int i = 0; i < InventoryPlayerBattle.EXTRA_INV_SIZE; i++){
				Packet.writeItemStack(inventory.getStackInSlot(i+InventoryPlayerBattle.OFFSET), outputStream);
			}
		}catch (Exception ex) {
	        ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = syncBattlePackets;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	private void processBattleItemsSync(Packet250CustomPayload packet, EntityPlayer player){
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try{
			EntityPlayer targetPlayer = player.worldObj.getPlayerEntityByName(Packet.readString(inputStream, 30));
			
			System.out.println(targetPlayer.username);
			targetPlayer.inventory.currentItem = inputStream.readInt();
			for(int i = 0; i < InventoryPlayerBattle.EXTRA_INV_SIZE; i++){
				ItemStack stack = Packet.readItemStack(inputStream);
				
				if(stack!=null){
					System.out.println(stack.getItemName());
					targetPlayer.inventory.setInventorySlotContents(InventoryPlayerBattle.OFFSET+i, stack);
				}
			}
		}catch (IOException e) {
            e.printStackTrace();
            return;
		}
		
	}
	
	public static Packet250CustomPayload generateBgAnimationPacket(EnumBGAnimations animation, String username){

		ByteArrayOutputStream bos = new ByteArrayOutputStream(300);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(animation.ordinal());
			Packet.writeString(username, outputStream);
			
		}catch (Exception ex) {
	        ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = mbAnimation;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	private void processOffHandAnimationPacket(Packet250CustomPayload packet, World world) {
		
		System.out.println("Recieve");
		
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		String playername = null;
		EnumBGAnimations animation = null;
		try{
			animation = EnumBGAnimations.values()[inputStream.readInt()];
			playername = Packet.readString(inputStream, 16);
			System.out.println(playername);
		}catch (IOException e) {
            e.printStackTrace();
            return;
		}

		if(playername != null && animation != null){
			
			EntityPlayer entity = world.getPlayerEntityByName(playername);
			
		
				
				if(world instanceof WorldServer){
					System.out.println("Re-distribute Packet");
					
					((WorldServer)world).getEntityTracker().sendPacketToAllPlayersTrackingEntity(entity, packet);
				}
				
				
				System.out.println("Process");
				animation.processAnimation(entity);
				
			
			
			
			
		}
	}
}
