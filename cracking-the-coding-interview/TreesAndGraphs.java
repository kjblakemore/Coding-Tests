import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class TreesAndGraphs {

	/**
	 * Graphs consist of Nodes which are accessed by their unique name.
	 */
	static class Graph {
		private HashMap <String, Node> map;

		public Graph() {
			map = new HashMap<String, Node>();
		}

		/**
		 * Add a Node to the Graph.
		 */
		public void addNode(String name) {
			map.put(name, new Node(name));
		}

		/**
		 * Add edge to graph.  If edge was successfully added, return true.
		 * Otherwise, return false. 
		 */
		public Boolean addEdge(String from, String to) {
			Node fromNode = map.get(from);
			if(fromNode == null) {
				System.out.println("From name not in graph!");
				return false;
			}
			Node toNode = map.get(to);
			if(toNode == null) {
				System.out.println("To name not in graph!");
				return false;
			}

			fromNode.addChild(toNode);
			return true;
		}

		/**
		 * Search for a path between start and goal. Return true if path is found,
		 * else false.
		 */
		public Boolean search(String start, String goal) {
			Node startNode = map.get(start);
			if(startNode == null) {
				System.out.println("Start node is null!  No path exists.");
				return false;
			}
			Node goalNode = map.get(goal);
			if(goalNode == null) {
				System.out.println("Goal node is null!  No path exisits.");
				return false;
			}

			// queued keeps track nodes that have been queued, to avoid revisits.
			HashSet<Node> queued = new HashSet<Node>();

			// toVist holds nodes to be visited, ordered in increasing layer depth.
			Queue<Node> toVisit = new LinkedList<Node>();

			// Traverse graph from startNode, one layer at a time,
			// until graph is exhausted or goalNode is found.
			toVisit.add(startNode);
			queued.add(startNode);

			while(!toVisit.isEmpty()) {
				Node node = toVisit.remove();

				if(node == goalNode)	// found path!
					return true;

				// Add each unseen adjacent node to the queue of nodes to be visited.
				for(Node adjacentNode: node.getAdjacentNodes()) {
					if(!queued.contains(adjacentNode)) {
						queued.add(adjacentNode);
						toVisit.add(adjacentNode);
					}
				}
			}

			return false;	// path not found
		}

		/**
		 * Each Node contains a unique name and a set of children nodes.
		 */
		class Node {
			private String name;
			private HashSet<Node> children;

			public Node(String name) {
				this.name = name;
				this.children = new HashSet<Node>();
			}

			/**
			 * Add a child node to this node.
			 */
			public void addChild(Node toNode) {
				children.add(toNode);
			}

			/**
			 * Return the set of nodes directly reachable by this node.
			 */
			public HashSet<Node> getAdjacentNodes() {
				return children;
			}
		}
	}

	public static void main(String[] args) {
		Graph graph = new Graph();

		graph.addNode("a");
		graph.addNode("b");
		graph.addNode("c");
		graph.addNode("d");
		graph.addNode("e");

		graph.addEdge("a", "e");
		graph.addEdge("e", "a");
		graph.addEdge("a", "b");
		graph.addEdge("a", "c");
		graph.addEdge("c", "b");
		graph.addEdge("b", "d");

		Boolean val;

		val = graph.search("a", "d");
		System.out.println("search(a, d) = " + val);

		val = graph.search("c", "e");
		System.out.println("search(c, e) = " + val);
	}
}