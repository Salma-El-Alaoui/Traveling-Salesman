package test;

import java.io.File;

import Model.DeliveryRequest;
import Model.Network;
import View.FileChooserView;
import junit.framework.*;

public class XmlParseTest extends TestCase{
	public void testNetworkNotNull()
	{
		FileChooserView networkChooserView = new FileChooserView();
		File f1 = networkChooserView.paint();
		Network network = new Network();
		network.parseNetworkFile(f1);
		assertNotSame(network, new Network());	
	}
	
	public void testDeliveryRequest10NotNull()
	{
		Network network = new Network();
		network.parseNetworkFile(new File("plan10x10.xml"));
		FileChooserView deliveryRequestChooserView = new FileChooserView();
		File f2 = deliveryRequestChooserView.paint();
		network.parseDeliveryRequestFile(f2);
		assertNotSame(network.getDeliveryRequest(), new DeliveryRequest());
	}
	
	public void testDeliveryRequest20NotNull()
	{
		Network network = new Network();
		network.parseNetworkFile(new File("plan20x20.xml"));
		FileChooserView deliveryRequestChooserView = new FileChooserView();
		File f2 = deliveryRequestChooserView.paint();
		network.parseDeliveryRequestFile(f2);
		assertNotSame(network.getDeliveryRequest(), new DeliveryRequest());
	}
}
