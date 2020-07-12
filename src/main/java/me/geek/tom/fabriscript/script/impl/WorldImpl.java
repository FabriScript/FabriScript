package me.geek.tom.fabriscript.script.impl;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.geek.tom.fabriscript.script.api.IWorld;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.BlockArgumentParser;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class WorldImpl implements IWorld {

    private PlayerEntity player;
    private BlockPos playerPos;
    private World world;

    public WorldImpl(PlayerEntity player, World world) {
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

    @Override
    public void spawnEntity(int relX, int relY, int relZ, String entity) {
        spawnEntity(relX, relY, relZ, entity, false);
    }

    @Override
    public void spawnEntity(int relX, int relY, int relZ, String entity, boolean naturalSpawn) {
        Registry.ENTITY_TYPE.get(new Identifier(entity)).spawn(this.world,
                null, null, null,
                this.playerPos.add(relX, relY, relZ),
                naturalSpawn ? SpawnReason.NATURAL : SpawnReason.COMMAND,
                false, false
        );
    }
}
