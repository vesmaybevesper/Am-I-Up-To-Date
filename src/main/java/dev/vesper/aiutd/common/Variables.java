package dev.vesper.aiutd.common;

import dev.vesper.aiutd.common.config.Config;
import net.minecraft.ChatFormatting;

public class Variables {
	protected static ChatFormatting changelogColor;
	protected static ChatFormatting updateMsgColor;
	protected static ChatFormatting ignoreMsgColor;

	public static void setAll(){
		setColors();
		// the following do nothing at this time, functionality will be added later
		setWidgetPos();
		setWidgetSize();
	}

	private static void setColors() {

		switch(Config.changelogColor) {
			case RED -> changelogColor = ChatFormatting.RED;
			case BLACK -> changelogColor = ChatFormatting.BLACK;
			case DARK_BLUE -> changelogColor = ChatFormatting.DARK_BLUE;
			case DARK_GREEN -> changelogColor = ChatFormatting.DARK_GREEN;
			case DARK_AQUA -> changelogColor = ChatFormatting.DARK_AQUA;
			case DARK_RED -> changelogColor = ChatFormatting.DARK_RED;
			case DARK_PURPLE -> changelogColor = ChatFormatting.DARK_PURPLE;
			case GOLD  -> changelogColor = ChatFormatting.GOLD;
			case GRAY  -> changelogColor = ChatFormatting.GRAY;
			case DARK_GRAY  -> changelogColor = ChatFormatting.DARK_GRAY;
			case BLUE   -> changelogColor = ChatFormatting.BLUE;
			case GREEN   -> changelogColor = ChatFormatting.GREEN;
			case AQUA    -> changelogColor = ChatFormatting.AQUA;
			case LIGHT_PURPLE  -> changelogColor = ChatFormatting.LIGHT_PURPLE;
			case YELLOW   -> changelogColor = ChatFormatting.YELLOW;
			case WHITE    -> changelogColor = ChatFormatting.WHITE;
		}

		switch(Config.messageColor) {
			case RED -> updateMsgColor = ChatFormatting.RED;
			case BLACK -> updateMsgColor = ChatFormatting.BLACK;
			case DARK_BLUE -> updateMsgColor = ChatFormatting.DARK_BLUE;
			case DARK_GREEN -> updateMsgColor = ChatFormatting.DARK_GREEN;
			case DARK_AQUA -> updateMsgColor = ChatFormatting.DARK_AQUA;
			case DARK_RED -> updateMsgColor = ChatFormatting.DARK_RED;
			case DARK_PURPLE -> updateMsgColor = ChatFormatting.DARK_PURPLE;
			case GOLD  -> updateMsgColor = ChatFormatting.GOLD;
			case GRAY  -> updateMsgColor = ChatFormatting.GRAY;
			case DARK_GRAY  -> updateMsgColor = ChatFormatting.DARK_GRAY;
			case BLUE   -> updateMsgColor = ChatFormatting.BLUE;
			case GREEN   -> updateMsgColor = ChatFormatting.GREEN;
			case AQUA    -> updateMsgColor = ChatFormatting.AQUA;
			case LIGHT_PURPLE  -> updateMsgColor = ChatFormatting.LIGHT_PURPLE;
			case YELLOW   -> updateMsgColor = ChatFormatting.YELLOW;
			case WHITE    -> updateMsgColor = ChatFormatting.WHITE;
		}

		switch(Config.ignoreColor) {
			case RED -> ignoreMsgColor = ChatFormatting.RED;
			case BLACK -> ignoreMsgColor = ChatFormatting.BLACK;
			case DARK_BLUE -> ignoreMsgColor = ChatFormatting.DARK_BLUE;
			case DARK_GREEN -> ignoreMsgColor = ChatFormatting.DARK_GREEN;
			case DARK_AQUA -> ignoreMsgColor = ChatFormatting.DARK_AQUA;
			case DARK_RED -> ignoreMsgColor = ChatFormatting.DARK_RED;
			case DARK_PURPLE -> ignoreMsgColor = ChatFormatting.DARK_PURPLE;
			case GOLD  -> ignoreMsgColor = ChatFormatting.GOLD;
			case GRAY  -> ignoreMsgColor = ChatFormatting.GRAY;
			case DARK_GRAY  -> ignoreMsgColor = ChatFormatting.DARK_GRAY;
			case BLUE   -> ignoreMsgColor = ChatFormatting.BLUE;
			case GREEN   -> ignoreMsgColor = ChatFormatting.GREEN;
			case AQUA    -> ignoreMsgColor = ChatFormatting.AQUA;
			case LIGHT_PURPLE  -> ignoreMsgColor = ChatFormatting.LIGHT_PURPLE;
			case YELLOW   -> ignoreMsgColor = ChatFormatting.YELLOW;
			case WHITE    -> ignoreMsgColor = ChatFormatting.WHITE;
		}
	}

	private static void setWidgetPos(){

	}

	private static void setWidgetSize(){

	}
}
