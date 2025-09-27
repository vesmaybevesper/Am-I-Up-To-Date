package dev.vesper.AIUTD.neoforge;

//? neoforge {
/*import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.config.EndUserConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

@EventBusSubscriber(modid = AIUTD.MOD_ID, value = Dist.CLIENT)
public class RegisterCommand {
    private static int executeShouldIgnore(CommandContext<?> context) {
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.displayClientMessage(Component.literal("You have set chat notifications to be ignored!"), false);
        EndUserConfig.shouldIgnore = true;
        return 1;
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterClientCommandsEvent event) {

        AIUTD.LOG.info(String.valueOf(event.getBuildContext()));
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal("shouldIgnore")
                        .executes(RegisterCommand::executeShouldIgnore)
        );
    }
}
*///?}