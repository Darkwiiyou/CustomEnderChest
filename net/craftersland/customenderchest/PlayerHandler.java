/*     */ package net.craftersland.customenderchest;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.craftersland.customenderchest.storage.StorageInterface;
/*     */ import net.craftersland.customenderchest.utils.EnderChestUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class PlayerHandler implements org.bukkit.event.Listener
/*     */ {
/*     */   private EnderChest enderchest;
/*  23 */   private Set<UUID> interactCooldown = new java.util.HashSet();
/*     */   
/*     */   public PlayerHandler(EnderChest enderchest) {
/*  26 */     this.enderchest = enderchest;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerJoinEvent(final PlayerJoinEvent e) {
/*  31 */     Bukkit.getScheduler().runTaskAsynchronously(this.enderchest, new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*  35 */         if (e.getPlayer().isOnline()) {
/*  36 */           int size = PlayerHandler.this.enderchest.getEnderChestUtils().getSize(e.getPlayer()).intValue();
/*  37 */           if (size == 0) {
/*  38 */             size = 9;
/*     */           }
/*  40 */           String enderChestTitle = PlayerHandler.this.enderchest.getEnderChestUtils().getTitle(e.getPlayer());
/*  41 */           Inventory inv = Bukkit.getServer().createInventory(e.getPlayer(), size, enderChestTitle);
/*  42 */           if (PlayerHandler.this.enderchest.getStorageInterface().hasDataFile(e.getPlayer().getUniqueId())) {
/*  43 */             PlayerHandler.this.enderchest.getStorageInterface().loadEnderChest(e.getPlayer(), inv);
/*     */           }
/*  45 */           PlayerHandler.this.enderchest.getDataHandler().setData(e.getPlayer().getUniqueId(), inv);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerDisconnectEvent(PlayerQuitEvent e)
/*     */   {
/*  54 */     this.enderchest.getDataHandler().removeData(e.getPlayer().getUniqueId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerInteract(PlayerInteractEvent e)
/*     */   {
/*  60 */     if ((e.getClickedBlock() != null) && 
/*  61 */       (e.getClickedBlock().getType() == Material.ENDER_CHEST) && 
/*  62 */       (!this.enderchest.getConfigHandler().getBoolean("settings.disable-enderchest-click")) && 
/*  63 */       (e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) && 
/*  64 */       (!this.interactCooldown.contains(e.getPlayer().getUniqueId()))) {
/*  65 */       if (!e.getPlayer().isSneaking()) {
/*  66 */         e.setCancelled(true);
/*  67 */         addInteractCooldown(e.getPlayer().getUniqueId());
/*  68 */         this.enderchest.getEnderChestUtils().openMenu(e.getPlayer());
/*     */       }
/*  70 */       else if (!EnderChest.is19Server) {
/*  71 */         if (!hasItemInHand(e.getPlayer().getItemInHand())) {
/*  72 */           e.setCancelled(true);
/*  73 */           addInteractCooldown(e.getPlayer().getUniqueId());
/*  74 */           this.enderchest.getEnderChestUtils().openMenu(e.getPlayer());
/*     */         }
/*     */       }
/*  77 */       else if ((!hasItemInHand(e.getPlayer().getInventory().getItemInMainHand())) && (!hasItemInHand(e.getPlayer().getInventory().getItemInOffHand()))) {
/*  78 */         e.setCancelled(true);
/*  79 */         addInteractCooldown(e.getPlayer().getUniqueId());
/*  80 */         this.enderchest.getEnderChestUtils().openMenu(e.getPlayer());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void addInteractCooldown(final UUID u)
/*     */   {
/*  92 */     this.interactCooldown.add(u);
/*  93 */     Bukkit.getScheduler().runTaskLaterAsynchronously(this.enderchest, new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*  97 */         PlayerHandler.this.interactCooldown.remove(u);
/*     */       }
/*     */       
/* 100 */     }, 2L);
/*     */   }
/*     */   
/*     */   private boolean hasItemInHand(ItemStack item) {
/* 104 */     if (item == null) {
/* 105 */       return false;
/*     */     }
/* 107 */     if (item.getType() == Material.AIR) {
/* 108 */       return false;
/*     */     }
/*     */     
/* 111 */     return true;
/*     */   }
/*     */   
/*     */   private void saveEnderchest(final Inventory inv, final Player p, final UUID u) {
/* 115 */     Bukkit.getScheduler().runTaskAsynchronously(this.enderchest, new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 119 */         if (u == null) {
/* 120 */           PlayerHandler.this.enderchest.getStorageInterface().saveEnderChest(p, inv);
/*     */         } else {
/* 122 */           PlayerHandler.this.enderchest.getStorageInterface().saveEnderChest(u, inv);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @EventHandler
/*     */   public void onInventoryClose(InventoryCloseEvent e)
/*     */   {
/* 133 */     Player p = (Player)e.getPlayer();
/* 134 */     if ((p != null) && 
/* 135 */       (e.getInventory() != null)) {
/*     */       try {
/* 137 */         if (this.enderchest.getDataHandler().isLiveEnderchest(e.getInventory())) {
/* 138 */           this.enderchest.getSoundHandler().sendEnderchestCloseSound(p);
/* 139 */           if (this.enderchest.admin.containsKey(e.getInventory())) {
/* 140 */             UUID u = (UUID)this.enderchest.admin.get(e.getInventory());
/* 141 */             if (!u.equals(e.getPlayer().getUniqueId())) {
/* 142 */               this.enderchest.admin.remove(e.getInventory());
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 147 */             this.enderchest.getDataHandler().setData(e.getPlayer().getUniqueId(), e.getInventory());
/* 148 */             saveEnderchest(e.getInventory(), (Player)e.getPlayer(), null);
/*     */           }
/* 150 */         } else if (this.enderchest.admin.containsKey(e.getInventory())) {
/* 151 */           this.enderchest.getSoundHandler().sendEnderchestCloseSound(p);
/* 152 */           saveEnderchest(e.getInventory(), (Player)e.getPlayer(), (UUID)this.enderchest.admin.get(e.getInventory()));
/* 153 */           this.enderchest.admin.remove(e.getInventory());
/*     */         }
/*     */       } catch (Exception ex) {
/* 156 */         EnderChest.log.severe("Error saving enderchest data for player: " + p.getName() + " . Error: " + ex.getMessage());
/* 157 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/PlayerHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */