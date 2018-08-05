/*    */ package net.craftersland.customenderchest;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ 
/*    */ public class DataHandler
/*    */ {
/* 11 */   private Map<UUID, Inventory> liveData = new HashMap();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Inventory getData(UUID playerUUID)
/*    */   {
/* 18 */     return (Inventory)this.liveData.get(playerUUID);
/*    */   }
/*    */   
/*    */   public void setData(UUID playerUUID, Inventory enderchestInventory) {
/* 22 */     this.liveData.put(playerUUID, enderchestInventory);
/*    */   }
/*    */   
/*    */   public void removeData(UUID playerUUID) {
/* 26 */     this.liveData.remove(playerUUID);
/*    */   }
/*    */   
/*    */   public boolean isLiveEnderchest(Inventory inventory) {
/* 30 */     return this.liveData.containsValue(inventory);
/*    */   }
/*    */ }


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/DataHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */