package com.example.expensestracker;

import java.util.ArrayList;
import java.util.List;

public class ListClaims<Key, Value> {
	//List of the claims
	//Planning on using Destination as Key, claims as value
	
	List<Node<Key, Value>> list = new ArrayList<Node<Key,Value>>();
	
	public void addClaim(Key key, Value value) {
		Node<Key, Value> node = new Node<Key, Value>(key, value);
		list.add(node);
	}
		
	public List<Node<Key, Value>> getClaims() {
		return list;
	}
	
}


class Node<Key, Value> {
	protected Key key;
	protected Value value;
	
	public Node(Key key, Value value) {
		this.key = key;
		this.value = value;
	}

	public Key getKey() {
		return this.key;
	}
	
	public Value getValue() {
		return this.value;
	}
}