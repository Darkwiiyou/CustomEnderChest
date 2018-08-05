/*     */ package net.craftersland.customenderchest.storage;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Logger;
/*     */ import net.craftersland.customenderchest.EnderChest;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class FlatFileStorage implements StorageInterface
/*     */ {
/*     */   private EnderChest enderchest;
/*     */   
/*     */   public FlatFileStorage(EnderChest enderchest)
/*     */   {
/*  20 */     this.enderchest = enderchest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasDataFile(UUID playerUUID)
/*     */   {
/*  27 */     return new File("plugins" + System.getProperty("file.separator") + "CustomEnderChest" + System.getProperty("file.separator") + "PlayerData" + System.getProperty("file.separator") + playerUUID + ".yml").exists();
/*     */   }
/*     */   
/*     */   public boolean createDataFile(UUID playerUUID, Player player)
/*     */   {
/*     */     try {
/*  33 */       File dataFile = new File("plugins" + System.getProperty("file.separator") + "CustomEnderChest" + System.getProperty("file.separator") + "PlayerData", playerUUID + ".yml");
/*  34 */       if (!dataFile.exists()) {
/*  35 */         dataFile.createNewFile();
/*  36 */         FileConfiguration ymlFormat = YamlConfiguration.loadConfiguration(dataFile);
/*  37 */         String playerName = player.getName();
/*  38 */         ymlFormat.set("PlayerLastName", playerName);
/*     */         
/*  40 */         int slots = this.enderchest.getEnderChestUtils().getSize(player).intValue();
/*  41 */         ymlFormat.set("EnderChestSize", Integer.valueOf(slots));
/*  42 */         ymlFormat.save(dataFile);
/*     */         
/*  44 */         return true;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  48 */       this.enderchest.getLogger().severe("Could not create data file " + playerUUID + "!");
/*  49 */       e.printStackTrace();
/*     */     }
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public boolean saveInventory(UUID playerUUID, Player player, Integer size, ItemStack inventory)
/*     */   {
/*  56 */     if (!hasDataFile(playerUUID)) {
/*  57 */       createDataFile(playerUUID, player);
/*     */     }
/*     */     try
/*     */     {
/*  61 */       File dataFile = new File("plugins" + System.getProperty("file.separator") + "CustomEnderChest" + System.getProperty("file.separator") + "PlayerData", playerUUID + ".yml");
/*  62 */       FileConfiguration ymlFormat = YamlConfiguration.loadConfiguration(dataFile);
/*  63 */       String playerName = player.getName();
/*  64 */       ymlFormat.set("PlayerLastName", playerName);
/*     */       
/*  66 */       int slots = this.enderchest.getEnderChestUtils().getSize(player).intValue();
/*  67 */       ymlFormat.set("EnderChestSize", Integer.valueOf(slots));
/*     */       
/*  69 */       ymlFormat.set("EnderChestInventory." + size, inventory);
/*     */       
/*  71 */       ymlFormat.save(dataFile);
/*  72 */       return true;
/*     */     }
/*     */     catch (Exception e) {
/*  75 */       this.enderchest.getLogger().severe("Could not save inventory of " + playerUUID + "!");
/*  76 */       e.printStackTrace();
/*     */     }
/*  78 */     return false;
/*     */   }
/*     */   
/*     */   public boolean saveInventory(UUID playerUUID, Integer size, ItemStack inventory)
/*     */   {
/*     */     try
/*     */     {
/*  85 */       File dataFile = new File("plugins" + System.getProperty("file.separator") + "CustomEnderChest" + System.getProperty("file.separator") + "PlayerData", playerUUID + ".yml");
/*  86 */       FileConfiguration ymlFormat = YamlConfiguration.loadConfiguration(dataFile);
/*     */       
/*  88 */       ymlFormat.set("EnderChestInventory." + size, inventory);
/*     */       
/*  90 */       ymlFormat.save(dataFile);
/*  91 */       return true;
/*     */     }
/*     */     catch (Exception e) {
/*  94 */       this.enderchest.getLogger().severe("Could not save inventory of " + playerUUID + "!");
/*  95 */       e.printStackTrace();
/*     */     }
/*  97 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean saveEnderChest(Player p, Inventory inv)
/*     */   {
/* 103 */     for (int i = 0; i < inv.getSize(); i++) {
/* 104 */       ItemStack item = inv.getContents()[i];
/* 105 */       saveInventory(p.getUniqueId(), p, Integer.valueOf(i), item);
/*     */     }
/* 107 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean saveEnderChest(UUID p, Inventory inv)
/*     */   {
/* 113 */     for (int i = 0; i < inv.getSize(); i++) {
/* 114 */       ItemStack item = inv.getContents()[i];
/* 115 */       saveInventory(p, Integer.valueOf(i), item);
/*     */     }
/* 117 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean loadEnderChest(Player p, Inventory inv)
/*     */   {
/* 123 */     if (!hasDataFile(p.getUniqueId())) {
/* 124 */       createDataFile(p.getUniqueId(), p);
/*     */     }
/* 126 */     File dataFile = new File("plugins" + System.getProperty("file.separator") + "CustomEnderChest" + System.getProperty("file.separator") + "PlayerData", p.getUniqueId() + ".yml");
/* 127 */     FileConfiguration ymlFormat = YamlConfiguration.loadConfiguration(dataFile);
/* 128 */     ArrayList<ItemStack> items = new ArrayList();
/* 129 */     for (int i = 0; i < inv.getSize(); i++) {
/* 130 */       ItemStack item = ymlFormat.getItemStack("EnderChestInventory." + i);
/* 131 */       items.add(item);
/*     */     }
/* 133 */     ItemStack[] itemsList = (ItemStack[])items.toArray(new ItemStack[items.size()]);
/* 134 */     inv.setContents(itemsList);
/* 135 */     items.clear();
/* 136 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean loadEnderChest(UUID playerUUID, Inventory inv)
/*     */   {
/* 142 */     File dataFile = new File("plugins" + System.getProperty("file.separator") + "CustomEnderChest" + System.getProperty("file.separator") + "PlayerData", playerUUID + ".yml");
/* 143 */     FileConfiguration ymlFormat = YamlConfiguration.loadConfiguration(dataFile);
/* 144 */     ArrayList<ItemStack> items = new ArrayList();
/* 145 */     for (int i = 0; i < inv.getSize(); i++) {
/* 146 */       ItemStack item = ymlFormat.getItemStack("EnderChestInventory." + i);
/* 147 */       items.add(item);
/*     */     }
/* 149 */     ItemStack[] itemsList = (ItemStack[])items.toArray(new ItemStack[items.size()]);
/* 150 */     inv.setContents(itemsList);
/* 151 */     items.clear();
/* 152 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public String loadName(UUID playerUUID)
/*     */   {
/* 158 */     if (!hasDataFile(playerUUID)) {
/* 159 */       return null;
/*     */     }
/* 161 */     File dataFile = new File("plugins" + System.getProperty("file.separator") + "CustomEnderChest" + System.getProperty("file.separator") + "PlayerData", playerUUID + ".yml");
/* 162 */     FileConfiguration ymlFormat = YamlConfiguration.loadConfiguration(dataFile);
/* 163 */     String name = ymlFormat.getString("PlayerLastName");
/* 164 */     return name;
/*     */   }
/*     */   
/*     */ 
/*     */   public Integer loadSize(UUID playerUUID)
/*     */   {
/* 170 */     if (!hasDataFile(playerUUID)) {
/* 171 */       return Integer.valueOf(0);
/*     */     }
/* 173 */     File dataFile = new File("plugins" + System.getProperty("file.separator") + "CustomEnderChest" + System.getProperty("file.separator") + "PlayerData", playerUUID + ".yml");
/* 174 */     FileConfiguration ymlFormat = YamlConfiguration.loadConfiguration(dataFile);
/* 175 */     Integer size = Integer.valueOf(ymlFormat.getInt("EnderChestSize"));
/* 176 */     return size;
/*     */   }
/*     */   
/*     */   public boolean deleteDataFile(UUID playerUUID)
/*     */   {
/*     */     try
/*     */     {
/* 183 */       File dataFile = new File("plugins" + System.getProperty("file.separator") + "CustomEnderChest" + System.getProperty("file.separator") + "PlayerData", playerUUID + ".yml");
/* 184 */       if (dataFile.exists())
/*     */       {
/* 186 */         dataFile.delete();
/* 187 */         return true;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 191 */       this.enderchest.getLogger().severe("Could not delete data file " + playerUUID + "!");
/* 192 */       e.printStackTrace();
/*     */     }
/* 194 */     return false;
/*     */   }
/*     */   
/*     */   public void saveEnderChest(UUID uuid, Inventory endInv, String playerName, int invSize) {}
/*     */ }


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/storage/FlatFileStorage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */