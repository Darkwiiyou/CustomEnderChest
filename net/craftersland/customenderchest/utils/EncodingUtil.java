/*     */ package net.craftersland.customenderchest.utils;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.util.io.BukkitObjectInputStream;
/*     */ import org.bukkit.util.io.BukkitObjectOutputStream;
/*     */ import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
/*     */ 
/*     */ public class EncodingUtil
/*     */ {
/*     */   public static String toBase64(Inventory inventory) throws IllegalStateException, IOException
/*     */   {
/*  20 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*  21 */     BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
/*     */     
/*     */ 
/*  24 */     dataOutput.writeInt(inventory.getSize());
/*     */     
/*     */ 
/*  27 */     for (int i = 0; i < inventory.getSize(); i++) {
/*  28 */       dataOutput.writeObject(inventory.getItem(i));
/*     */     }
/*     */     
/*     */ 
/*  32 */     dataOutput.close();
/*  33 */     return Base64Coder.encodeLines(outputStream.toByteArray());
/*     */   }
/*     */   
/*     */   public static Inventory fromBase64(String data) throws IOException, ClassNotFoundException {
/*  37 */     ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
/*  38 */     BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
/*  39 */     Inventory inventory = org.bukkit.Bukkit.getServer().createInventory(null, dataInput.readInt());
/*     */     
/*     */ 
/*  42 */     for (int i = 0; i < inventory.getSize(); i++) {
/*  43 */       inventory.setItem(i, (ItemStack)dataInput.readObject());
/*     */     }
/*     */     
/*  46 */     dataInput.close();
/*  47 */     return inventory;
/*     */   }
/*     */   
/*     */   public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
/*     */     try {
/*  52 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*  53 */       BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
/*     */       
/*     */ 
/*  56 */       dataOutput.writeInt(items.length);
/*     */       
/*     */ 
/*  59 */       for (int i = 0; i < items.length; i++) {
/*  60 */         dataOutput.writeObject(items[i]);
/*     */       }
/*     */       
/*     */ 
/*  64 */       dataOutput.close();
/*  65 */       return Base64Coder.encodeLines(outputStream.toByteArray());
/*     */     } catch (Exception e) {
/*  67 */       throw new IllegalStateException("Unable to save item stacks.", e);
/*     */     }
/*     */   }
/*     */   
/*     */   public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
/*     */     try {
/*  73 */       ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
/*  74 */       BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
/*  75 */       ItemStack[] items = new ItemStack[dataInput.readInt()];
/*     */       
/*     */ 
/*  78 */       for (int i = 0; i < items.length; i++) {
/*  79 */         items[i] = ((ItemStack)dataInput.readObject());
/*     */       }
/*     */       
/*  82 */       dataInput.close();
/*  83 */       return items;
/*     */     } catch (ClassNotFoundException e) {
/*  85 */       throw new IOException("Unable to decode class type.", e);
/*     */     }
/*     */   }
/*     */   
/*     */   public static String potionEffectsCollectionToBase64(Collection<PotionEffect> potionEffect) throws IllegalStateException {
/*     */     try {
/*  91 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*  92 */       BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
/*     */       
/*     */ 
/*  95 */       dataOutput.writeInt(potionEffect.toArray().length);
/*     */       
/*     */ 
/*  98 */       for (int i = 0; i < potionEffect.toArray().length; i++) {
/*  99 */         dataOutput.writeObject(potionEffect.toArray()[i]);
/*     */       }
/*     */       
/*     */ 
/* 103 */       dataOutput.close();
/* 104 */       return Base64Coder.encodeLines(outputStream.toByteArray());
/*     */     } catch (Exception e) {
/* 106 */       throw new IllegalStateException("Unable to save potion effect.", e);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Collection<PotionEffect> potionEffectsCollectionFromBase64(String data) throws IOException {
/*     */     try {
/* 112 */       ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
/* 113 */       BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
/*     */       
/* 115 */       PotionEffect[] potionEffects = new PotionEffect[dataInput.readInt()];
/* 116 */       Collection<PotionEffect> effects = new ArrayList();
/*     */       
/*     */ 
/* 119 */       for (int i = 0; i < potionEffects.length; i++) {
/* 120 */         effects.add((PotionEffect)dataInput.readObject());
/*     */       }
/*     */       
/* 123 */       dataInput.close();
/* 124 */       return effects;
/*     */     } catch (ClassNotFoundException e) {
/* 126 */       throw new IOException("Unable to decode class type.", e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/utils/EncodingUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */