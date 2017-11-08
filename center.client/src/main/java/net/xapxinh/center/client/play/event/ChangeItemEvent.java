package net.xapxinh.center.client.play.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Sann Tran
 */
public class ChangeItemEvent extends GwtEvent<ChangeItemEventHandler> {
	public static Type<ChangeItemEventHandler> TYPE = new Type<ChangeItemEventHandler>();

	public ChangeItemEvent() {
	}

	@Override
	public Type<ChangeItemEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChangeItemEventHandler handler) {
		handler.onChangeItem(this);
	}
}
