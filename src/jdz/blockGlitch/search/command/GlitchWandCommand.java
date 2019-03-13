
package jdz.blockGlitch.search.command;

import org.bukkit.entity.Player;

import jdz.blockGlitch.search.RegionSelector;
import jdz.bukkitUtils.commands.SubCommand;
import jdz.bukkitUtils.commands.annotations.CommandLabel;
import jdz.bukkitUtils.commands.annotations.CommandMethod;


@CommandLabel("wand")
public class GlitchWandCommand extends SubCommand {
	@CommandMethod
	public void execute(Player sender) {
		if (!RegionSelector.getInstance().hasWandInInventory(sender)) {
			RegionSelector.getInstance().giveWand(sender);
			return;
		}
	}
}
