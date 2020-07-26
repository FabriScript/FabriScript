package me.geek.tom.fabriscript.ws;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.geek.tom.fabriscript.script.ScriptExecutor;
import me.geek.tom.fabriscript.server.IFabriScript;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FabriScriptImpl implements IFabriScript {

    private static final Logger LOGGER = LogManager.getLogger();

    private final MinecraftServer server;
    private final File scriptDir;

    public FabriScriptImpl(MinecraftServer server) {
        this.server = server;
        scriptDir = new File(server.getRunDirectory(), "fabriscript");
    }

    @Override
    public boolean receiveScript(String name, String content) {
        File script = new File(scriptDir, name);

        if (!scriptDir.equals(script.getAbsoluteFile().getParentFile())) { // No exploits pls.
            String warning1 = "     SOMEBODY ATTEMPTED TO SAVE A SCRIPT FILE OUTSIDE OF THE SCRIPTS DIR     ";
            String warning2 = "     THEY ATTEMPTED TO WRITE TO:" + script + "     ";
            int count = Math.max(warning1.length(), warning2.length());
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < count; i++) {
                stars.append("*");
            }
            LOGGER.warn(stars);
            LOGGER.warn(warning1);
            LOGGER.warn(warning2);
            LOGGER.warn(stars);
            return false;
        }

        if (!script.exists()) {
            try {
                if (!script.createNewFile())
                    return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(script))) {
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean runScript(String name) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        if (players.size() == 0)
            return false;
        ServerPlayerEntity player = players.get(0);
        ServerCommandSource source = player.getCommandSource();
        try {
            ScriptExecutor.loadAndExecScript(name, "", source);
        } catch (CommandSyntaxException e) {
            source.sendError(Texts.toText(e.getRawMessage()));
            if (e.getInput() != null && e.getCursor() >= 0) {
                int i = Math.min(e.getInput().length(), e.getCursor());
                MutableText msg = (new LiteralText("")).formatted(Formatting.GRAY);
                if (i > 10) {
                    msg.append("...");
                }

                msg.append(e.getInput().substring(Math.max(0, i - 10), i));
                if (i < e.getInput().length()) {
                    Text text = (new LiteralText(e.getInput().substring(i))).formatted(new Formatting[]{Formatting.RED, Formatting.UNDERLINE});
                    msg.append(text);
                }

                msg.append((new TranslatableText("command.context.here")).formatted(new Formatting[]{Formatting.RED, Formatting.ITALIC}));
                source.sendError(msg);
            }
            return false;
        }

        return true;
    }
}
