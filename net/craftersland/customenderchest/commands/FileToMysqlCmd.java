/*    */ package net.craftersland.customenderchest.commands;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.logging.Logger;
/*    */ import net.craftersland.customenderchest.ConfigHandler;
/*    */ import net.craftersland.customenderchest.EnderChest;
/*    */ import net.craftersland.customenderchest.storage.StorageInterface;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class FileToMysqlCmd
/*    */ {
/*    */   private EnderChest pl;
/*    */   
/*    */   public FileToMysqlCmd(EnderChest plugin)
/*    */   {
/* 22 */     this.pl = plugin;
/*    */   }
/*    */   
/*    */   public void runCmd(CommandSender sender, final boolean overwrite) {
/* 26 */     if (this.pl.getConfigHandler().getString("database.typeOfDatabase").equalsIgnoreCase("mysql")) {
/* 27 */       if (this.pl.getMysqlSetup().getConnection() != null) {
/* 28 */         final File dataFolder = new File("plugins" + System.getProperty("file.separator") + EnderChest.pluginName + System.getProperty("file.separator") + "PlayerData");
/* 29 */         if (dataFolder.exists()) {
/* 30 */           sender.sendMessage(this.pl.getConfigHandler().getStringWithColor("chatMessages.flatfileImport-started"));
/* 31 */           Bukkit.getScheduler().runTaskAsynchronously(this.pl, new Runnable()
/*    */           {
/*    */             public void run()
/*    */             {
/* 35 */               File[] fileList = dataFolder.listFiles();
/* 36 */               EnderChest.log.info("Starting importing data from flatfile to mysql! " + fileList.length + " files found...");
/* 37 */               int progress = 0;
/* 38 */               File[] arrayOfFile1; int j = (arrayOfFile1 = fileList).length; for (int i = 0; i < j; i++) { File f = arrayOfFile1[i];
/* 39 */                 progress++;
/*    */                 try {
/* 41 */                   if (f.getName().endsWith(".yml")) {
/* 42 */                     java.util.UUID playerUUID = java.util.UUID.fromString(f.getName().substring(0, 36));
/* 43 */                     if (!FileToMysqlCmd.this.pl.getStorageInterface().hasDataFile(playerUUID))
/*    */                     {
/* 45 */                       FileConfiguration ymlFormat = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(f);
/* 46 */                       int invSize = ymlFormat.getInt("EnderChestSize");
/* 47 */                       Inventory inv = Bukkit.createInventory(null, invSize);
/* 48 */                       ArrayList<ItemStack> items = new ArrayList();
/* 49 */                       for (int i = 0; i < inv.getSize(); i++) {
/* 50 */                         ItemStack item = ymlFormat.getItemStack("EnderChestInventory." + i);
/* 51 */                         items.add(item);
/*    */                       }
/* 53 */                       ItemStack[] itemsList = (ItemStack[])items.toArray(new ItemStack[items.size()]);
/* 54 */                       inv.setContents(itemsList);
/* 55 */                       items.clear();
/* 56 */                       FileToMysqlCmd.this.pl.getStorageInterface().saveEnderChest(playerUUID, inv, ymlFormat.getString("PlayerLastName"), invSize);
/* 57 */                     } else if (overwrite)
/*    */                     {
/* 59 */                       FileConfiguration ymlFormat = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(f);
/* 60 */                       int invSize = ymlFormat.getInt("EnderChestSize");
/* 61 */                       Inventory inv = Bukkit.createInventory(null, invSize);
/* 62 */                       ArrayList<ItemStack> items = new ArrayList();
/* 63 */                       for (int i = 0; i < inv.getSize(); i++) {
/* 64 */                         ItemStack item = ymlFormat.getItemStack("EnderChestInventory." + i);
/* 65 */                         items.add(item);
/*    */                       }
/* 67 */                       ItemStack[] itemsList = (ItemStack[])items.toArray(new ItemStack[items.size()]);
/* 68 */                       inv.setContents(itemsList);
/* 69 */                       items.clear();
/* 70 */                       FileToMysqlCmd.this.pl.getStorageInterface().saveEnderChest(playerUUID, inv);
/* 71 */                       Player p = Bukkit.getPlayer(playerUUID);
/* 72 */                       if ((p != null) && 
/* 73 */                         (p.isOnline())) {
/* 74 */                         String enderChestTitle = FileToMysqlCmd.this.pl.getEnderChestUtils().getTitle(p);
/* 75 */                         Inventory invT = Bukkit.getServer().createInventory(p, FileToMysqlCmd.this.pl.getStorageInterface().loadSize(playerUUID).intValue(), enderChestTitle);
/* 76 */                         FileToMysqlCmd.this.pl.getStorageInterface().loadEnderChest(p, invT);
/* 77 */                         FileToMysqlCmd.this.pl.getDataHandler().setData(p.getUniqueId(), invT);
/*    */                       }
/*    */                     }
/*    */                   }
/*    */                 }
/*    */                 catch (Exception e) {
/* 83 */                   EnderChest.log.warning("Failed to import file: " + f.getName() + " .Error: " + e.getMessage());
/*    */                 }
/* 85 */                 EnderChest.log.info("Import progress: " + progress + " / " + fileList.length);
/*    */               }
/* 87 */               EnderChest.log.info("Data import is complete!");
/*    */             }
/*    */           });
/*    */         }
/*    */         else {
/* 92 */           sender.sendMessage(this.pl.getConfigHandler().getStringWithColor("chatMessages.flatfileImport-datafolder"));
/*    */         }
/*    */       } else {
/* 95 */         sender.sendMessage(this.pl.getConfigHandler().getStringWithColor("chatMessages.flatfileImport-connection"));
/*    */       }
/*    */     } else {
/* 98 */       sender.sendMessage(this.pl.getConfigHandler().getStringWithColor("chatMessages.flatfileImport-mysql"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/commands/FileToMysqlCmd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */