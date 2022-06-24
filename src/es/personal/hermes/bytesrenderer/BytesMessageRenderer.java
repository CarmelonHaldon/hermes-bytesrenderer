package es.personal.hermes.bytesrenderer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.swing.JComponent;

import hermes.browser.dialog.message.MapBytesMessagePayloadPanel;
import hermes.renderers.DefaultMessageRenderer;

public class BytesMessageRenderer extends DefaultMessageRenderer {

	protected JComponent handleMapMessage(MapMessage mapMessage) throws JMSException {

		return new MapBytesMessagePayloadPanel(mapMessage, false);
	}
}
