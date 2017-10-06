package me.jtghawk137.ce.commands;

import me.jtghawk137.ce.enchants.EnchantmentHandler;
import me.jtghawk137.ce.enchants.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class CECommand implements CommandExecutor
{

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (!commandSender.hasPermission("ce.give"))
        {
            commandSender.sendMessage("§cYou do not have permission to use this command");
            return true;
        }
        if (args.length != 4 && args.length != 2)
        {
            commandSender.sendMessage("§6Usage: §7/ce <player> <item> <enchantment> <level>");
            return true;
        }
        Player player = null;
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (args[0].equalsIgnoreCase(p.getName()))
            {
                player = p;
                break;
            } else
            {
                commandSender.sendMessage("§cPlayer not found!");
                return true;
            }
        }
        if (!args[1].matches("(?i)Sword|Pickaxe|Shovel"))
        {
            commandSender.sendMessage("§cItem name is invalid, please use Sword, Pickaxe, or Shovel!");
            return true;
        }
        Material material = null;
        if (args[1].equalsIgnoreCase("Sword"))
            material = Material.DIAMOND_SWORD;
        if (args[1].equalsIgnoreCase("Pickaxe"))
        {
            material = Material.DIAMOND_PICKAXE;
            player.getInventory().addItem(ItemUtils.customEnchantItem(material, 1, null, 0, player));
            return true;
        }
        if (args[1].equalsIgnoreCase("Shovel"))
        {
            material = Material.DIAMOND_SPADE;
            player.getInventory().addItem(ItemUtils.customEnchantItem(material, 1, null, 0, player));
            return true;
        }
        Enchantment enchantment;
        if (!args[2].matches("(?i)Poison|Pickaxe|Shovel"))
        {
            commandSender.sendMessage("§cEnchantment name is invalid, please use Poison!");
            return true;
        }
        enchantment = EnchantmentHandler.getEnchantmentByName(args[2].toLowerCase());
        int level = Integer.parseInt(args[3]);
        if (level > enchantment.getMaxLevel() || level < enchantment.getStartLevel())
        {
            commandSender.sendMessage("§cThe level is too high or low, please use an number between 1-3");
            return true;
        }
        player.getInventory().addItem(ItemUtils.customEnchantItem(material, 1, enchantment, level, player));
        return true;
    }
}
