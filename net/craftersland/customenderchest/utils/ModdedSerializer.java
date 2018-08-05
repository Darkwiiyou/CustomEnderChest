/*    */ package net.craftersland.customenderchest.utils;
/*    */ 
/*    */ import com.comphenix.protocol.utility.StreamSerializer;
/*    */ import java.io.IOException;
/*    */ import net.craftersland.customenderchest.EnderChest;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModdedSerializer
/*    */ {
/*    */   private EnderChest pl;
/*    */   
/*    */   public ModdedSerializer(EnderChest pl)
/*    */   {
/* 17 */     this.pl = pl;
/*    */   }
/*    */   
/*    */   public String toBase64(ItemStack[] itemStacks) throws IOException {
/* 21 */     StringBuilder stringBuilder = new StringBuilder();
/* 22 */     for (int i = 0; i < itemStacks.length; i++) {
/* 23 */       if (i > 0) {
/* 24 */         stringBuilder.append(";");
/*    */       }
/* 26 */       if ((itemStacks[i] != null) && (itemStacks[i].getType() != Material.AIR)) {
/* 27 */         stringBuilder.append(StreamSerializer.getDefault().serializeItemStack(itemStacks[i]));
/*    */       }
/*    */     }
/* 30 */     return stringBuilder.toString();
/*    */   }
/*    */   
/*    */   public ItemStack[] fromBase64(String data) throws IOException {
/* 34 */     String[] strings = data.split(";");
/* 35 */     ItemStack[] itemStacks = new ItemStack[strings.length];
/* 36 */     for (int i = 0; i < strings.length; i++) {
/* 37 */       if (!strings[i].equals("")) {
/* 38 */         itemStacks[i] = StreamSerializer.getDefault().deserializeItemStack(strings[i]);
/*    */       }
/*    */     }
/* 41 */     return itemStacks;
/*    */   }
/*    */ }


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/utils/ModdedSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */