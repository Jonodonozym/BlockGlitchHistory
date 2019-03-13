
package jdz.blockGlitch.search.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import jdz.blockGlitch.data.DataSetMetrics;
import jdz.blockGlitch.data.GlitchData;
import jdz.blockGlitch.data.GlitchDatabase;
import jdz.blockGlitch.search.RegionSelector;
import jdz.blockGlitch.search.RegionSelector.Region;
import jdz.bukkitUtils.commands.SubCommand;
import jdz.bukkitUtils.commands.annotations.CommandLabel;
import jdz.bukkitUtils.commands.annotations.CommandMethod;


@CommandLabel("metrics")
public class GlitchMetricsCommand extends SubCommand {
	@CommandMethod
	public void execute(Player sender) {
		Region region = RegionSelector.getInstance().getSelectedRegion(sender);
		if (region == null) {
			sender.sendMessage(ChatColor.RED + "you must select a region first!");
			return;
		}
		
		List<GlitchData> history = GlitchDatabase.getInside(region);
		DataSetMetrics metrics = new DataSetMetrics(history);
		metrics.outputResults(sender);
	}
}
