package fanndrann.firenuke;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public final class FireNuke extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getCommand("nuke").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эта команда может быть использована только игроком.");
            return true;
        }
        Player player = (Player) sender;

        if (player.isOp()) {
            if (args.length < 2) {
                player.sendMessage("Используйте: /nuke <скорость> <мощность>");
                return true;
            }

            try {
                double speed = Double.parseDouble(args[0]);
                double yield = Double.parseDouble(args[1]);

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Бум-бум"));
                Vector playerDirection = player.getLocation().getDirection();
                Fireball fireball = player.getWorld().spawn(player.getEyeLocation(), Fireball.class);
                fireball.setVelocity(playerDirection.multiply(speed));
                fireball.setYield((float) yield);

                return true;
            } catch (NumberFormatException e) {
                player.sendMessage("Используйте: /nuke <скорость> <мощность>");
                return true;
            }
        } else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Права покажите!"));
            return true;
        }
    }

    @Override
    public void onDisable() {
    }
}
