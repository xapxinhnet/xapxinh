package net.xapxinh.center.client.play.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Sann Tran
 */
public class ShowPopupMenuEvent extends GwtEvent<ShowPopupMenuEventHandler> {
	public static Type<ShowPopupMenuEventHandler> TYPE = new Type<ShowPopupMenuEventHandler>();

	@Override
	public Type<ShowPopupMenuEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowPopupMenuEventHandler handler) {
		handler.onShowPopupMainMenu(this);
	}
}
