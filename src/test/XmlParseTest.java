package test;

import java.io.File;

import Model.DeliveryRequest;
import Model.InvalidDeliveryRequestFileException;
import Model.InvalidNetworkFileException;
import Model.Network;
import Model.WarningDeliveryRequestFile;
import View.FileChooserView;
import View.WarningDialogView;
import junit.framework.*;

public class XmlParseTest extends TestCase{
	public void testNetworkNotNull() throws InvalidNetworkFileException,InvalidDeliveryRequestFileException
	{
		FileChooserView networkChooserView = new FileChooserView();
		File f1 = networkChooserView.paintOpen();
		Network network = new Network();
		try {
			network.parseNetworkFile(f1);
		} catch (InvalidNetworkFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDeliveryRequestFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WarningDeliveryRequestFile e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotSame(network, new Network());	
	}

	public void testDeliveryRequest10NotNull()
	{
		Network network = new Network();
		try {
			network.parseNetworkFile(new File("plan10x10.xml"));
		} catch (InvalidNetworkFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDeliveryRequestFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WarningDeliveryRequestFile e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileChooserView deliveryRequestChooserView = new FileChooserView();
		File f2 = deliveryRequestChooserView.paintOpen();
		try {
			network.parseDeliveryRequestFile(f2);
		} catch (InvalidNetworkFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDeliveryRequestFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (WarningDeliveryRequestFile e){
			e.printStackTrace();
		}
		assertNotSame(network.getDeliveryRequest(), new DeliveryRequest(network));
	}

	public void testDeliveryRequest20NotNull()
	{
		Network network = new Network();
		try {
			network.parseNetworkFile(new File("plan20x20.xml"));
		} catch (InvalidNetworkFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDeliveryRequestFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WarningDeliveryRequestFile e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileChooserView deliveryRequestChooserView = new FileChooserView();
		File f2 = deliveryRequestChooserView.paintOpen();
		try {
			network.parseDeliveryRequestFile(f2);
		} catch (InvalidNetworkFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDeliveryRequestFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WarningDeliveryRequestFile e){
			e.printStackTrace();
		}
		assertNotSame(network.getDeliveryRequest(), new DeliveryRequest(network));
	}
}
