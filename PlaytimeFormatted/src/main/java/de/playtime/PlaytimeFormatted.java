package de.playtime;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class PlaytimeFormatted extends JavaPlugin {

    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().severe("PlaceholderAPI wurde nicht gefunden!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        new PlaytimeExpansion().register();
        getLogger().info("PlaytimeFormatted aktiviert.");
    }

    public static final class PlaytimeExpansion extends PlaceholderExpansion {

        @Override
        public @NotNull String getIdentifier() {
            return "playtime";
        }

        @Override
        public @NotNull String getAuthor() {
            return "ServerAdmin";
        }

        @Override
        public @NotNull String getVersion() {
            return "1.0.0";
        }

        @Override
        public boolean persist() {
            return true;
        }

        @Override
        public String onRequest(OfflinePlayer player, @NotNull String params) {
            if (player == null || !params.equalsIgnoreCase("formatted")) {
                return null;
            }

            int ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
            long totalMinutes = ticks / 1200L;

            long days = totalMinutes / (24L * 60L);
            long hours = (totalMinutes / 60L) % 24L;
            long minutes = totalMinutes % 60L;

            if (days > 0) {
                return days + "D " + hours + "H";
            }

            return hours + "H " + minutes + "M";
        }
    }
}
