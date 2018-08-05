/*     */ package net.craftersland.customenderchest.storage;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Logger;
/*     */ import net.craftersland.customenderchest.ConfigHandler;
/*     */ import net.craftersland.customenderchest.EnderChest;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class MysqlStorage implements StorageInterface
/*     */ {
/*     */   private EnderChest enderchest;
/*     */   
/*     */   public MysqlStorage(EnderChest enderchest)
/*     */   {
/*  21 */     this.enderchest = enderchest;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasDataFile(UUID player)
/*     */   {
/*  27 */     ResultSet result = null;
/*  28 */     PreparedStatement preparedUpdateStatement = null;
/*     */     try {
/*  30 */       String sql = "SELECT `player_uuid` FROM `" + this.enderchest.getConfigHandler().getString("database.mysql.tableName") + "` WHERE `player_uuid` = ?";
/*  31 */       preparedUpdateStatement = this.enderchest.getMysqlSetup().getConnection().prepareStatement(sql);
/*  32 */       preparedUpdateStatement.setString(1, player.toString());
/*  33 */       result = preparedUpdateStatement.executeQuery();
/*  34 */       if (result.next()) {
/*  35 */         return true;
/*     */       }
/*     */     } catch (SQLException e) {
/*  38 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/*  41 */         if (result != null) {
/*  42 */           result.close();
/*     */         }
/*  44 */         if (preparedUpdateStatement != null) {
/*  45 */           preparedUpdateStatement.close();
/*     */         }
/*     */       } catch (Exception e) {
/*  48 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/*  41 */       if (result != null) {
/*  42 */         result.close();
/*     */       }
/*  44 */       if (preparedUpdateStatement != null) {
/*  45 */         preparedUpdateStatement.close();
/*     */       }
/*     */     } catch (Exception e) {
/*  48 */       e.printStackTrace();
/*     */     }
/*     */     
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public boolean deleteDataFile(UUID player)
/*     */   {
/*  56 */     PreparedStatement preparedStatement = null;
/*     */     try {
/*  58 */       String sql = "DELETE FROM `" + this.enderchest.getConfigHandler().getString("database.mysql.tableName") + "` WHERE `player_uuid` =?";
/*  59 */       preparedStatement = this.enderchest.getMysqlSetup().getConnection().prepareStatement(sql);
/*  60 */       preparedStatement.setString(1, String.valueOf(player));
/*  61 */       preparedStatement.executeUpdate();
/*  62 */       return true;
/*     */     } catch (SQLException e) {
/*  64 */       return false;
/*     */     } finally {
/*     */       try {
/*  67 */         if (preparedStatement != null) {
/*  68 */           preparedStatement.close();
/*     */         }
/*     */       } catch (Exception e) {
/*  71 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean createAccount(UUID uuid, Player p) {
/*  77 */     PreparedStatement preparedStatement = null;
/*     */     try {
/*  79 */       String sql = "INSERT INTO `" + this.enderchest.getConfigHandler().getString("database.mysql.tableName") + "`(`player_uuid`, `player_name`, `enderchest_data`, `size`, `last_seen`) " + "VALUES(?, ?, ?, ?, ?)";
/*  80 */       preparedStatement = this.enderchest.getMysqlSetup().getConnection().prepareStatement(sql);
/*  81 */       preparedStatement.setString(1, uuid.toString());
/*  82 */       preparedStatement.setString(2, p.getName());
/*  83 */       preparedStatement.setString(3, "none");
/*  84 */       preparedStatement.setInt(4, 0);
/*  85 */       preparedStatement.setString(5, String.valueOf(System.currentTimeMillis()));
/*  86 */       preparedStatement.executeUpdate();
/*  87 */       return true;
/*     */     } catch (SQLException e) {
/*  89 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/*  92 */         if (preparedStatement != null) {
/*  93 */           preparedStatement.close();
/*     */         }
/*     */       } catch (Exception e) {
/*  96 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  99 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean saveEnderChest(UUID uuid, Inventory endInv)
/*     */   {
/* 107 */     PreparedStatement preparedUpdateStatement = null;
/*     */     try {
/* 109 */       String updateSqlExp = "UPDATE `" + this.enderchest.getConfigHandler().getString("database.mysql.tableName") + "` " + "SET `enderchest_data` = ?" + ", `size` = ?" + " WHERE `player_uuid` = ?";
/* 110 */       preparedUpdateStatement = this.enderchest.getMysqlSetup().getConnection().prepareStatement(updateSqlExp);
/* 111 */       preparedUpdateStatement.setString(1, encodeInventory(endInv, uuid.toString()));
/* 112 */       preparedUpdateStatement.setInt(2, endInv.getSize());
/* 113 */       preparedUpdateStatement.setString(3, uuid.toString());
/* 114 */       preparedUpdateStatement.executeUpdate();
/* 115 */       return true;
/*     */     } catch (SQLException e) {
/* 117 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 120 */         if (preparedUpdateStatement != null) {
/* 121 */           preparedUpdateStatement.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 124 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 127 */     return false;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public void saveEnderChest(UUID uuid, Inventory endInv, String playerName, int invSize)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore 5
/*     */     //   3: new 23	java/lang/StringBuilder
/*     */     //   6: dup
/*     */     //   7: ldc -123
/*     */     //   9: invokespecial 27	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   12: aload_0
/*     */     //   13: getfield 15	net/craftersland/customenderchest/storage/MysqlStorage:enderchest	Lnet/craftersland/customenderchest/EnderChest;
/*     */     //   16: invokevirtual 30	net/craftersland/customenderchest/EnderChest:getConfigHandler	()Lnet/craftersland/customenderchest/ConfigHandler;
/*     */     //   19: ldc 36
/*     */     //   21: invokevirtual 38	net/craftersland/customenderchest/ConfigHandler:getString	(Ljava/lang/String;)Ljava/lang/String;
/*     */     //   24: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   27: ldc -121
/*     */     //   29: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   32: ldc -119
/*     */     //   34: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   37: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   40: astore 6
/*     */     //   42: aload_0
/*     */     //   43: getfield 15	net/craftersland/customenderchest/storage/MysqlStorage:enderchest	Lnet/craftersland/customenderchest/EnderChest;
/*     */     //   46: invokevirtual 54	net/craftersland/customenderchest/EnderChest:getMysqlSetup	()Lnet/craftersland/customenderchest/storage/MysqlSetup;
/*     */     //   49: invokevirtual 58	net/craftersland/customenderchest/storage/MysqlSetup:getConnection	()Ljava/sql/Connection;
/*     */     //   52: aload 6
/*     */     //   54: invokeinterface 64 2 0
/*     */     //   59: astore 5
/*     */     //   61: aload 5
/*     */     //   63: iconst_1
/*     */     //   64: aload_1
/*     */     //   65: invokevirtual 70	java/util/UUID:toString	()Ljava/lang/String;
/*     */     //   68: invokeinterface 73 3 0
/*     */     //   73: aload 5
/*     */     //   75: iconst_2
/*     */     //   76: aload_3
/*     */     //   77: invokeinterface 73 3 0
/*     */     //   82: aload 5
/*     */     //   84: iconst_3
/*     */     //   85: aload_0
/*     */     //   86: aload_2
/*     */     //   87: aload_1
/*     */     //   88: invokevirtual 70	java/util/UUID:toString	()Ljava/lang/String;
/*     */     //   91: invokespecial 174	net/craftersland/customenderchest/storage/MysqlStorage:encodeInventory	(Lorg/bukkit/inventory/Inventory;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   94: invokeinterface 73 3 0
/*     */     //   99: aload 5
/*     */     //   101: iconst_4
/*     */     //   102: iload 4
/*     */     //   104: invokeinterface 146 3 0
/*     */     //   109: aload 5
/*     */     //   111: iconst_5
/*     */     //   112: invokestatic 150	java/lang/System:currentTimeMillis	()J
/*     */     //   115: invokestatic 156	java/lang/String:valueOf	(J)Ljava/lang/String;
/*     */     //   118: invokeinterface 73 3 0
/*     */     //   123: aload 5
/*     */     //   125: invokeinterface 126 1 0
/*     */     //   130: pop
/*     */     //   131: goto +62 -> 193
/*     */     //   134: astore 6
/*     */     //   136: aload 6
/*     */     //   138: invokevirtual 98	java/sql/SQLException:printStackTrace	()V
/*     */     //   141: aload 5
/*     */     //   143: ifnull +72 -> 215
/*     */     //   146: aload 5
/*     */     //   148: invokeinterface 92 1 0
/*     */     //   153: goto +62 -> 215
/*     */     //   156: astore 8
/*     */     //   158: aload 8
/*     */     //   160: invokevirtual 93	java/lang/Exception:printStackTrace	()V
/*     */     //   163: goto +52 -> 215
/*     */     //   166: astore 7
/*     */     //   168: aload 5
/*     */     //   170: ifnull +20 -> 190
/*     */     //   173: aload 5
/*     */     //   175: invokeinterface 92 1 0
/*     */     //   180: goto +10 -> 190
/*     */     //   183: astore 8
/*     */     //   185: aload 8
/*     */     //   187: invokevirtual 93	java/lang/Exception:printStackTrace	()V
/*     */     //   190: aload 7
/*     */     //   192: athrow
/*     */     //   193: aload 5
/*     */     //   195: ifnull +20 -> 215
/*     */     //   198: aload 5
/*     */     //   200: invokeinterface 92 1 0
/*     */     //   205: goto +10 -> 215
/*     */     //   208: astore 8
/*     */     //   210: aload 8
/*     */     //   212: invokevirtual 93	java/lang/Exception:printStackTrace	()V
/*     */     //   215: return
/*     */     // Line number table:
/*     */     //   Java source line #132	-> byte code offset #0
/*     */     //   Java source line #134	-> byte code offset #3
/*     */     //   Java source line #135	-> byte code offset #42
/*     */     //   Java source line #136	-> byte code offset #61
/*     */     //   Java source line #137	-> byte code offset #73
/*     */     //   Java source line #138	-> byte code offset #82
/*     */     //   Java source line #139	-> byte code offset #99
/*     */     //   Java source line #140	-> byte code offset #109
/*     */     //   Java source line #141	-> byte code offset #123
/*     */     //   Java source line #142	-> byte code offset #131
/*     */     //   Java source line #143	-> byte code offset #136
/*     */     //   Java source line #146	-> byte code offset #141
/*     */     //   Java source line #147	-> byte code offset #146
/*     */     //   Java source line #149	-> byte code offset #153
/*     */     //   Java source line #150	-> byte code offset #158
/*     */     //   Java source line #144	-> byte code offset #166
/*     */     //   Java source line #146	-> byte code offset #168
/*     */     //   Java source line #147	-> byte code offset #173
/*     */     //   Java source line #149	-> byte code offset #180
/*     */     //   Java source line #150	-> byte code offset #185
/*     */     //   Java source line #152	-> byte code offset #190
/*     */     //   Java source line #146	-> byte code offset #193
/*     */     //   Java source line #147	-> byte code offset #198
/*     */     //   Java source line #149	-> byte code offset #205
/*     */     //   Java source line #150	-> byte code offset #210
/*     */     //   Java source line #153	-> byte code offset #215
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	216	0	this	MysqlStorage
/*     */     //   0	216	1	uuid	UUID
/*     */     //   0	216	2	endInv	Inventory
/*     */     //   0	216	3	playerName	String
/*     */     //   0	216	4	invSize	int
/*     */     //   1	198	5	preparedStatement	PreparedStatement
/*     */     //   40	13	6	sql	String
/*     */     //   134	3	6	e	SQLException
/*     */     //   166	25	7	localObject	Object
/*     */     //   156	3	8	e	Exception
/*     */     //   183	3	8	e	Exception
/*     */     //   208	3	8	e	Exception
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   3	131	134	java/sql/SQLException
/*     */     //   141	153	156	java/lang/Exception
/*     */     //   3	141	166	finally
/*     */     //   168	180	183	java/lang/Exception
/*     */     //   193	205	208	java/lang/Exception
/*     */   }
/*     */   
/*     */   public boolean saveEnderChest(Player p, Inventory endInv)
/*     */   {
/* 157 */     if (!hasDataFile(p.getUniqueId())) {
/* 158 */       createAccount(p.getUniqueId(), p);
/*     */     }
/* 160 */     PreparedStatement preparedUpdateStatement = null;
/*     */     try {
/* 162 */       String updateSqlExp = "UPDATE `" + this.enderchest.getConfigHandler().getString("database.mysql.tableName") + "` " + "SET `player_name` = ?" + ", `enderchest_data` = ?" + ", `size` = ?" + ", `last_seen` = ?" + " WHERE `player_uuid` = ?";
/* 163 */       preparedUpdateStatement = this.enderchest.getMysqlSetup().getConnection().prepareStatement(updateSqlExp);
/* 164 */       preparedUpdateStatement.setString(1, p.getName());
/* 165 */       preparedUpdateStatement.setString(2, encodeInventory(endInv, p.getName()));
/* 166 */       preparedUpdateStatement.setInt(3, endInv.getSize());
/* 167 */       preparedUpdateStatement.setString(4, String.valueOf(System.currentTimeMillis()));
/* 168 */       preparedUpdateStatement.setString(5, p.getUniqueId().toString());
/* 169 */       preparedUpdateStatement.executeUpdate();
/* 170 */       return true;
/*     */     } catch (SQLException e) {
/* 172 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 175 */         if (preparedUpdateStatement != null) {
/* 176 */           preparedUpdateStatement.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 179 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 182 */     return false;
/*     */   }
/*     */   
/*     */   public boolean loadEnderChest(UUID uuid, Inventory endInv)
/*     */   {
/* 187 */     if (!hasDataFile(uuid)) {
/* 188 */       createAccount(uuid, null);
/*     */     }
/* 190 */     PreparedStatement preparedUpdateStatement = null;
/* 191 */     ResultSet result = null;
/*     */     try {
/* 193 */       String sql = "SELECT * FROM `" + this.enderchest.getConfigHandler().getString("database.mysql.tableName") + "` WHERE `player_uuid` = ?";
/* 194 */       preparedUpdateStatement = this.enderchest.getMysqlSetup().getConnection().prepareStatement(sql);
/* 195 */       preparedUpdateStatement.setString(1, uuid.toString());
/* 196 */       result = preparedUpdateStatement.executeQuery();
/* 197 */       if (result.next()) {
/*     */         try {
/* 199 */           Inventory mysqlInv = decodeInventory(result.getString("enderchest_data"), uuid.toString(), result.getInt("size"));
/* 200 */           for (int i = 0; i < endInv.getSize(); i++) {
/* 201 */             ItemStack item = mysqlInv.getItem(i);
/* 202 */             endInv.setItem(i, item);
/*     */           }
/* 204 */           return true;
/*     */         } catch (Exception e) {
/* 206 */           return false;
/*     */         }
/*     */       }
/*     */     } catch (SQLException e) {
/* 210 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 213 */         if (result != null) {
/* 214 */           result.close();
/*     */         }
/* 216 */         if (preparedUpdateStatement != null) {
/* 217 */           preparedUpdateStatement.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 220 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 223 */     return false;
/*     */   }
/*     */   
/*     */   public boolean loadEnderChest(Player p, Inventory endInv)
/*     */   {
/* 228 */     if (!hasDataFile(p.getUniqueId())) {
/* 229 */       createAccount(p.getUniqueId(), p);
/*     */     }
/* 231 */     PreparedStatement preparedUpdateStatement = null;
/* 232 */     ResultSet result = null;
/*     */     try {
/* 234 */       String sql = "SELECT * FROM `" + this.enderchest.getConfigHandler().getString("database.mysql.tableName") + "` WHERE `player_uuid` = ?";
/* 235 */       preparedUpdateStatement = this.enderchest.getMysqlSetup().getConnection().prepareStatement(sql);
/* 236 */       preparedUpdateStatement.setString(1, p.getUniqueId().toString());
/* 237 */       result = preparedUpdateStatement.executeQuery();
/* 238 */       if (result.next()) {
/*     */         try {
/* 240 */           Inventory mysqlInv = decodeInventory(result.getString("enderchest_data"), p.getName(), result.getInt("size"));
/*     */           
/* 242 */           for (int i = 0; i < endInv.getSize(); i++) {
/* 243 */             ItemStack item = mysqlInv.getItem(i);
/* 244 */             endInv.setItem(i, item);
/*     */           }
/*     */           
/*     */ 
/* 248 */           return true;
/*     */         } catch (Exception e) {
/* 250 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (SQLException localSQLException) {}finally
/*     */     {
/*     */       try {
/* 257 */         if (result != null) {
/* 258 */           result.close();
/*     */         }
/* 260 */         if (preparedUpdateStatement != null) {
/* 261 */           preparedUpdateStatement.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 264 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 267 */     return false;
/*     */   }
/*     */   
/*     */   private Inventory decodeInventory(String rawData, String playerName, int chestSize) {
/* 271 */     if (this.enderchest.getModdedSerializer() != null) {
/*     */       try {
/* 273 */         ItemStack[] items = this.enderchest.getModdedSerializer().fromBase64(rawData);
/* 274 */         Inventory inv = org.bukkit.Bukkit.getServer().createInventory(null, chestSize);
/* 275 */         inv.setContents(items);
/* 276 */         return inv;
/*     */       } catch (Exception e) {
/* 278 */         e.printStackTrace();
/*     */         try {
/* 280 */           return net.craftersland.customenderchest.utils.EncodingUtil.fromBase64(rawData);
/*     */         } catch (Exception ex) {
/* 282 */           EnderChest.log.severe("Failed to decode inventory for " + playerName + "! Error: " + ex.getMessage());
/* 283 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     } else {
/*     */       try {
/* 288 */         return net.craftersland.customenderchest.utils.EncodingUtil.fromBase64(rawData);
/*     */       } catch (Exception e) {
/* 290 */         EnderChest.log.severe("Failed to decode inventory for " + playerName + "! Error: " + e.getMessage());
/* 291 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 294 */     return null;
/*     */   }
/*     */   
/*     */   private String encodeInventory(Inventory inv, String playerName) {
/* 298 */     if (this.enderchest.getModdedSerializer() != null) {
/*     */       try {
/* 300 */         return this.enderchest.getModdedSerializer().toBase64(inv.getContents());
/*     */       } catch (Exception e) {
/* 302 */         EnderChest.log.severe("Failed to save enderchest data for " + playerName + "! Error: " + e.getMessage());
/* 303 */         e.printStackTrace();
/*     */       }
/*     */     } else {
/*     */       try {
/* 307 */         return net.craftersland.customenderchest.utils.EncodingUtil.toBase64(inv);
/*     */       } catch (Exception e) {
/* 309 */         EnderChest.log.severe("Failed to save enderchest data for " + playerName + "! Error: " + e.getMessage());
/* 310 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 313 */     return null;
/*     */   }
/*     */   
/*     */   public String loadName(UUID uuid) {
/* 317 */     if (!hasDataFile(uuid)) {
/* 318 */       createAccount(uuid, null);
/*     */     }
/* 320 */     PreparedStatement preparedUpdateStatement = null;
/* 321 */     ResultSet result = null;
/*     */     try {
/* 323 */       String sql = "SELECT `player_name` FROM `" + this.enderchest.getConfigHandler().getString("database.mysql.tableName") + "` WHERE `player_uuid` = ?";
/* 324 */       preparedUpdateStatement = this.enderchest.getMysqlSetup().getConnection().prepareStatement(sql);
/* 325 */       preparedUpdateStatement.setString(1, uuid.toString());
/* 326 */       result = preparedUpdateStatement.executeQuery();
/* 327 */       if (result.next()) {
/* 328 */         return result.getString("player_name");
/*     */       }
/*     */     } catch (SQLException e) {
/* 331 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 334 */         if (result != null) {
/* 335 */           result.close();
/*     */         }
/* 337 */         if (preparedUpdateStatement != null) {
/* 338 */           preparedUpdateStatement.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 341 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 334 */       if (result != null) {
/* 335 */         result.close();
/*     */       }
/* 337 */       if (preparedUpdateStatement != null) {
/* 338 */         preparedUpdateStatement.close();
/*     */       }
/*     */     } catch (Exception e) {
/* 341 */       e.printStackTrace();
/*     */     }
/*     */     
/* 344 */     return null;
/*     */   }
/*     */   
/*     */   public Integer loadSize(UUID uuid) {
/* 348 */     if (!hasDataFile(uuid)) {
/* 349 */       createAccount(uuid, null);
/*     */     }
/* 351 */     PreparedStatement preparedUpdateStatement = null;
/* 352 */     ResultSet result = null;
/*     */     try {
/* 354 */       String sql = "SELECT `size` FROM `" + this.enderchest.getConfigHandler().getString("database.mysql.tableName") + "` WHERE `player_uuid` = ?";
/* 355 */       preparedUpdateStatement = this.enderchest.getMysqlSetup().getConnection().prepareStatement(sql);
/* 356 */       preparedUpdateStatement.setString(1, uuid.toString());
/* 357 */       result = preparedUpdateStatement.executeQuery();
/* 358 */       if (result.next()) {
/* 359 */         return Integer.valueOf(result.getInt("size"));
/*     */       }
/*     */     } catch (SQLException e) {
/* 362 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 365 */         if (result != null) {
/* 366 */           result.close();
/*     */         }
/* 368 */         if (preparedUpdateStatement != null) {
/* 369 */           preparedUpdateStatement.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 372 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 365 */       if (result != null) {
/* 366 */         result.close();
/*     */       }
/* 368 */       if (preparedUpdateStatement != null) {
/* 369 */         preparedUpdateStatement.close();
/*     */       }
/*     */     } catch (Exception e) {
/* 372 */       e.printStackTrace();
/*     */     }
/*     */     
/* 375 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/storage/MysqlStorage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */