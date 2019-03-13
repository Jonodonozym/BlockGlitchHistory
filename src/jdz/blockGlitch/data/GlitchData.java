
package jdz.blockGlitch.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import jdz.bukkitUtils.sql.ORM.NoSave;
import jdz.bukkitUtils.sql.ORM.SQLDataClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GlitchData extends SQLDataClass {
	@NoSave private static final DateFormat format = new SimpleDateFormat("mon dd hh:mm:ss");
	
	private final OfflinePlayer player;
	private final long time;
	private final int x, y, z;
	private final Material material;

	public GlitchData(Player player, Block block) {
		this(player, System.currentTimeMillis(), block.getLocation().getBlockX(), block.getLocation().getBlockY(),
				block.getLocation().getBlockZ(), block.getType());
	}
	
	public Vector getVector() {
		return new Vector(x, y, z);
	}
	
	public String getTimeString() {
		return getTimeString(time);
	}
	
	public static String getTimeString(long time) {
		return format.format(new Date(time));
	}
}
