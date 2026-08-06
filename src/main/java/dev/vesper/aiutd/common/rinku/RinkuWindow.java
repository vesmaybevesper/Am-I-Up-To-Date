package dev.vesper.aiutd.common.rinku;

//? 1.21.1 || >= 1.21.11 {
/*import de.keksuccino.rinku.Rinku;
import de.keksuccino.rinku.RinkuBrowser;
import dev.vesper.aiutd.AIUTD;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefDisplayHandler;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

public class RinkuWindow extends Screen {

	private static final int FRAME_MARGIN = 20;
	private static final int NAV_BAR_HEIGHT = 20;
	private static final int NAV_BAR_GAP = 6;
	private static final int NAV_BUTTON_WIDTH = 24;
	private static final int NAV_SPACING = 4;
	private static final int LOADING_BAR_HEIGHT = 2;
	private static final int LOADING_BAR_TRACK_COLOR = 0x55000000;
	private static final int LOADING_BAR_FILL_COLOR = 0xFF3BA8FF;
	private static final String DEFAULT_URL = AIUTD.changelogLink;

	private RinkuBrowser browser;
	private EditBox urlBox;
	private Button backButton;
	private Button forwardButton;
	private Button reloadButton;
	private CefDisplayHandler addressBarDisplayHandler;

	protected RinkuWindow(Component title) {
		super(title);
	}

	@Override
	protected void init() {
		super.init();
		if (browser == null){
			boolean transparent = true;
			browser = Rinku.createBrowser(DEFAULT_URL, transparent);
		}
		registerAddressBarDisplayHandler();
		initNavigationWidgets();
		resizeBrowser();
		refreshNavigationState();
	}

	private  void registerAddressBarDisplayHandler() {
		if (addressBarDisplayHandler != null) {
			return;
		}

		addressBarDisplayHandler = new CefDisplayHandlerAdapter() {
			@Override
			public void onAddressChange(CefBrowser cefBrowser, CefFrame cefFrame, String url) {
				if (browser == null || cefBrowser == null || cefFrame == null || !cefFrame.isMain()) {
					return;
				}
				if (cefBrowser.getIdentifier() != browser.getIdentifier()) {
					return;
				}

				minecraft.execute(() -> {
					if (minecraft.gui.screen() != RinkuWindow.this || urlBox == null || url == null || url.isBlank()) {
					return;
					}
					if (!url.equals(urlBox.getValue())) {
						urlBox.setValue(url);
					}
				});
			}
		};
		Rinku.getClient().addDisplayHandler(addressBarDisplayHandler);
	}

	private  void initNavigationWidgets() {
		int navX = FRAME_MARGIN;
		int navY = FRAME_MARGIN;

		backButton = addRenderableWidget(
				Button.builder(Component.literal("<"), (button) -> browser.goBack())
						.bounds(navX, navY, NAV_BUTTON_WIDTH, NAV_BAR_HEIGHT)
						.build()
		);
		navX += NAV_BUTTON_WIDTH + NAV_SPACING;

		forwardButton = addRenderableWidget(
				Button.builder(Component.literal(">"), (button) -> browser.goForward())
						.bounds(navX, navY, NAV_BUTTON_WIDTH, NAV_BAR_HEIGHT)
						.build()
		);
		navX += NAV_BUTTON_WIDTH + NAV_SPACING;

		reloadButton = addRenderableWidget(
				Button.builder(Component.literal("R"), (button) -> browser.reload())
						.bounds(navX, navY, NAV_BUTTON_WIDTH, NAV_BAR_HEIGHT)
						.build()
		);
		navX += NAV_BUTTON_WIDTH + NAV_SPACING;

		int urlWidth = Math.max(60, width - FRAME_MARGIN - navX);
		urlBox = addRenderableWidget(new EditBox(font, navX, navY, urlWidth, NAV_BAR_HEIGHT, Component.literal("URL")));
		urlBox.setMaxLength(2048);
		String currentUrl = browser.getURL();
		urlBox.setValue(currentUrl == null || currentUrl.isBlank() ? DEFAULT_URL : currentUrl);
	}

	private int getBrowserX() {
		return FRAME_MARGIN;
	}

	private int getBrowserY() {
		return FRAME_MARGIN + NAV_BAR_HEIGHT + NAV_BAR_GAP;
	}

	private int getBrowserWidth() {
		return Math.max(1, width - FRAME_MARGIN * 2);
	}

	private int getBrowserHeight() {
		return Math.max(1, height - getBrowserY() - FRAME_MARGIN);
	}

	private boolean isInBrowserBounds(double x, double y) {
		int browserX = getBrowserX();
		int browserY = getBrowserY();
		return x >= browserX && y >= browserY && x < (browserX + getBrowserWidth()) && y < (browserY + getBrowserHeight());
	}

	private int mouseX(double x) {
		return (int) ((x - getBrowserX()) * minecraft.getWindow().getGuiScale());
	}

	private int mouseY(double y) {
		return (int) ((y - getBrowserY()) * minecraft.getWindow().getGuiScale());
	}

	private void resizeBrowser() {
		if (browser != null) {
			browser.resize((int) (getBrowserWidth() * minecraft.getWindow().getGuiScale()), (int) (getBrowserHeight() * minecraft.getWindow().getGuiScale()));
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		resizeBrowser();
	}

	@Override
	public void onClose() {
		if (addressBarDisplayHandler != null && Rinku.isInitialized()) {
			Rinku.getClient().removeDisplayHandler(addressBarDisplayHandler);
		}
		addressBarDisplayHandler = null;
		browser.close();
		super.onClose();
	}

	@Override
	public void tick() {
		super.tick();
		refreshNavigationState();
	}

	private void refreshNavigationState() {
		if (browser == null) return;

		if (backButton != null) backButton.active = browser.canGoBack();

		if (forwardButton != null) forwardButton.active = browser.canGoForward();

		if (reloadButton != null) reloadButton.active = true;

		if (urlBox != null && !urlBox.isFocused()) {
			String currentUrl = browser.getURL();
			if (currentUrl != null && !currentUrl.isBlank() && !currentUrl.equals(urlBox.getValue())) {
				urlBox.setValue(currentUrl);
			}
		}
	}

	private void navigateFromUrlField() {
		if (urlBox == null) {
			return;
		}

		String input = urlBox.getValue();
		if (input == null) {
			return;
		}
		input = input.trim();
		if (input.isEmpty()) {
			return;
		}

		String normalizedUrl = normalizeUrl(input);
		urlBox.setValue(normalizedUrl);
		browser.loadURL(normalizedUrl);
		browser.setFocus(true);
	}

	private String normalizeUrl(String input) {
		if (input.matches("^[a-zA-Z][a-zA-Z0-9+.-]*:.*")) {
			return input;
		}
		return "https://" + input;
	}

	@Override
	public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractRenderState(graphics, mouseX, mouseY, a);
		renderLoadingIndicator(graphics);

		if (browser != null && browser.isTextureReady()) renderBrowserTexture(graphics);
	}

	private void renderBrowserTexture(GuiGraphicsExtractor graphics){
		Identifier textureLocation = browser.getTextureIdentifier();
		if (textureLocation == null) return;

		int frameRenderWidth = getBrowserWidth();
		int frameRenderHeight = getBrowserHeight();
		graphics.blit(
				RenderPipelines.GUI_TEXTURED,
				textureLocation,
				getBrowserX(),
				getBrowserY(),
				0.0F,
				0.0F,
				frameRenderWidth,
				frameRenderHeight,
				frameRenderWidth,
				frameRenderHeight
		);
	}

	private void renderLoadingIndicator(GuiGraphicsExtractor graphics) {
		if (browser == null || urlBox == null || !browser.isLoading()) {
			return;
		}

		int barX = urlBox.getX();
		int barY = urlBox.getY() + 1;
		int barWidth = urlBox.getWidth();
		int barBottom = barY + LOADING_BAR_HEIGHT;
		graphics.fill(barX, barY, barX + barWidth, barBottom, LOADING_BAR_TRACK_COLOR);

		int segmentWidth = Math.max(20, barWidth / 4);
		int travelRange = barWidth + segmentWidth;
		int animatedOffset = (int) ((Util.getMillis() / 6L) % travelRange) - segmentWidth;
		int segmentStart = Math.max(barX, barX + animatedOffset);
		int segmentEnd = Math.min(barX + barWidth, barX + animatedOffset + segmentWidth);
		if (segmentEnd > segmentStart) {
			graphics.fill(segmentStart, barY, segmentEnd, barBottom, LOADING_BAR_FILL_COLOR);
		}
	}

	@Override
	public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean doubleClick) {
		boolean handled = super.mouseClicked(event, doubleClick);
		if (handled) {
			return true;
		}

		if (!isInBrowserBounds(event.x(), event.y())) {
			return false;
		}

		browser.sendMousePress(mouseX(event.x()), mouseY(event.y()), event.button());
		browser.setFocus(true);
		return true;
	}

	@Override
	public boolean mouseReleased(MouseButtonEvent event) {
		boolean handled = super.mouseReleased(event);
		if (handled) {
			return true;
		}

		browser.sendMouseRelease(mouseX(event.x()), mouseY(event.y()), event.button());
		browser.setFocus(true);
		return true;
	}

	@Override
	public void mouseMoved(double x, double y) {
		if (isInBrowserBounds(x, y)) {
			browser.sendMouseMove(this.mouseX(x), this.mouseY(y));
		}
		super.mouseMoved(x, y);
	}

	@Override
	public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
		return super.mouseDragged(event, dx, dy);
	}

	@Override
	public boolean mouseScrolled(double x, double y, double scrollX, double scrollY) {
		boolean handled = super.mouseScrolled(x, y, scrollX, scrollY);
		if (handled) {
			return true;
		}

		if (!isInBrowserBounds(x, y)) {
			return false;
		}

		browser.sendMouseWheel(this.mouseX(x), this.mouseY(y), scrollY, 0);
		return true;
	}

	@Override
	public boolean keyPressed(@NonNull KeyEvent event) {
		if (urlBox != null && urlBox.isFocused() && (event.key() == GLFW.GLFW_KEY_ENTER || event.key() == GLFW.GLFW_KEY_KP_ENTER)) {
			navigateFromUrlField();
			setFocused(null);
			browser.setFocus(true);
			return true;
		}

		if (super.keyPressed(event)) {
			return true;
		}

		if (urlBox != null && urlBox.isFocused()) {
			return true;
		}

		browser.sendKeyPress(event.key(), event.scancode(), event.modifiers());
		browser.setFocus(true);
		return true;
	}

	@Override
	public boolean keyReleased(@NonNull KeyEvent event) {
		if (super.keyReleased(event)) {
			return true;
		}

		if (urlBox != null && urlBox.isFocused()) {
			return true;
		}

		browser.sendKeyRelease(event.key(), event.scancode(), event.modifiers());
		browser.setFocus(true);
		return true;
	}

	@Override
	public boolean charTyped(CharacterEvent event) {
		if (super.charTyped(event)) {
			return true;
		}

		if (urlBox != null && urlBox.isFocused()) {
			return true;
		}

		if (event.codepoint() == (char) 0) return false;
		browser.sendKeyTyped((char) event.codepoint(), 0);
		browser.setFocus(true);
		return true;
	}
}*/
//?}
