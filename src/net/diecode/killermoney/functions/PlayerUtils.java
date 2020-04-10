package net.diecode.killermoney.functions;

import net.diecode.killermoney.BukkitMain;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerUtils {

    private static PlayerUtils playerUtils;

    public boolean hasMeta(Player player, String meta) {
        return player.hasMetadata(meta);
    }

    public void setMeta(Player player, String meta, Object value) {
        player.setMetadata(meta, new FixedMetadataValue(BukkitMain.getInstance(), value));
    }

    public void clearMeta(Player player, String meta) {
        if(hasMeta(player, meta)) {
            player.removeMetadata(meta, BukkitMain.getInstance());
        }
    }

    public String getMetaValue(Player player, String meta) {
        if(!(hasMeta(player, meta)) || player.getMetadata(meta).size() == 0) return "";
        return player.getMetadata(meta).get(0).asString();
    }

    public static PlayerUtils getInstance() {
        if(playerUtils == null) {
            playerUtils = new PlayerUtils();
        }
        return playerUtils;
    }
}
