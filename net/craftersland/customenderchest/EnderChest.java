/*     */ package net.craftersland.customenderchest;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
/*     */ import net.craftersland.customenderchest.commands.FileToMysqlCmd;
/*     */ import net.craftersland.customenderchest.storage.FlatFileStorage;
/*     */ import net.craftersland.customenderchest.storage.MysqlSetup;
/*     */ import net.craftersland.customenderchest.storage.MysqlStorage;
/*     */ import net.craftersland.customenderchest.storage.StorageInterface;
/*     */ import net.craftersland.customenderchest.utils.EnderChestUtils;
/*     */ import net.craftersland.customenderchest.utils.ModdedSerializer;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class EnderChest extends JavaPlugin
/*     */ {
/*     */   public static Logger log;
/*  26 */   public Map<Inventory, java.util.UUID> admin = new HashMap();
/*  27 */   public static boolean is19Server = true;
/*  28 */   public static String pluginName = "CustomEnderChest";
/*     */   private static ConfigHandler configHandler;
/*     */   private static StorageInterface storageInterface;
/*     */   private static EnderChestUtils enderchestUtils;
/*     */   private static DataHandler dH;
/*     */   private static MysqlSetup mysqlSetup;
/*     */   private static SoundHandler sH;
/*     */   private static ModdedSerializer ms;
/*     */   private static FileToMysqlCmd ftmc;
/*     */   
/*     */   public void onEnable()
/*     */   {
/*  40 */     log = getLogger();
/*  41 */     getMcVersion();
/*  42 */     configHandler = new ConfigHandler(this);
/*  43 */     checkForModdedNBTsupport();
/*  44 */     enderchestUtils = new EnderChestUtils(this);
/*  45 */     dH = new DataHandler();
/*  46 */     if (configHandler.getString("database.typeOfDatabase").equalsIgnoreCase("mysql")) {
/*  47 */       log.info("Using MySQL database for data.");
/*  48 */       mysqlSetup = new MysqlSetup(this);
/*  49 */       storageInterface = new MysqlStorage(this);
/*     */     } else {
/*  51 */       log.info("Using FlatFile system for data.");
/*  52 */       File pluginFolder = new File("plugins" + System.getProperty("file.separator") + pluginName + System.getProperty("file.separator") + "PlayerData");
/*  53 */       if (!pluginFolder.exists()) {
/*  54 */         pluginFolder.mkdir();
/*     */       }
/*  56 */       storageInterface = new FlatFileStorage(this);
/*     */     }
/*  58 */     sH = new SoundHandler(this);
/*  59 */     ftmc = new FileToMysqlCmd(this);
/*  60 */     PluginManager pm = getServer().getPluginManager();
/*  61 */     pm.registerEvents(new PlayerHandler(this), this);
/*  62 */     CommandHandler cH = new CommandHandler(this);
/*  63 */     getCommand("customec").setExecutor(cH);
/*  64 */     getCommand("ec").setExecutor(cH);
/*  65 */     getCommand("customenderchest").setExecutor(cH);
/*  66 */     log.info(pluginName + " loaded successfully!");
/*     */   }
/*     */   
/*     */   public void onDisable()
/*     */   {
/*  71 */     HandlerList.unregisterAll(this);
/*  72 */     Bukkit.getScheduler().cancelTasks(this);
/*  73 */     if ((configHandler.getString("database.typeOfDatabase").equalsIgnoreCase("mysql")) && 
/*  74 */       (mysqlSetup.getConnection() != null)) {
/*  75 */       mysqlSetup.closeDatabase();
/*     */     }
/*     */     
/*  78 */     log.info(pluginName + " is disabled!");
/*     */   }
/*     */   
/*     */   private boolean getMcVersion() {
/*  82 */     String[] serverVersion = Bukkit.getBukkitVersion().split("-");
/*  83 */     String version = serverVersion[0];
/*     */     
/*  85 */     if ((version.matches("1.7.10")) || (version.matches("1.7.9")) || (version.matches("1.7.5")) || (version.matches("1.7.2")) || (version.matches("1.8.8")) || (version.matches("1.8.3")) || (version.matches("1.8.4")) || (version.matches("1.8"))) {
/*  86 */       is19Server = false;
/*  87 */       return true;
/*     */     }
/*  89 */     return false;
/*     */   }
/*     */   
/*     */   private void checkForModdedNBTsupport() {
/*  93 */     if (configHandler.getBoolean("settings.modded-NBT-data-support")) {
/*  94 */       if (configHandler.getString("database.typeOfDatabase").equalsIgnoreCase("mysql")) {
/*  95 */         if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
/*  96 */           ms = new ModdedSerializer(this);
/*  97 */           log.info("ProtocolLib dependency found. Modded NBT data support is enabled!");
/*     */         } else {
/*  99 */           log.warning("ProtocolLib dependency not found!!! Modded NBT data support is disabled!");
/*     */         }
/*     */       } else {
/* 102 */         log.warning("NBT Modded data support only works for MySQL storage. Modded NBT data support is disabled!");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public ConfigHandler getConfigHandler() {
/* 108 */     return configHandler;
/*     */   }
/*     */   
/* 111 */   public StorageInterface getStorageInterface() { return storageInterface; }
/*     */   
/*     */   public EnderChestUtils getEnderChestUtils() {
/* 114 */     return enderchestUtils;
/*     */   }
/*     */   
/* 117 */   public MysqlSetup getMysqlSetup() { return mysqlSetup; }
/*     */   
/*     */   public SoundHandler getSoundHandler() {
/* 120 */     return sH;
/*     */   }
/*     */   
/* 123 */   public DataHandler getDataHandler() { return dH; }
/*     */   
/*     */   public ModdedSerializer getModdedSerializer() {
/* 126 */     return ms;
/*     */   }
/*     */   
/* 129 */   public FileToMysqlCmd getFileToMysqlCmd() { return ftmc; }
/*     */ }


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/EnderChest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */