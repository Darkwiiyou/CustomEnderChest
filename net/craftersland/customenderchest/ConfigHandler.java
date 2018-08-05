/*     */ package net.craftersland.customenderchest;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class ConfigHandler
/*     */ {
/*     */   private EnderChest enderchest;
/*     */   
/*     */   public ConfigHandler(EnderChest enderchest)
/*     */   {
/*  15 */     this.enderchest = enderchest;
/*  16 */     loadConfig();
/*     */   }
/*     */   
/*     */   public void loadConfig() {
/*  20 */     File pluginFolder = new File("plugins" + System.getProperty("file.separator") + EnderChest.pluginName);
/*  21 */     if (!pluginFolder.exists()) {
/*  22 */       pluginFolder.mkdir();
/*     */     }
/*  24 */     File configFile = new File("plugins" + System.getProperty("file.separator") + EnderChest.pluginName + System.getProperty("file.separator") + "config.yml");
/*  25 */     if (!configFile.exists()) {
/*  26 */       EnderChest.log.info("No config file found! Creating new one...");
/*  27 */       this.enderchest.saveDefaultConfig();
/*     */     }
/*     */     try {
/*  30 */       EnderChest.log.info("Loading the config file...");
/*  31 */       this.enderchest.getConfig().load(configFile);
/*  32 */       EnderChest.log.info("Config loaded successfully!");
/*     */     } catch (Exception e) {
/*  34 */       EnderChest.log.severe("Could not load the config file! You need to regenerate the config! Error: " + e.getMessage());
/*  35 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getStringWithColor(String key) {
/*  40 */     if (!this.enderchest.getConfig().contains(key)) {
/*  41 */       this.enderchest.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + EnderChest.pluginName + " folder! (Try generating a new one by deleting the current)");
/*  42 */       return "errorCouldNotLocateInConfigYml:" + key;
/*     */     }
/*  44 */     return this.enderchest.getConfig().getString(key).replaceAll("&", "§");
/*     */   }
/*     */   
/*     */   public String getString(String key)
/*     */   {
/*  49 */     if (!this.enderchest.getConfig().contains(key)) {
/*  50 */       this.enderchest.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + EnderChest.pluginName + " folder! (Try generating a new one by deleting the current)");
/*  51 */       return "errorCouldNotLocateInConfigYml:" + key;
/*     */     }
/*  53 */     return this.enderchest.getConfig().getString(key);
/*     */   }
/*     */   
/*     */   public List<String> getStringList(String key)
/*     */   {
/*  58 */     if (!this.enderchest.getConfig().contains(key)) {
/*  59 */       this.enderchest.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + EnderChest.pluginName + " folder! (Try generating a new one by deleting the current)");
/*  60 */       return null;
/*     */     }
/*  62 */     return this.enderchest.getConfig().getStringList(key);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String key)
/*     */   {
/*  67 */     if (!this.enderchest.getConfig().contains(key)) {
/*  68 */       this.enderchest.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + EnderChest.pluginName + " folder! (Try generating a new one by deleting the current)");
/*  69 */       return false;
/*     */     }
/*  71 */     return this.enderchest.getConfig().getBoolean(key);
/*     */   }
/*     */   
/*     */   public double getDouble(String key)
/*     */   {
/*  76 */     if (!this.enderchest.getConfig().contains(key)) {
/*  77 */       this.enderchest.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + EnderChest.pluginName + " folder! (Try generating a new one by deleting the current)");
/*  78 */       return 0.0D;
/*     */     }
/*  80 */     return this.enderchest.getConfig().getDouble(key);
/*     */   }
/*     */   
/*     */   public int getInteger(String key)
/*     */   {
/*  85 */     if (!this.enderchest.getConfig().contains(key)) {
/*  86 */       this.enderchest.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + EnderChest.pluginName + " folder! (Try generating a new one by deleting the current)");
/*  87 */       return 0;
/*     */     }
/*  89 */     return this.enderchest.getConfig().getInt(key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void printMessage(Player p, String messageKey)
/*     */   {
/*  96 */     if (this.enderchest.getConfig().contains(messageKey)) {
/*  97 */       List<String> message = new java.util.ArrayList();
/*  98 */       String configMsg = this.enderchest.getConfig().getString(messageKey);
/*     */       
/* 100 */       if (configMsg.matches("")) { return;
/*     */       }
/* 102 */       message.add(configMsg);
/*     */       
/* 104 */       if (p != null)
/*     */       {
/* 106 */         p.sendMessage(getString("chatMessages.prefix").replaceAll("&", "§") + ((String)message.get(0)).replaceAll("&", "§"));
/*     */       }
/*     */     }
/*     */     else {
/* 110 */       this.enderchest.getLogger().severe("Could not locate '" + messageKey + "' in the config.yml inside of the CustomEnderChest folder!");
/* 111 */       p.sendMessage(org.bukkit.ChatColor.DARK_RED + org.bukkit.ChatColor.BOLD + ">> " + org.bukkit.ChatColor.RED + "Could not locate '" + messageKey + "' in the config.yml inside of the CustomEnderChest folder!");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/ConfigHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */