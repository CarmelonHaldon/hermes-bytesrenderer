package es.carmelonhaldon.hermes.mapbytesrenderer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.swing.JComponent;

import hermes.browser.dialog.message.MapBytesMessagePayloadPanel;
import hermes.renderers.DefaultMessageRenderer;

public class MapBytesMessageRenderer extends DefaultMessageRenderer {

	protected JComponent handleMapMessage(MapMessage mapMessage) throws JMSException {

		return new MapBytesMessagePayloadPanel(mapMessage, false);
	}

}
