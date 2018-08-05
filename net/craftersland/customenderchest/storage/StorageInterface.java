package net.craftersland.customenderchest.storage;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract interface StorageInterface
{
  public abstract boolean hasDataFile(UUID paramUUID);
  
  public abstract boolean deleteDataFile(UUID paramUUID);
  
  public abstract boolean saveEnderChest(Player paramPlayer, Inventory paramInventory);
  
  public abstract boolean saveEnderChest(UUID paramUUID, Inventory paramInventory);
  
  public abstract boolean loadEnderChest(Player paramPlayer, Inventory paramInventory);
  
  public abstract boolean loadEnderChest(UUID paramUUID, Inventory paramInventory);
  
  public abstract String loadName(UUID paramUUID);
  
  public abstract Integer loadSize(UUID paramUUID);
  
  public abstract void saveEnderChest(UUID paramUUID, Inventory paramInventory, String paramString, int paramInt);
}


/* Location:              /Users/Server/Desktop/Unpacked Jars/CustomEnderChest-v1.8.0.jar!/net/craftersland/customenderchest/storage/StorageInterface.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */