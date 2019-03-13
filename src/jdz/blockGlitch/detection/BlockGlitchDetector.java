
package jdz.blockGlitch.detection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import jdz.blockGlitch.data.GlitchData;
import jdz.blockGlitch.data.GlitchDatabase;
import jdz.bukkitUtils.events.Listener;

public class BlockGlitchDetector implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		if (!event.isCancelled())
			return;

		GlitchDatabase.addAsync(new GlitchData(event.getPlayer(), event.getBlock()));
	}
}
