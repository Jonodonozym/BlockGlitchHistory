
package jdz.blockGlitch.data;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import jdz.blockGlitch.search.RegionSelector.Region;
import jdz.bukkitUtils.sql.SQLRow;
import jdz.bukkitUtils.sql.SqlDatabase;
import lombok.Getter;

public class GlitchDatabase extends SqlDatabase {
	@Getter private static GlitchDatabase instance;

	public GlitchDatabase(Plugin plugin) {
		super(plugin);
		instance = this;
		runOnConnect(() -> {
			GlitchData.createTable(GlitchData.class, this);
			GlitchData.createIndex(GlitchData.class, "x", this);
			GlitchData.createIndex(GlitchData.class, "y", this);
			GlitchData.createIndex(GlitchData.class, "z", this);
		});
	}

	public static List<GlitchData> getInside(Region region) {
		int xMin = Math.min(region.getL1().getBlockX(), region.getL2().getBlockX());
		int xMax = Math.max(region.getL1().getBlockX(), region.getL2().getBlockX());
		int yMin = Math.min(region.getL1().getBlockY(), region.getL2().getBlockY());
		int yMax = Math.max(region.getL1().getBlockY(), region.getL2().getBlockY());
		int zMin = Math.min(region.getL1().getBlockZ(), region.getL2().getBlockY());
		int zMax = Math.max(region.getL1().getBlockZ(), region.getL2().getBlockY());

		String query = String.format(
				"SELECT * FROM %s WHERE x BETWEEN %d AND %d AND y BETWEEN %d AND %d AND z BETWEEN %d AND %d",
				getTable(), xMin, xMax, yMin, yMax, zMin, zMax);

		return getInstance().query(query).stream().map(GlitchDatabase::parse).collect(Collectors.toList());
	}

	public static List<GlitchData> getByPlayer(OfflinePlayer player) {
		String query = String.format("SELECT * FROM %s WHERE player=%s", getTable(), player.getUniqueId().toString());
		return getInstance().query(query).stream().map(GlitchDatabase::parse).collect(Collectors.toList());
	}

	public static void addAsync(GlitchData data) {
		Bukkit.getScheduler().runTaskAsynchronously(instance.getPlugin(), () -> {
			data.insert(instance);
		});
	}

	private static String getTable() {
		return GlitchData.getTableName(GlitchData.class);
	}

	private static GlitchData parse(SQLRow row) {
		return GlitchData.parse(GlitchData.class, row);
	}

}
