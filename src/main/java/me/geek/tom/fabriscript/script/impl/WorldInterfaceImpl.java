package me.geek.tom.fabriscript.script.impl;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.geek.tom.fabriscript.script.api.IWorldInterface;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.BlockArgumentParser;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class WorldInterfaceImpl implements IWorldInterface {

    private PlayerEntity player;
    private BlockPos playerPos;
    private World world;

    public WorldInterfaceImpl(PlayerEntity player, World world) {
        this.player = player;
        this.world = world;
        this.playerPos = player.getBlockPos();
    }

    @Override
    public void setBlock(int x, int y, int z, String block) {
        this.world.setBlockState(playerPos.add(x, y, z), Registry.BLOCK.get(new Identifier(block)).getDefaultState());
    }

    @Override
    public void setBlockNoUpdates(int x, int y, int z, String block) throws IllegalArgumentException {
        this.world.setBlockState(playerPos.add(x, y, z), Registry.BLOCK.get(new Identifier(block)).getDefaultState(), 18);
    }

    @Override
    public void setBlockState(int x, int y, int z, String blockState) {
        BlockArgumentParser blockArgumentParser = new BlockArgumentParser(new StringReader(blockState), false);
        BlockState state;
        try {
            blockArgumentParser.parse(false);
            state = blockArgumentParser.getBlockState();
        } catch (CommandSyntaxException e) {
            this.player.sendMessage(
                    new TranslatableText("fabriscript.script.warnnotblock", blockState).styled(s->s.withColor(Formatting.RED)),
                    false);
            state = Blocks.AIR.getDefaultState();
        }
        this.world.setBlockState(playerPos.add(x, y, z), state);
    }
}
