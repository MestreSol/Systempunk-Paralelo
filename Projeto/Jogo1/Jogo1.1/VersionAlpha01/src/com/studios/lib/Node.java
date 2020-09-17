package com.studios.lib;

public class Node {
	public Vector2i tile;
	public Node parent;
	public double fCost, gCost, hCost;
	public Node(Vector2i tile, Node parent, double gCost, double hCost) {
		super();
		this.tile = tile;
		this.parent = parent;
		this.fCost = gCost+hCost;
		this.gCost = gCost;
		this.hCost = hCost;
	}
}
