
package jdz.blockGlitch.search.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import jdz.bukkitUtils.commands.CommandExecutor;
import jdz.bukkitUtils.commands.SubCommand;
import jdz.bukkitUtils.commands.annotations.CommandExecutorPermission;
import lombok.Getter;

@CommandExecutorPermission("blockGlitch.admin")
public class GlitchCommandExecutor extends CommandExecutor {
	@Getter private final List<SubCommand> subCommands = Arrays.asList(new GlitchWandCommand(),
			new GlitchHistoryCommand(), new GlitchMetricsCommand());

	public GlitchCommandExecutor(JavaPlugin plugin) {
		super(plugin, "blockGlitch");
		setDefaultCommand(getHelpCommand());
	}
}
