package hermes.browser.dialog.message;

import java.awt.BorderLayout;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import hermes.swing.PropertyRow;
import hermes.swing.PropertyType;
import hermes.util.TextUtils;

public class MapBytesMessagePayloadPanel extends MessageWriter {

	private MapBytesPanelImpl panel;

	public MapBytesMessagePayloadPanel() throws JMSException {

		this(null, true);
	}

	public MapBytesMessagePayloadPanel(MapMessage message, boolean editable) throws JMSException {

		panel = new MapBytesPanelImpl(message, editable);
		setLayout(new BorderLayout());
		// if (false) {
		// JLabel label = new JLabel("Edit the MapMessage");
		// label.setBorder(Borders.EMPTY_BORDER);
		// add(label, BorderLayout.NORTH);
		//
		// }
		add(panel, BorderLayout.CENTER);
	}

	class MapBytesPanelImpl extends GenericPropertyPanel {

		public MapBytesPanelImpl(MapMessage message, boolean editable) throws JMSException {

			super(editable);

			if (message != null) {
				for (Enumeration<String> e = message.getMapNames(); e.hasMoreElements();) {
					PropertyRow row = new PropertyRow();
					row.name = e.nextElement();

					// row.value = message.getObject(row.name);
					Object object = message.getObject(row.name);
					if (object instanceof byte[]) {
						try {
							object = new String((byte[]) object, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							throw new RuntimeException(object.toString(), e1);
						}
					}
					row.value = object;

					row.type = PropertyType.fromObject(row.value);
					model.addRow(row);
				}
			}
		}

		public void setProperties(MapMessage message) throws NumberFormatException, JMSException {

			for (int i = 0; i < model.getRowCount(); i++) {
				PropertyRow row = model.getRow(i);
				if (!TextUtils.isEmpty(row.name)) {
					switch (row.type) {
					case INT:
						message.setInt(row.name, Integer.decode(row.value.toString()));
						break;
					case DOUBLE:
						message.setDouble(row.name, Double.parseDouble(row.value.toString()));
						break;
					case LONG:
						message.setLong(row.name, Long.decode(row.value.toString()));
						break;
					case BOOLEAN:
						message.setBoolean(row.name, Boolean.parseBoolean(row.value.toString()));
						break;
					case STRING:
						message.setString(row.name, row.value.toString());
						break;
					// case CHAR:
					// message.setChar(row.name, row.value.toString().charAt(0));
					// break;
					case BYTE:
						message.setByte(row.name, Byte.parseByte(row.value.toString()));
						break;
					}
				}
			}
		}
	}

	// TODO personal: El paquete tiene que ser el mismo...
	@Override
	void onMessage(Message message) throws JMSException {

		panel.setProperties((MapMessage) message);
	}

	@Override
	boolean supports(MessageType type) {

		return type == MessageType.MapMessage;
	}
}
