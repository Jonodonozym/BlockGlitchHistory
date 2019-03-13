
package jdz.blockGlitch.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;

import lombok.Data;

public class DataSetMetrics {
	private final Map<Vector, VectorMetrics> metricsSet = new HashMap<>();
	private final List<Vector> orderedVectors;

	@Data
	private class VectorMetrics {
		private final Vector vector;
		private long earliestModification = Long.MAX_VALUE;
		private long latestModification = Long.MIN_VALUE;
		private OfflinePlayer latestModifiedPlayer = null;
		private int frequency = 0;
	}

	public DataSetMetrics(Collection<GlitchData> dataset) {
		for (GlitchData data : dataset) {
			if (!metricsSet.containsKey(data.getVector()))
				metricsSet.put(data.getVector(), new VectorMetrics(data.getVector()));

			VectorMetrics metrics = metricsSet.get(data.getVector());
			if (data.getTime() < metrics.getEarliestModification())
				metrics.setEarliestModification(data.getTime());
			if (data.getTime() > metrics.getLatestModification()) {
				metrics.setEarliestModification(data.getTime());
				metrics.setLatestModifiedPlayer(data.getPlayer());
			}
			metrics.setFrequency(metrics.getFrequency() + 1);
		}

		orderedVectors = new ArrayList<>(metricsSet.keySet());
		orderedVectors.sort(this::compare);
	}

	private int compare(Vector v1, Vector v2) {
		if (v1.getBlockX() < v2.getBlockX())
			return -1;
		else if (v1.getBlockX() > v2.getBlockX())
			return 1;
		if (v1.getBlockY() < v2.getBlockY())
			return -1;
		else if (v1.getBlockY() > v2.getBlockY())
			return 1;
		if (v1.getBlockZ() < v2.getBlockZ())
			return -1;
		else if (v1.getBlockZ() > v2.getBlockZ())
			return 1;
		return 0;
	}

	public void outputResults(CommandSender player) {
		for (Vector vector : orderedVectors) {
			VectorMetrics metrics = metricsSet.get(vector);
			String message = ChatColor.LIGHT_PURPLE
					+ String.format("(%d,%d,%d)", vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
			message += String.format(" broken &d times between %s and %s, last break by %s", metrics.frequency,
					GlitchData.getTimeString(metrics.earliestModification),
					GlitchData.getTimeString(metrics.latestModification), metrics.latestModifiedPlayer.getName());
			player.sendMessage(message);
		}
	}
}
