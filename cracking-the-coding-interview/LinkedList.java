class LinkedList{

	/**
	 * The Node class represents a singly linked list.
	 */
	static class Node {
		Node next = null;
		int data;

		public Node(int d) {
			data = d;
		}

		/**
		 * Append an item to the list.
		 */
		void append(int d) {
			Node n = this;
			Node last = new Node(d);
		
			while (n.next != null) n = n.next;
			n.next = last;
		}

		/**
		 * Remove duplcates from singly linked list.
		 */
		void removeDups() {
			Node current = this;

			while(current.next != null) {
				Node prev = current;
				Node n = current.next;
				while (n != null) {
					if(n.data == current.data)
						prev.next = prev.next.next;
					prev = n;
					n = n.next;
				}
				current = current.next;
			}
		}

		/**
		 * Return kth to last element of list. Null is returned for error conditions.
		 */
		Node kthToLast(int k) {
			if (k <= 0 || this == null) return null;

			Node n1 = this;
			Node n2 = this;
			int count = 0;

			while(count < k && n2 != null) {
				n2 = n2.next;
				count++;
			}

			if (count < k) return null;

			while(n2 != null) {
				n1 = n1.next;
				n2 = n2.next;
			}
			return n1;
		}

		/**
		 * Delete a middle node, without a pointer to the list head.
		 * Return true, if deletion was successful. Otherwise return false.
		 */
		Boolean deleteMiddleNode(Node n) {
			if (n == null || n.next == null) return false;
			n.data = n.next.data;
			n.next = n.next.next;
			return true;
		}

		/**
		 * Rearrange the linked list, such that all elements less than val are placed 
		 * before val, and all those greater than val are positioned after val.
		 */
		Node partition(int val) {
			Node n = this;
			Node head = this;
			Node tail = this;
			Node tmp;

			while (n != null) {
				tmp = n.next;
				if (n.data < val) {	// less than val => insert node at head
					n.next = head;
					head = n;
				} else {						// gte val => insert node at tail.
					tail.next = n;
					tail = n;
				}
				n = tmp;
			}
			tail.next = null;
			return head;
		}
	}

	public static void main(String[] args) {
		Node n;
		Boolean val;

		Node linkedList = new LinkedList.Node(0);
		linkedList.append(1);
		linkedList.append(2);
		linkedList.append(3);
		linkedList.append(2);
		linkedList.append(4);

		System.out.println("after list create");
		n = linkedList;
		while(n != null) {
			System.out.println(n.data);
			n = n.next;
		}

		linkedList.removeDups();
		System.out.println("removeDups(0->1->2->3->2->4");
		n = linkedList;
		while(n != null) {
			System.out.println(n.data);
			n = n.next;
		}

		n = linkedList.kthToLast(2);
		System.out.println("kthToLast(2) = " + n.data);

		val = linkedList.deleteMiddleNode(linkedList.next.next);
		System.out.println("deleteMiddleNode(0->1->2->3->4), deleting 3rd node = " + val);
		n = linkedList;
		while(n != null) {
			System.out.println(n.data);
			n = n.next;
		}

		linkedList.append(0);
		linkedList.append(1);
		linkedList.append(2);
		linkedList.append(3);
		linkedList.append(4);

		n = linkedList.partition(2);
		System.out.println("partition(0->1->2->3->4->0->1->2->3->4) on 2");
		while(n != null) {
			System.out.println(n.data);
			n = n.next;
		}
	}
}