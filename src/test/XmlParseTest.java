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

/**
 * Test Class
 */
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
			network.parseNetworkFile(new File("xml/plan10x10.xml"));
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
			network.parseNetworkFile(new File("xml/plan20x20.xml"));
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
	
	public void testOneClientTwoAdresses()
	{
		Network network = new Network();
		try {
			network.parseNetworkFile(new File("xml/plan20x20.xml"));
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
		File f2 = new File("xml/livraison_Warning_UnClientAPlusieursAdresses.xml");
		boolean warning = false;
		try {
			network.parseDeliveryRequestFile(f2);
		} catch (InvalidNetworkFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDeliveryRequestFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WarningDeliveryRequestFile e){
			warning = true;
			e.printStackTrace();
		}
		assertSame(warning, true);
	}
	
	public void testOneAddressTwoClients()
	{
		Network network = new Network();
		try {
			network.parseNetworkFile(new File("xml/plan20x20.xml"));
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
		File f2 = new File("xml/livraison_Error_UneAdressePourPlusieursClients.xml");
		boolean error = false;
		try {
			network.parseDeliveryRequestFile(f2);
		} catch (InvalidNetworkFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDeliveryRequestFileException e) {
			// TODO Auto-generated catch block
			error = true;
			e.printStackTrace();
		} catch (WarningDeliveryRequestFile e){		
			e.printStackTrace();
		}
		assertSame(error, true);
	}
	
	public void testTwoTimesSameClient()
	{
		Network network = new Network();
		try {
			network.parseNetworkFile(new File("xml/plan20x20.xml"));
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
		File f2 = new File("xml/livraison_Warning_2foisLeMemeClient.xml");
		boolean warning = false;
		try {
			network.parseDeliveryRequestFile(f2);
		} catch (InvalidNetworkFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDeliveryRequestFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WarningDeliveryRequestFile e){	
			warning = true;
			e.printStackTrace();
		}
		assertSame(warning, true);
	}
	
	public void testNetworkInsteadOfDeliveries()
	{
		FileChooserView networkChooserView = new FileChooserView();
		File f1 = networkChooserView.paintOpen();
		Network network = new Network();
		boolean error = false;
		try {
			network.parseNetworkFile(f1);
		} catch (InvalidNetworkFileException e) {
			error = true;
			e.printStackTrace();
		} catch (InvalidDeliveryRequestFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WarningDeliveryRequestFile e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertSame(error, true);	
	}
	
	public void testDeliveriesInsteadOfNetwork()
	{
		Network network = new Network();
		boolean error = false;
		try {
			network.parseNetworkFile(new File("xml/plan10x10.xml"));
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
			error = true;
			e.printStackTrace();
		}  catch (WarningDeliveryRequestFile e){
			e.printStackTrace();
		}
		assertSame(error, true);
	}
	
	public void testNodeNotInNetwork()
	{
		Network network = new Network();
		boolean error = false;
		try {
			network.parseNetworkFile(new File("xml/plan10x10.xml"));
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
			error = true;
			e.printStackTrace();
		}  catch (WarningDeliveryRequestFile e){
			e.printStackTrace();
		}
		assertSame(error, true);
	}
	
	public void testTimeSlotOverlap()
	{
		Network network = new Network();
		boolean error = false;
		try {
			network.parseNetworkFile(new File("xml/plan10x10.xml"));
		} catch (InvalidNetworkFileException e) {
			// TODO Auto-generated catch block
			error = true;
			e.printStackTrace();
		} catch (InvalidDeliveryRequestFileException e) {
			// TODO Auto-generated catch block
			error = true;
			e.printStackTrace();
		} catch (WarningDeliveryRequestFile e) {
			// TODO Auto-generated catch block
			error = true;
			e.printStackTrace();
		}
		FileChooserView deliveryRequestChooserView = new FileChooserView();
		File f2 = deliveryRequestChooserView.paintOpen();
		try {
			network.parseDeliveryRequestFile(f2);
		} catch (InvalidNetworkFileException e) {
			// TODO Auto-generated catch block
			error = true;
			e.printStackTrace();
		} catch (InvalidDeliveryRequestFileException e) {
			error = true;
			e.printStackTrace();
		}  catch (WarningDeliveryRequestFile e){
			error = true;
			e.printStackTrace();
		}
		assertSame(error, false);
	}
	
	public void testDeliveryInWarehouse()
	{
		Network network = new Network();
		boolean error = false;
		try {
			network.parseNetworkFile(new File("xml/plan10x10.xml"));
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
			e.printStackTrace();
		}  catch (WarningDeliveryRequestFile e){
			e.printStackTrace();
		} catch (Exception e)
		{
			error = true;
		}
		assertSame(error, true);
	}
	
	public void testDepartureSupArrivalTimeSlot()
	{
		Network network = new Network();
		boolean error = false;
		try {
			network.parseNetworkFile(new File("xml/plan10x10.xml"));
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
			error = true;
			e.printStackTrace();
		}  catch (WarningDeliveryRequestFile e){
			e.printStackTrace();
		}
		assertSame(error, true);
	}
	
	public void testNoDeliveryInTimeSlot()
	{
		Network network = new Network();
		boolean warning = false;
		try {
			network.parseNetworkFile(new File("xml/plan10x10.xml"));
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
			e.printStackTrace();
		}  catch (WarningDeliveryRequestFile e){
			warning = true;
			e.printStackTrace();
		}
		assertSame(warning, true);
	}
}
