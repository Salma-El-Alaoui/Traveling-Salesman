package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Dijkstra {

	/**
	 * Contain the previous node of the key
	 */
	private Map<Node, Node> mPreviousNode;

	/**
	 * Refer to the origin node of the graph
	 */
	private Node mOriginNode;

	/**
	 * Construct a Dijkstra refering to the origin node
	 * 
	 * @param originNode
	 *            Origin Node for the future calculation
	 */
	public Dijkstra(Node originNode) {
		mOriginNode = originNode;
	}

	/**
	 * Calculate the shortest path to endNode
	 * 
	 * @param endNode
	 * @return The shortest path, null if no path can be found
	 */
	public Path calculateShortestPathTo(Node endNode) {
		if (mPreviousNode == null) {
			initPreviousMap();
		}
		if (mPreviousNode.get(endNode) != null) {
			Path path = new Path();
			Node currentNode = endNode;
			Node previousNode;
			while ((previousNode = mPreviousNode.get(currentNode)) != null) {
				List<Segment> listOutSegment = previousNode.getOutSegmentList();
				for (Segment s : listOutSegment) {
					if (s.getArrivalNode() == currentNode) {
						path.addSegment(s);
					}
				}
				currentNode = previousNode;
			}
			return path;
		} else {
			return null;
		}
	}

	/**
	 * Initialize the previous map
	 */
	private void initPreviousMap() {
		mPreviousNode = new HashMap<Node, Node>();
		Map<Node, Integer> distance = new HashMap<Node, Integer>();
		List<Node> nodeToVisit = new LinkedList<Node>();

		distance.put(mOriginNode, 0);
		mPreviousNode.put(mOriginNode, null);
		nodeToVisit.add(mOriginNode);

		while (!nodeToVisit.isEmpty()) {
			Node currentNode = nodeToVisit.remove(0);
			List<Segment> outSegmentList = currentNode.getOutSegmentList();
			for (Segment outSegment : outSegmentList) {
				Node nextNode = outSegment.getArrivalNode();
				int newDistance = distance.get(currentNode)
						+ (int) outSegment.getTime();
				if (distance.get(nextNode) == null
						|| distance.get(nextNode) > newDistance) {
					nodeToVisit.remove(nextNode);
					distance.put(nextNode, newDistance);
					mPreviousNode.put(nextNode, currentNode);
					nodeToVisit.add(nextNode);
				}
			}
		}
	}

	/**
	 * Calculate the shortest path from startNode to endNode
	 * 
	 * @param originNode
	 * @param endNode
	 * @return The shortest path, null if no path can be found
	 */
	public static Path calculateShortestPath(Node originNode, Node endNode) {
		Dijkstra d = new Dijkstra(originNode);
		return d.calculateShortestPathTo(endNode);
	}
}
