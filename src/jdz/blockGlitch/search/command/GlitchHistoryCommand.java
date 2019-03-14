
package jdz.blockGlitch.search.command;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;

import jdz.blockGlitch.data.DataSetMetrics;
import jdz.blockGlitch.data.GlitchData;
import jdz.blockGlitch.data.GlitchDatabase;
import jdz.blockGlitch.search.RegionSelector;
import jdz.blockGlitch.search.RegionSelector.Region;
import jdz.bukkitUtils.commands.SubCommand;
import jdz.bukkitUtils.commands.annotations.CommandLabel;
import jdz.bukkitUtils.commands.annotations.CommandMethod;


@CommandLabel("history")
public class GlitchHistoryCommand extends SubCommand {
	@CommandMethod
	public void execute(Player sender) {
		Region region = RegionSelector.getInstance().getSelectedRegion(sender);
		if (region == null) {
			sender.sendMessage(RED + "you must select a region first!");
			return;
		}

		List<GlitchData> history = GlitchDatabase.getInside(region);
		sender.sendMessage(LIGHT_PURPLE + "" + history.size() + " entries found");
		sender.sendMessage(LIGHT_PURPLE + "Server time is: " + GlitchData.getTimeString(System.currentTimeMillis()));
		history.sort((a, b) -> {
			return Long.compare(a.getTime(), b.getTime());
		});
		for (GlitchData data : history) {
			String message = LIGHT_PURPLE + data.getTimeString() + ": ";
			message += String.format("%s broke a block at (%d,%d,%d)", data.getPlayer().getName(), data.getX(),
					data.getY(), data.getZ());
			sender.sendMessage(message);
		}
	}
}
