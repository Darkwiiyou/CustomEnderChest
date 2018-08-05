/*    */ package net.craftersland.customenderchest;
/*    */ 
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class SoundHandler
/*    */ {
/*    */   private EnderChest pl;
/*    */   
/*    */   public SoundHandler(EnderChest pl)
/*    */   {
/* 12 */     this.pl = pl;
/*    */   }
/*    */   
/*    */   public void sendAnvilLandSound(Player p) {
/* 16 */     if (EnderChest.is19Server) {
/* 17 */       p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
/*    */     } else {
/* 19 */       p.playSound(p.getLocation(), Sound.valueOf("ANVIL_LAND"), 1.0F, 1.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public void sendCompleteSound(Player p) {
/* 24 */     if (EnderChest.is19Server) {
/* 25 */       p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
/*    */     } else {
/* 27 */       p.playSound(p.getLocation(), Sound.valueOf("LEVEL_UP"), 1.0F, 1.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public void sendEnderchestCloseSound(Player p) {
/* 32 */     if (EnderChest.is19Server) {
/* 33 */       p.playSound(p.getLocation(), Sound.BLOCK_ENDERCHEST_CLOSE, 1.0F, 1.0F);
/*    */     } else {
/* 35 */       p.playSound(p.getLocation(), Sound.valueOf("CHEST_CLOSE"), 1.0F, 1.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public void sendEnderchestOpenSound(Player p) {
/* 40 */     if (EnderChest.is19Server) {
/* 41 */       p.playSound(p.getLocation(), Sound.BLOCK_ENDERCHEST_OPEN, 1.0F, 1.0F);
/*    */     } else {
/* 43 */       p.playSound(p.getLocation(), Sound.valueOf("CHEST_OPEN"), 1.0F, 1.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public void sendFailedSound(Player p) {
/* 48 */     if (EnderChest.is19Server) {
/* 49 */       p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 3.0F, 3.0F);
/*    */     } else {
/* 51 */       p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 3.0F, 3.0F);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/SoundHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */