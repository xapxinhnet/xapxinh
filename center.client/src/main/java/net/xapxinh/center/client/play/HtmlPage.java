package net.xapxinh.center.client.play;

import com.google.gwt.user.client.ui.RootPanel;

public class HtmlPage {
	
	private final RootPanel spinner;
	private final RootPanel header;
	private final RootPanel center;
	private final RootPanel footer;
	
	HtmlPage() {
		spinner = RootPanel.get("spinner");
		header = RootPanel.get("header");
		center = RootPanel.get("center");
		footer = RootPanel.get("footer");
	}
	
	public void clear() {
		getHeader().clear();
		getCenter().clear();
		getFooter().clear();
	}

	public RootPanel getSpinner() {
		return spinner;
	}

	public RootPanel getHeader() {
		return header;
	}

	public RootPanel getCenter() {
		return center;
	}

	public RootPanel getFooter() {
		return footer;
	}
}
