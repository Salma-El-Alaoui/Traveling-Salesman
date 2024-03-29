package model;

import java.util.*;

/**
 * Class that represents a tour
 */
public class Tour {

	/**
	 * Constructor
	 * 
	 * @param wareHouse
	 *            Warehouse of the tour
	 */
	public Tour(Node wareHouse) {
		mWarehouse = wareHouse;
		mPathList = new ArrayList<Path>();
		mDeliveryList = new ArrayList<Delivery>();
	}

	/**
	 * Warehouse of the tour
	 */
	private Node mWarehouse;

	/**
	 * List of the deliveries to be delivered during the tour
	 */
	private List<Delivery> mDeliveryList;

	/**
	 * List of the paths contained in the tour
	 */
	private List<Path> mPathList;

	/**
	 * Departure hour from the warehouse
	 */
	private int mStartHour;

	/**
	 * Adds a delivery to the ordered delivery list
	 * 
	 * @param delivery
	 * @return
	 */
	public void addDelivery(Delivery delivery) {
		this.mDeliveryList.add(delivery);
	}

	/**
	 * Adds a path to the ordered path list
	 * 
	 * @param path
	 * @return
	 */
	public void addPath(Path path) {
		this.mPathList.add(path);
	}

	/**
	 * Insert newDelivery after previousDelivery into the tour and recalculate
	 * locally the path
	 * 
	 * @param previousDelivery
	 *            is null if we want to insert the delivery after the warehouse
	 * @param newDelivery
	 */
	public boolean insertDelivery(Delivery previousDelivery,
			Delivery newDelivery) {

		Node previousNode = null;
		Node newNode = newDelivery.getNode();
		Node nextNode = null;

		if (previousDelivery != null) // Not after warehouse
		{
			newDelivery.setTimeSlot(previousDelivery.getTimeSlot());
			previousNode = previousDelivery.getNode();

			for (int i = 0; i < mDeliveryList.size(); i++) {
				if (mDeliveryList.get(i).getNode().getId() == previousNode
						.getId()) {
					if (i != mDeliveryList.size() - 1) {
						nextNode = mDeliveryList.get(i + 1).getNode();
						mDeliveryList.add(i + 1, newDelivery);
						break;
					} else {
						nextNode = mWarehouse;
						mDeliveryList.add(newDelivery);
						break;
					}
				}
			}
			if (nextNode == null) {
				System.out
						.println("Can't find node after the one we want to insert");
				return false;
			}
		} else // after warehouse
		{
			previousNode = mWarehouse;
			Delivery nextDelivery = mDeliveryList.get(0);
			nextNode = nextDelivery.getNode();
			newDelivery.setTimeSlot(nextDelivery.getTimeSlot());
			mDeliveryList.add(0, newDelivery);
		}

		Path firstPath = Dijkstra.calculateShortestPath(previousNode, newNode);
		Path secondPath = Dijkstra.calculateShortestPath(newNode, nextNode);

		for (int i = 0; i < mPathList.size(); i++) {
			Node departureNode = mPathList.get(i).getSegment(0)
					.getDepartureNode();
			if (departureNode.getId() == previousNode.getId()) {
				mPathList.set(i, firstPath);
				if (i != mPathList.size() - 1) {
					mPathList.add(i + 1, secondPath);
				} else {
					mPathList.add(secondPath);
				}
				break;
			}
		}

		updateHour();
		if (newDelivery.getDeliveryHour() > newDelivery.getTimeSlot()
				.getEndHour()) {
			newDelivery.setTimeSlot(nextNode.getDelivery().getTimeSlot());
		}

		return true;
	}

	/**
	 * Remove the delivery from the tour and recalculate locally the path
	 * 
	 * @param Delivery
	 *            delivery to remove
	 * @return the node before the removed delivery
	 */
	public Node removeDelivery(Delivery delivery) {
		Node node = delivery.getNode();
		Node previousNode = null;
		Node nextNode = null;

		if (mDeliveryList.size() <= 1) {
			return null;
		}

		for (int i = 0; i < mDeliveryList.size(); i++) {
			Delivery currentDelivery = mDeliveryList.get(i);
			if (currentDelivery.getNode().getId() == node.getId()) {
				if (i != mDeliveryList.size() - 1 && i != 0) {
					previousNode = mDeliveryList.get(i - 1).getNode();
					nextNode = mDeliveryList.get(i + 1).getNode();
				} else if (i != mDeliveryList.size() - 1 && i == 0) {
					previousNode = mWarehouse;
					nextNode = mDeliveryList.get(1).getNode();
				} else {
					previousNode = mDeliveryList.get(i - 1).getNode();
					nextNode = mWarehouse;
				}
				currentDelivery.getTimeSlot().removeDelivery(currentDelivery);
				mDeliveryList.remove(i);
				break;
			}
		}

		if (previousNode == null || nextNode == null) {
			System.out
					.println("Can't find nodes around the one we want to remove");
			return null;
		}

		Network network = node.getNetwork();
		Path newPath = network.calculateShortestPath(previousNode, nextNode);

		for (int i = 0; i < mPathList.size(); i++) {
			Node departureNode = mPathList.get(i).getSegment(0)
					.getDepartureNode();
			if (departureNode.getId() == node.getId()) {
				mPathList.set(i, newPath);
				if (i != 0) {
					mPathList.remove(i - 1);
				} else {
					mPathList.remove(mPathList.size() - 1);
				}
			}
		}
		delivery.getNode().setDelivery(null);
		updateHour();
		return previousNode;
	}

	/**
	 * Updates the hours of all the deliveries
	 */
	public void updateHour() {
		if (!mDeliveryList.isEmpty()) {
			mStartHour = mDeliveryList.get(0).getTimeSlot().getStartHour()
					- mPathList.get(0).getGlobalTime();
			int globalTime = mStartHour;
			for (int i = 0; i < mDeliveryList.size(); i++) {
				globalTime += mPathList.get(i).getGlobalTime();
				Delivery delivery = mDeliveryList.get(i);
				delivery.setArrivalHour(globalTime);
				globalTime = delivery.getDepartureHour();
			}
		}
	}

	/**
	 * Returns the list of deliveries delivered during the tour
	 * 
	 * @return the list of deliveries delivered during the tour
	 */
	public List<Delivery> getDeliveryList() {
		return mDeliveryList;
	}

	/**
	 * Returns the list of the paths contained in the tour
	 * 
	 * @return the list of the paths contained in the tour
	 */
	public List<Path> getPathList() {
		return mPathList;
	}
}