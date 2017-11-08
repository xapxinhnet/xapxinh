package net.xapxinh.center.client.play.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ResourcesBundle extends ClientBundle {
	ResourcesBundle INSTANCE = GWT.create(ResourcesBundle.class);

	@Source("net/xapxinh/center/client/play/header/timepicker_AM.png")
	ImageResource timePickerAM();

	@Source("net/xapxinh/center/client/play/header/timepicker_PM.png")
	ImageResource timePickerPM();
}