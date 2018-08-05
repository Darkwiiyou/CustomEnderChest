/*     */ package net.craftersland.customenderchest.utils;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.craftersland.customenderchest.ConfigHandler;
/*     */ import net.craftersland.customenderchest.EnderChest;
/*     */ import net.craftersland.customenderchest.SoundHandler;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ 
/*     */ public class EnderChestUtils
/*     */ {
/*     */   private EnderChest enderchest;
/*     */   
/*     */   public EnderChestUtils(EnderChest enderchest)
/*     */   {
/*  20 */     this.enderchest = enderchest;
/*     */   }
/*     */   
/*     */   public String getTitle(Player p)
/*     */   {
/*  25 */     List<String> chestTitle = new java.util.ArrayList();
/*  26 */     chestTitle.add(this.enderchest.getConfigHandler().getString("enderChestTitle.enderChestName"));
/*     */     
/*  28 */     if (p.hasPermission("CustomEnderChest.level.5")) {
/*  29 */       chestTitle.set(0, ((String)chestTitle.get(0)).replaceAll("%level", this.enderchest.getConfigHandler().getString("enderChestTitle.level5")));
/*  30 */     } else if ((p.hasPermission("CustomEnderChest.level.4")) && (!p.isOp())) {
/*  31 */       chestTitle.set(0, ((String)chestTitle.get(0)).replaceAll("%level", this.enderchest.getConfigHandler().getString("enderChestTitle.level4")));
/*  32 */     } else if ((p.hasPermission("CustomEnderChest.level.3")) && (!p.isOp())) {
/*  33 */       chestTitle.set(0, ((String)chestTitle.get(0)).replaceAll("%level", this.enderchest.getConfigHandler().getString("enderChestTitle.level3")));
/*  34 */     } else if ((p.hasPermission("CustomEnderChest.level.2")) && (!p.isOp())) {
/*  35 */       chestTitle.set(0, ((String)chestTitle.get(0)).replaceAll("%level", this.enderchest.getConfigHandler().getString("enderChestTitle.level2")));
/*  36 */     } else if ((p.hasPermission("CustomEnderChest.level.1")) && (!p.isOp())) {
/*  37 */       chestTitle.set(0, ((String)chestTitle.get(0)).replaceAll("%level", this.enderchest.getConfigHandler().getString("enderChestTitle.level1")));
/*  38 */     } else if ((p.hasPermission("CustomEnderChest.level.0")) && (!p.isOp())) {
/*  39 */       chestTitle.set(0, ((String)chestTitle.get(0)).replaceAll("%level", this.enderchest.getConfigHandler().getString("enderChestTitle.level0")));
/*     */     }
/*     */     
/*  42 */     chestTitle.set(0, ((String)chestTitle.get(0)).replaceAll("%player", p.getName()));
/*     */     
/*  44 */     if (((String)chestTitle.get(0)).length() <= 32) {
/*  45 */       String enderChestTitle = ((String)chestTitle.get(0)).replaceAll("&", "§");
/*  46 */       return enderChestTitle;
/*     */     }
/*  48 */     String enderChestTitle = ((String)chestTitle.get(0)).substring(0, 32).replaceAll("&", "§");
/*  49 */     return enderChestTitle;
/*     */   }
/*     */   
/*     */   public String getCmdTitle(Player p)
/*     */   {
/*  54 */     String chestTitle = ChatColor.DARK_PURPLE + ChatColor.BOLD + p.getName() + "'s " + ChatColor.LIGHT_PURPLE + "Ender Chest";
/*     */     
/*  56 */     if (chestTitle.length() <= 32) {
/*  57 */       return chestTitle.replaceAll("&", "§");
/*     */     }
/*  59 */     return chestTitle.substring(0, 32).replaceAll("&", "§");
/*     */   }
/*     */   
/*     */   public String getCmdTitle(UUID p)
/*     */   {
/*  64 */     String chestTitle = ChatColor.DARK_PURPLE + ChatColor.BOLD + this.enderchest.getStorageInterface().loadName(p) + "'s " + ChatColor.LIGHT_PURPLE + "Ender Chest";
/*     */     
/*  66 */     if (chestTitle.length() <= 32) {
/*  67 */       return chestTitle.replaceAll("&", "§");
/*     */     }
/*  69 */     return chestTitle.substring(0, 32).replaceAll("&", "§");
/*     */   }
/*     */   
/*     */ 
/*     */   public Integer getSize(Player p)
/*     */   {
/*  75 */     if (p.hasPermission("CustomEnderChest.level.5")) {
/*  76 */       return Integer.valueOf(54);
/*     */     }
/*  78 */     if ((p.hasPermission("CustomEnderChest.level.4")) && (!p.isOp())) {
/*  79 */       return Integer.valueOf(45);
/*     */     }
/*  81 */     if ((p.hasPermission("CustomEnderChest.level.3")) && (!p.isOp())) {
/*  82 */       return Integer.valueOf(36);
/*     */     }
/*  84 */     if ((p.hasPermission("CustomEnderChest.level.2")) && (!p.isOp())) {
/*  85 */       return Integer.valueOf(27);
/*     */     }
/*  87 */     if ((p.hasPermission("CustomEnderChest.level.1")) && (!p.isOp())) {
/*  88 */       return Integer.valueOf(18);
/*     */     }
/*  90 */     if ((p.hasPermission("CustomEnderChest.level.0")) && (!p.isOp())) {
/*  91 */       return Integer.valueOf(9);
/*     */     }
/*     */     
/*  94 */     return Integer.valueOf(0);
/*     */   }
/*     */   
/*     */   public void openMenu(Player p)
/*     */   {
/*  99 */     p.closeInventory();
/*     */     
/* 101 */     int size = this.enderchest.getEnderChestUtils().getSize(p).intValue();
/*     */     
/* 103 */     if (size == 0) {
/* 104 */       this.enderchest.getConfigHandler().printMessage(p, "chatMessages.noPermission");
/* 105 */       this.enderchest.getSoundHandler().sendFailedSound(p);
/* 106 */       return;
/*     */     }
/* 108 */     Inventory inv = this.enderchest.getDataHandler().getData(p.getUniqueId());
/* 109 */     if (inv == null) {
/* 110 */       String enderChestTitle = this.enderchest.getEnderChestUtils().getTitle(p);
/* 111 */       inv = Bukkit.getServer().createInventory(p, size, enderChestTitle);
/* 112 */     } else if (inv.getSize() != size) {
/* 113 */       String enderChestTitle = this.enderchest.getEnderChestUtils().getTitle(p);
/* 114 */       Inventory newInv = Bukkit.getServer().createInventory(p, size, enderChestTitle);
/*     */       int toScan;
/* 116 */       int toScan; if (size > inv.getSize()) {
/* 117 */         toScan = inv.getSize();
/*     */       } else {
/* 119 */         toScan = size;
/*     */       }
/* 121 */       for (int i = 0; i < toScan; i++) {
/* 122 */         org.bukkit.inventory.ItemStack item = inv.getItem(i);
/* 123 */         newInv.setItem(i, item);
/*     */       }
/* 125 */       inv = newInv;
/*     */     }
/* 127 */     this.enderchest.getDataHandler().setData(p.getUniqueId(), inv);
/* 128 */     this.enderchest.getSoundHandler().sendEnderchestOpenSound(p);
/* 129 */     p.openInventory(inv);
/*     */   }
/*     */ }


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/utils/EnderChestUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */