
package jdz.blockGlitch.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import jdz.bukkitUtils.events.Listener;
import jdz.bukkitUtils.misc.WorldUtils;
import jdz.bukkitUtils.misc.utils.ItemUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionSelector implements Listener {
	@Data
	public static class Region {
		private final Location l1, l2;

		public void print(Player player) {
			player.sendMessage(ChatColor.LIGHT_PURPLE + "Corner 1: " + WorldUtils.locationToLegibleString(l1));
			player.sendMessage(ChatColor.LIGHT_PURPLE + "Corner 2: " + WorldUtils.locationToLegibleString(l2));
		}
	}

	@Getter private static final RegionSelector instance = new RegionSelector();

	private final Map<Player, Location> locationA = new HashMap<>();
	private final Map<Player, Location> locationB = new HashMap<>();
	private final ItemStack regionWand = createRegionWand();

	public Region getSelectedRegion(Player player) {
		if (!(locationA.containsKey(player) && locationB.containsKey(player)))
			return null;
		return new Region(locationA.get(player), locationB.get(player));
	}

	public void giveWand(Player player) {
		player.getInventory().addItem(regionWand);
	}

	public boolean hasWandInInventory(Player player) {
		for (ItemStack item : player.getInventory())
			if (isWand(item))
				return true;
		return false;
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if (!isHoldingWand(event.getPlayer()))
			return;

		Block target = event.getClickedBlock();
		if (target == null)
			target = event.getPlayer().getTargetBlock((HashSet<Byte>) null, 100);
		if (target == null)
			return;

		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			locationA.put(event.getPlayer(), target.getLocation());
			sendFormattedMessage(event.getPlayer(), target.getLocation(), 1);
		}
		else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			locationB.put(event.getPlayer(), target.getLocation());
			sendFormattedMessage(event.getPlayer(), target.getLocation(), 2);
		}
	}

	protected void sendFormattedMessage(Player player, Location location, int corner) {
		player.sendMessage(ChatColor.LIGHT_PURPLE + "Corner " + corner + " set to "
				+ WorldUtils.locationToLegibleString(location));
	}

	public boolean isHoldingWand(Player player) {
		return isWand(player.getItemInHand());
	}

	private boolean isWand(ItemStack item) {
		return item != null && item.getType() == Material.GOLD_HOE
				&& item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Glitch region wand");
	}

	private ItemStack createRegionWand() {
		ItemStack wand = new ItemStack(Material.GOLD_HOE);
		ItemUtils.setName(wand, ChatColor.GOLD + "Glitch region wand");
		return wand;
	}
}
