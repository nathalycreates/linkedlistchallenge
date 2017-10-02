package com.idklabs.app;


import java.lang.Math;

/**
 * A grid using a linked list.
 */
public class LinkedListGrid {
	
	/**
	 * A node on a {@link LinkedListGrid}.
	 */
	public interface GridNode {

		
		/**
		 * Get the value of node.
		 * 
		 */
		Object getValue();
		
		/**
		 * Set the value of node.
		 */
		void setValue(Object value);
	}
	
	/**
	 * Layer grows in two directions, so I'm incrementing the linked list by two.
	 */
	private static final int LAYER_INCREMENT = 2;
	
	private final int max;
	private final Node originNode;
	private int size;
	
	public LinkedListGrid(int max) {
		if (max % 2 == 0 || max < 1) {
			throw new IllegalArgumentException("The max size of grid must be a positionitive odd number.");
		}
		
		this.max = max;
		this.originNode = new Node(1);
		this.size = 1;
	}
	
	/**
	 * Get the size of the linked list grid. This is defined as the sum of nodes
	 * along the X and Y of the grid. For a grid of 3x3, its size is indicated by
	 * 5.
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Get the node at the provided coordinates. This fails for coordinates outside
	 * of the currently allocated nodes. Use {@link #addLayer} to grow the grid.
	 */
	public GridNode getNode(int x, int y) {
		int localMax = (int) Math.ceil(size / 2d);
		if (x > localMax || y > localMax) {
			throw new IllegalArgumentException("Coordinates are out of bounds: " + localMax);
		}
		
		return getNode((x + y) - 2).getView(x - 1);
	}
	
	/**
	 * Adds a layer to the grid. This increases the dimensions of the grid
	 * by 1 along its X and Y axes.
	 */
	public void addLayer() {
		if (size >= max) {
			throw new IllegalStateException("The grid has reached its maximum size.");
		}
		
		Node currentNode = getNode(size - 1);
		for (int i = 0; i < LAYER_INCREMENT; i++) {
			size++;
			Node nextNode = new Node(size);
			currentNode.setNextNode(nextNode);
			currentNode = nextNode;
		}
	}
	
	/**
	 * Get node in the linked list. This is a zero-indexed list.
	 */
	private Node getNode(int position) {
		Node currentNode = originNode;
		while (currentNode.getNextNode() != null && position > 0) {
			currentNode = currentNode.getNextNode();
			position--;
		}
		
		if (position != 0) {
			throw new IllegalStateException("The node requested is out of bounds.");
		}
		
		return currentNode;
	}
	
	/**
	 * The internal node implementation. The node holds an array of slots that 
	 * hold the values along the X axis assigned to this node.
	 */
	private static class Node {
		
		private Node nextNode;
		private Object[] slots;
		
		private Node(int size) {
			this.slots = new Object[size];
		}
		
		private NodeView getView(int position) {
			return new NodeView(this, position);
		}
		
		private Object getSlot(int position) {
			return this.slots[position];
		}
		
		private void setSlot(int position, Object value) {
			this.slots[position] = value;
		}
		
		private Node getNextNode() {
			return this.nextNode;
		}
		
		private void setNextNode(Node nextNode) {
			this.nextNode = nextNode;
		}
	}
	
	/**
	 * Implementation of {@link GridNode} backed by the internal linked list {@link Node}.
	 * This view exposes a slot on the X axis for the given node.
	 */
	private static class NodeView implements GridNode {
		
		private final Node node;
		private final int expose;
		
		private NodeView(Node node, int xposition) {
			this.node = node;
			this.expose = expose;
		}
		
		@Override
		public Object getValue() {
			return node.getSlot(expose);
		}
		
		@Override
		public void setValue(Object value) {
			node.setSlot(expose, value);
		}
	}
}
