
package jdz.blockGlitch;

import org.bukkit.plugin.java.JavaPlugin;

import jdz.blockGlitch.data.GlitchDatabase;
import jdz.blockGlitch.detection.BlockGlitchDetector;
import jdz.blockGlitch.search.RegionSelector;
import jdz.blockGlitch.search.command.GlitchCommandExecutor;

public class BlockGlitchHistory extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new GlitchDatabase(this);
		new BlockGlitchDetector().registerEvents(this);
		new RegionSelector().registerEvents(this);
		new GlitchCommandExecutor(this).register();
	}

}
