package WINGS7N.DLogger.listeners;

import WINGS7N.DLogger.CMD.GC;
import WINGS7N.DLogger.CMD.GetPINGCMD;
import WINGS7N.DLogger.CMD.GetTPSCMD;
import WINGS7N.DLogger.CMD.HelpCommand;
import WINGS7N.DLogger.storage.SS;
import WINGS7N.InventoryWorker.DecodeInventoryFromFile;
import WINGS7N.InventoryWorker.DecodeInventoryFromFileLegacy;
import WINGS7N.PluginUpdater.SelfUpdate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Arrays;
import java.util.Objects;

public class CommandListener implements Listener, CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(SS.jdlcmd)) {

            if (args.length == 0) {
                if (s.hasPermission(SS.HelpPerm)) {
                    new HelpCommand(s);
                }
            } else {
                switch (args[0].toLowerCase()) {
                    case "rollback":
                    case "r":
                        if (s.hasPermission(SS.checkPerm)) {
                            String arg3 = null;
                            if (args.length > 2) {
                                arg3 = args[2].toLowerCase();
                            }
                            String FileName = args[1];
                            if (Objects.equals(arg3, "-s") || Objects.equals(arg3, "--self")) {
                                if (s instanceof Player) {
                                    new DecodeInventoryFromFile(s, FileName, true);
                                } else {
                                    s.sendMessage(SS.OnlyPlayer);
                                }
                            } else {
                                new DecodeInventoryFromFile(s, FileName, false);
                            }
                        }
                        break;
                    case "tps":
                        if (s.hasPermission(SS.GetTPSPerm)) {
                            new GetTPSCMD(s);
                        }
                        break;
                    case "ping":
                        new GetPINGCMD(s);
                        break;
                    case "help":
                    case "h":
                        if (s.hasPermission(SS.HelpPerm)) {
                            new HelpCommand(s);
                        }
                        break;
                    case "u":
                    case "upd":
                    case "update":
                        if (s.hasPermission(SS.UPDPerm)) {
                            new SelfUpdate(s);
                        }
                        break;
                    case "gc":
                        if (s.hasPermission(SS.GCPerm)) {
                            new GC(s);
                        }
                        break;
                    default:
                        if (s.hasPermission(SS.HelpPerm)) {
                            new HelpCommand(s);
                        } else {
                            s.sendMessage(SS.NoPerms);
                        }
                        break;
                }
            }
        }

        if (cmd.getName().equalsIgnoreCase(SS.jdllegacycmd)) {
            if (s instanceof Player) {
                if (args.length == 0) {
                    s.sendMessage(SS.ERROR);
                } else {
                    String FullFileName = args[1];
                    new DecodeInventoryFromFileLegacy(s, FullFileName);
                }
            }
        }
        return true;
    }
}
