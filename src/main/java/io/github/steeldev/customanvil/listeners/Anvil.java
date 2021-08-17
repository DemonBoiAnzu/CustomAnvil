package io.github.steeldev.customanvil.listeners;

import io.github.steeldev.customanvil.CustomAnvil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class Anvil implements Listener {
    CustomAnvil main = CustomAnvil.getInstance();

    @EventHandler
    public void anvilCombine(PrepareAnvilEvent event) {
        ItemStack slot1 = event.getInventory().getItem(0);
        ItemStack slot2 = event.getInventory().getItem(1);
        ItemStack slot3 = event.getResult();

        if (slot1 == null || slot1.getType().equals(Material.AIR)) return;
        if (slot2 == null || slot2.getType().equals(Material.AIR)) return;
        ItemStack resultItem = (slot3 != null && !slot3.getType().equals(Material.AIR)) ? slot3 : slot1.clone();
        ItemMeta meta = resultItem.getItemMeta();
        meta.setDisplayName(event.getInventory().getRenameText());
        resultItem.setItemMeta(meta);
        int finalLevel = 0;
        Map<Enchantment, Integer> slot2Enchants = (slot2.getType().equals(Material.ENCHANTED_BOOK)) ?
                ((EnchantmentStorageMeta) slot2.getItemMeta()).getStoredEnchants() : slot2.getEnchantments();
        Map<Enchantment, Integer> slot1Enchants = (slot2.getType().equals(Material.ENCHANTED_BOOK)) ?
                ((EnchantmentStorageMeta) slot2.getItemMeta()).getStoredEnchants() : slot2.getEnchantments();
        for (Enchantment enchantment : slot2Enchants.keySet()) {
            if (slot1Enchants.containsKey(enchantment)) {
                int newLvl = (main.config.ADD_LEVELS_LITERAL) ?
                        slot1.getEnchantmentLevel(enchantment) + slot2.getEnchantmentLevel(enchantment) :
                        slot1.getEnchantmentLevel(enchantment) + 1;
                finalLevel += newLvl;
                resultItem.removeEnchantment(enchantment);
                resultItem.addUnsafeEnchantment(enchantment, newLvl);
            }
        }
        if (slot1.equals(resultItem)) return;
        event.setResult(resultItem);
        event.getInventory().setRepairCost(event.getInventory().getRepairCost() + finalLevel);
    }
}
