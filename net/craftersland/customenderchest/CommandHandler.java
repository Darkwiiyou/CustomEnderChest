
package net.craftersland.customenderchest;


import java.util.UUID;
/*     */ import net.craftersland.customenderchest.storage.StorageInterface;
/*     */ import net.craftersland.customenderchest.utils.EnderChestUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;

 public class CommandHandler implements org.bukkit.command.CommandExecutor {
     private EnderChest enderchest;


    public CommandHandler(EnderChest enderchest) {

        this.enderchest = enderchest;

    }

    public boolean onCommand(CommandSender sender, Command command, String cmdlabel, String[] args) {
        Player p = null;
        if ((cmdlabel.equalsIgnoreCase("customec")) || (cmdlabel.equalsIgnoreCase("customenderchest")) || (cmdlabel.equalsIgnoreCase("ec") || (cmdlabel.equalsIgnoreCase("echest")))) {
            if (args.length == 0 && !cmdlabel.equalsIgnoreCase("echest")) {
                if ((sender instanceof Player)) {
                    p = (Player) sender;
                    sendHelp(p);
                    return true;
                }
                sendConsoleHelp(sender);
                return false;
            }
            if (args.length == 0 && cmdlabel.equalsIgnoreCase("echest")) {
                if ((cmdlabel.equalsIgnoreCase("echest"))) {
                    if ((sender instanceof Player)) {
                        p = (Player) sender;
                        if ((p.hasPermission("CustomEnderChest.commands")) || (p.hasPermission("CustomEnderChest.admin"))) {
                            int size = this.enderchest.getEnderChestUtils().getSize(p).intValue();
                            if (size == 0) {
                                this.enderchest.getConfigHandler().printMessage(p, "chatMessages.noPermission");
                                this.enderchest.getSoundHandler().sendFailedSound(p);
                                return false;
                            }
                            this.enderchest.getEnderChestUtils().openMenu(p);
                            return true;
                        }
                        this.enderchest.getSoundHandler().sendFailedSound(p);
                        this.enderchest.getConfigHandler().printMessage(p, "chatMessages.noPermission");
                        return false;
                    }
                    sender.sendMessage(ChatColor.DARK_RED + ">> " + ChatColor.RED + "You can't run this command by console!");
                    return false;
                }
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("importfromflatfile")) {
                    if ((sender instanceof Player)) {
                        p = (Player) sender;
                        if (!p.hasPermission("CustomEnderChest.admin")) {
                            this.enderchest.getSoundHandler().sendFailedSound(p);
                            this.enderchest.getConfigHandler().printMessage(p, "chatMessages.noPermission");
                            return true;
                        }
                    }
                    this.enderchest.getFileToMysqlCmd().runCmd(sender, false);
                } else {
                    if (args[0].equalsIgnoreCase("delete")) {
                        if ((sender instanceof Player)) {
                            p = (Player) sender;
                            if (p.hasPermission("CustomEnderChest.admin")) {
                                /*  77 */
                                this.enderchest.getSoundHandler().sendFailedSound(p);
                                /*  78 */
                                this.enderchest.getConfigHandler().printMessage(p, "chatMessages.deleteCmdUsage");
                                /*  79 */
                                return true;
                            }
                            this.enderchest.getSoundHandler().sendFailedSound(p);
                            this.enderchest.getConfigHandler().printMessage(p, "chatMessages.noPermission");
                            return true;
                        }
                        sender.sendMessage(ChatColor.DARK_RED + ">> " + ChatColor.RED + "Usage example: " + ChatColor.GRAY + "/customec delete John" + ChatColor.RED + " or " + ChatColor.GRAY + "/customec delete f694517d-d6cf-32f1-972b-dfc677ceac45");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("reload")) {
                        if ((sender instanceof Player)) {
                            p = (Player) sender;
                            if (p.hasPermission("CustomEnderChest.admin")) {
                                try {
                                    this.enderchest.getConfig().load(new java.io.File("plugins" + System.getProperty("file.separator") + "CustomEnderChest" + System.getProperty("file.separator") + "config.yml"));
                                } catch (Exception e) {
                                    this.enderchest.getConfigHandler().printMessage(p, "chatMessages.reloadFail");
                                    this.enderchest.getSoundHandler().sendFailedSound(p);
                                    e.printStackTrace();
                                    return true;
                                }
                                this.enderchest.getSoundHandler().sendCompleteSound(p);
                                this.enderchest.getConfigHandler().printMessage(p, "chatMessages.reload");
                                return true;
                            }
                            this.enderchest.getSoundHandler().sendFailedSound(p);
                            this.enderchest.getConfigHandler().printMessage(p, "chatMessages.noPermission");
                            return true;
                        }
                        try {
                            this.enderchest.getConfig().load(new java.io.File("plugins" + System.getProperty("file.separator") + "CustomEnderChest" + System.getProperty("file.separator") + "config.yml"));
                        } catch (Exception e) {
                            sender.sendMessage(ChatColor.DARK_RED + ">> " + ChatColor.RED + "Could not load config! Check logs!");
                            e.printStackTrace();
                            return false;
                        }
                        sender.sendMessage(ChatColor.DARK_GREEN + ">> " + ChatColor.GREEN + "Configuration reloaded!");
                        return true;
                    }
                    if ((sender instanceof Player)) {
                        p = (Player) sender;
                        sendHelp(p);
                        return false;
                    }
                    sendConsoleHelp(sender);
                    return false;
                }
            }
// ------=-----------------------------
            else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("open")) {
                    if ((sender instanceof Player)) {
                        p = (Player) sender;
                        if (p.hasPermission("CustomEnderChest.admin")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target != null) {
                                if (target.isOnline()) {
                                    if (!this.enderchest.getStorageInterface().hasDataFile(target.getUniqueId())) {
                                        this.enderchest.getSoundHandler().sendFailedSound(p);
                                        this.enderchest.getConfigHandler().printMessage(p, "chatMessages.noEnderchest");
                                        return false;
                                    }
                                    Inventory inv = this.enderchest.getDataHandler().getData(target.getUniqueId());

                                    this.enderchest.getSoundHandler().sendEnderchestOpenSound(p);

                                    this.enderchest.admin.put(inv, target.getUniqueId());

                                    p.openInventory(inv);

                                    return true;

                                }

                            } else {

                                try {

                                    UUID targetUUID = UUID.fromString(args[1]);

                                    if (!this.enderchest.getStorageInterface().hasDataFile(targetUUID)) {

                                        this.enderchest.getSoundHandler().sendFailedSound(p);

                                        this.enderchest.getConfigHandler().printMessage(p, "chatMessages.openUuidFail");

                                        return false;

                                    }

                                    int size = this.enderchest.getStorageInterface().loadSize(targetUUID).intValue();

                                    String enderChestTitle = this.enderchest.getEnderChestUtils().getCmdTitle(targetUUID);

                                    Inventory inv = Bukkit.getServer().createInventory(p, size, enderChestTitle);

                                    this.enderchest.getStorageInterface().loadEnderChest(targetUUID, inv);

                                    this.enderchest.getSoundHandler().sendEnderchestOpenSound(p);

                                    this.enderchest.admin.put(inv, targetUUID);

                                    p.openInventory(inv);

                                    return true;

                                } catch (Exception e) {

                                    this.enderchest.getSoundHandler().sendFailedSound(p);

                                    this.enderchest.getConfigHandler().printMessage(p, "chatMessages.openNameOffline");

                                    return false;

                                }

                            }

                        }

                        this.enderchest.getSoundHandler().sendFailedSound(p);

                        this.enderchest.getConfigHandler().printMessage(p, "chatMessages.noPermission");

                        return false;

                    }

                    sender.sendMessage(ChatColor.DARK_RED + ">> " + ChatColor.RED + "You can't run this command by console!");

                    return false;

                }

                if (args[0].equalsIgnoreCase("importfromflatfile")) {

                    if (args[1].equalsIgnoreCase("overwrite")) {

                        if ((sender instanceof Player)) {

                            p = (Player) sender;

                            if (!p.hasPermission("CustomEnderChest.admin")) {

                                this.enderchest.getSoundHandler().sendFailedSound(p);

                                this.enderchest.getConfigHandler().printMessage(p, "chatMessages.noPermission");

                                return true;

                            }

                        }

                        this.enderchest.getFileToMysqlCmd().runCmd(sender, true);

                    } else {

                        sender.sendMessage("Usage: /customec importfromflatfile overwrite");

                    }

                } else if (args[0].equalsIgnoreCase("delete")) {

                    if ((sender instanceof Player)) {

                        p = (Player) sender;

                        if (p.hasPermission("CustomEnderChest.admin")) {

                            Player target = Bukkit.getPlayer(args[1]);

                            if (target != null) {

                                if (target.isOnline()) {

                                    if (!this.enderchest.getStorageInterface().hasDataFile(target.getUniqueId())) {

                                        this.enderchest.getSoundHandler().sendFailedSound(p);

                                        this.enderchest.getConfigHandler().printMessage(p, "chatMessages.noEnderchest");

                                        return false;

                                    }

                                    this.enderchest.getStorageInterface().deleteDataFile(target.getUniqueId());

                                    this.enderchest.getSoundHandler().sendCompleteSound(p);

                                    this.enderchest.getConfigHandler().printMessage(p, "chatMessages.delete");

                                    return true;

                                }

                            } else {

                                try {

                                    UUID targetUUID = UUID.fromString(args[1]);

                                    if (!this.enderchest.getStorageInterface().hasDataFile(targetUUID)) {

                                        this.enderchest.getSoundHandler().sendFailedSound(p);

                                        this.enderchest.getConfigHandler().printMessage(p, "chatMessages.openUuidFail");

                                        return false;

                                    }

                                    this.enderchest.getConfigHandler().printMessage(p, "chatMessages.delete");

                                    this.enderchest.getStorageInterface().deleteDataFile(targetUUID);

                                    this.enderchest.getSoundHandler().sendCompleteSound(p);

                                    return true;

                                } catch (Exception e) {

                                    this.enderchest.getSoundHandler().sendFailedSound(p);

                                    this.enderchest.getConfigHandler().printMessage(p, "chatMessages.deleteNameOffline");

                                    return false;

                                }

                            }

                        }

                        this.enderchest.getSoundHandler().sendFailedSound(p);

                        this.enderchest.getConfigHandler().printMessage(p, "chatMessages.noPermission");

                        return false;

                    }

                    Player target = Bukkit.getPlayer(args[1]);

                    if (target != null) {

                        if (target.isOnline()) {

                            if (!this.enderchest.getStorageInterface().hasDataFile(target.getUniqueId())) {

                                sender.sendMessage(ChatColor.DARK_RED + ">> " + ChatColor.RED + "Player does not have and Ender Chest yet!");

                                return false;

                            }

                            this.enderchest.getStorageInterface().deleteDataFile(target.getUniqueId());

                            sender.sendMessage(ChatColor.DARK_GREEN + ">> " + ChatColor.GREEN + "Player " + ChatColor.GRAY + target.getName() + ChatColor.GREEN + " enderchest data removed!");

                            return true;

                        }

                    } else {

                        try {

                            UUID targetUUID = UUID.fromString(args[1]);

                            if (!this.enderchest.getStorageInterface().hasDataFile(targetUUID)) {

                                sender.sendMessage(ChatColor.DARK_RED + ">> " + ChatColor.RED + "Player does not have and Ender Chest or wrong UUID!");

                                return false;

                            }

                            sender.sendMessage(ChatColor.DARK_GREEN + ">> " + ChatColor.GREEN + "Player " + ChatColor.GRAY + this.enderchest.getStorageInterface().loadName(targetUUID) + ChatColor.GREEN + " enderchest data removed!");

                            this.enderchest.getStorageInterface().deleteDataFile(targetUUID);

                            return true;

                        } catch (Exception e) {

                            sender.sendMessage(ChatColor.DARK_RED + ">> " + ChatColor.RED + "Player offline or wrong UUID! Use: " + ChatColor.GRAY + "/customec delete <playerUUID>");

                            return false;

                        }

                    }

                } else {

                    if ((sender instanceof Player)) {

                        p = (Player) sender;

                        sendHelp(p);

                        return false;

                    }

                    sendConsoleHelp(sender);
                    return false;

                }

            }

        }

        return false;

    }

    public void sendHelp(Player p) {

        this.enderchest.getSoundHandler().sendAnvilLandSound(p);

        for (String h : this.enderchest.getConfigHandler().getStringList("chatMessages.Help.header")) {

            p.sendMessage(h.replaceAll("&", "§"));

        }

        if (p.hasPermission("CustomEnderChest.admin")) {

            for (String h : this.enderchest.getConfigHandler().getStringList("chatMessages.Help.admin")) {

                p.sendMessage(h.replaceAll("&", "§"));

            }

        } else {

            for (String h : this.enderchest.getConfigHandler().getStringList("chatMessages.Help.user")) {

                p.sendMessage(h.replaceAll("&", "§"));

            }

            if (p.hasPermission("CustomEnderChest.commands")) {

                for (String h : this.enderchest.getConfigHandler().getStringList("chatMessages.Help.command")) {

                    p.sendMessage(h.replaceAll("&", "§"));

                }

            }

            for (String h : this.enderchest.getConfigHandler().getStringList("chatMessages.Help.userFooter")) {

                p.sendMessage(h.replaceAll("&", "§"));

            }

        }

    }

    public void sendConsoleHelp(CommandSender sender) {

        sender.sendMessage(" ");

        sender.sendMessage(ChatColor.DARK_PURPLE + "-=-=-=-=-=-=-=-< " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "CustomEnderChest" + ChatColor.DARK_PURPLE + " >-=-=-=-=-=-=-=-=-");

        sender.sendMessage(" ");

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "        Delete other player's Ender Chest:");

        sender.sendMessage(ChatColor.DARK_GRAY + ">> " + ChatColor.WHITE + "/customec delete <playerName>" + ChatColor.GRAY + " - for online players.");

        sender.sendMessage(ChatColor.DARK_GRAY + ">> " + ChatColor.WHITE + "/customec delete <playerUUID>" + ChatColor.GRAY + " - for offline players.");

        sender.sendMessage(ChatColor.GRAY + "      Example: " + ChatColor.WHITE + "/customec delete John" + ChatColor.GRAY + " or " + ChatColor.WHITE + "/customec delete f694517d-d6cf-32f1-972b-dfc677ceac45");

        sender.sendMessage(" ");

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "        Reload plugin config:");

        sender.sendMessage(ChatColor.DARK_GRAY + ">> " + ChatColor.WHITE + "/customec reload");

        sender.sendMessage(" ");

        sender.sendMessage(ChatColor.DARK_PURPLE + "-=-=-=-=-=-=-=-=-< " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Console Help Page" + ChatColor.DARK_PURPLE + " >-=-=-=-=-=-=-=-=-");

        sender.sendMessage(" ");

    }
}