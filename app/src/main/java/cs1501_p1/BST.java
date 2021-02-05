/**
 * A basic Binary Search Tree class for operations mentions in BST_Inter.java
 * @author David Roberts (djr107)
 */

import java.util.*;

public class BST<T extends Comparable<T>> implements BST_Inter<T>
{
	/**
	 * Top-most node and start of the BST
	 */
	private BTNode<T> root;

	/**
	 * Number of nodes in BST
	 */
	private int size;

	/**
	 * Creates an empty BST
	 */
	public BST()
	{
		root = new BTNode(null);
		root.setLeft(null);
		root.setRight(null);
		size = 0;
	}

	/**
	 * Add a new key to the BST
	 *
	 * @param 	key Generic type value to be added to the BST
	 */
	public void put(T key)
	{
		if (root.getKey() == null)
		{
			root = new BTNode(key);
			//System.out.println("Added to top: "+key);
		}
		else
		{
			BTNode<T> curr = root;

			while(key.compareTo(curr.getKey()) != 0)
			{
				if (key.compareTo(curr.getKey()) < 0)
				{
					if (curr.getLeft() == null)
					{
						size++;
						curr.setLeft(new BTNode(key));
					}
					curr = curr.getLeft();
					//System.out.println("Went left");
				}
				else if (key.compareTo(curr.getKey()) > 0)
				{
					if (curr.getRight() == null)
					{
						size++;
						curr.setRight(new BTNode(key));
					}
					curr = curr.getRight();
					//System.out.println("Went right");
				}
			}
		}
	}

	/**
	 * Check if the BST contains a key
	 *
	 * @param	key Generic type value to look for in the BST
	 *
	 * @return	true if key is in the tree, false otherwise
	 */
	public boolean contains(T key)
	{
		if (root.getKey() == null)
		{
			return false;
		}
		else
		{
			BTNode<T> curr = root;

			while(curr != null)
			{
				//System.out.println("Key: "+key+" Curr: "+curr.getKey());
				if (key.compareTo(curr.getKey()) == 0)
				{
					//System.out.println("Found");
					return true;
				}
				if (key.compareTo(curr.getKey()) < 0)
				{
					curr = curr.getLeft();
					//System.out.println("Went left");
				}
				else if (key.compareTo(curr.getKey()) > 0)
				{
					curr = curr.getRight();
					//System.out.println("Went right");
				}
			}
			return false;
		}
	}

	/**
	 * Remove a key from the BST, if key is present
	 * 
	 * @param	key Generic type value to remove from the BST
	 */
	public void delete(T key)
	{
		if (root.getKey() == null)
		{
			System.out.println("Tree is empty, cannot delete");
		}
		else
		{
			BTNode<T> curr = root;

			while(curr != null)
			{
				if (key.compareTo(curr.getKey()) < 0)
				{
					if (key.compareTo(curr.getLeft().getKey()) == 0)
					{
						if (curr.getLeft().getLeft() != null || curr.getLeft().getRight() != null)
						{
							curr.getLeft().getLeft().setRight(curr.getLeft().getRight());
							curr.setLeft(curr.getLeft().getLeft());
						}
						else
							curr.setLeft(null);
						size--;
					}
					curr = curr.getLeft();
					//System.out.println("Went left");
				}
				else if (key.compareTo(curr.getKey()) > 0)
				{
					if (key.compareTo(curr.getRight().getKey()) == 0)
					{
						if (curr.getRight().getLeft() != null || curr.getRight().getRight() != null)
						{
							curr.getRight().getRight().setLeft(curr.getRight().getLeft());
							curr.setRight(curr.getRight().getRight());
						}
						else
							curr.setRight(null);
						size--;
					}
					curr = curr.getRight();
					//System.out.println("Went right");
				}
			}
		}
	}

	/**
	 * Determine the height of the BST
	 *
	 * <p>
	 * A single node tree has a height of 1, an empty tree has a height of 0.
	 *
	 * @return	int value indicating the height of the BST
	 */
	public int height()
	{
		if (root.getKey() == null)
		{
			System.out.println("Tree is empty, cannot delete");
			return -1;
		}
		else
		{
			ArrayList<Integer> hList = new ArrayList<Integer>();
			heightRec(root, 1, hList);

			int max = 0;
			for (Integer i : hList)
			{
				if (i > max)
					max = i;
			}
			return max;
		}
	}

	/**
	 * Goes to each node, increasing height when needed. When gets to a leaf, add height to hList,
	 * then backtrack, decreasing height and going to next node.
	 *
	 * @param curr 	current node of search
	 * 
	 * @param h  size of height at curr
	 * 
	 * @param hList list of heights
	 */
	private void heightRec(BTNode<T> curr, int h, ArrayList<Integer> hList)
	{
		//System.out.println("Height: "+h+" at curr: "+curr.getKey());
		if (curr.getLeft() != null)
		{
			//System.out.println("Going Left");
			heightRec(curr.getLeft(), h+1, hList);
		}
		//System.out.println("Done going left");
		//System.out.println("Height: "+h+" at curr: "+curr.getKey());
		if (curr.getRight() != null)
		{
			//System.out.println("Going Right");
			heightRec(curr.getRight(), h+1, hList);
		}
		else
		{
			hList.add(h);
		}
		//System.out.println("Done going right");
	}

	/**
	 * Determine if the BST is height-balanced
	 *
	 * <p>
	 * A height balanced binary tree is one where the left and right subtrees
	 * of all nodes differ in height by no more than 1.
	 *
	 * @return true if the BST is height-balanced, false if it is not
	 */
	public boolean isBalanced()
	{
		if (root.getKey() == null)
		{
			System.out.println("Tree is empty");
			return true;
		}
		else
		{
			ArrayList<Integer> hList = new ArrayList<Integer>();
			heightRec(root, 1, hList);

			for (int i=0; i<hList.size()-1; i++)
			{
				/*
				System.out.print(hList.get(i)+"!="+(hList.get(i+1))+": ");
				if(hList.get(i) != hList.get(i+1))
					System.out.println("True");
				else
					System.out.println("False");

				System.out.print(hList.get(i)+"!="+(hList.get(i+1)+1)+": ");
				if(hList.get(i) != hList.get(i+1)+1)
					System.out.println("True");
				else
					System.out.println("False");

				System.out.print(hList.get(i)+"!="+(hList.get(i+1)-1)+": ");
				if(hList.get(i) != hList.get(i+1)-1)
					System.out.println("True");
				else
					System.out.println("False");
				*/

				if (hList.get(i) != hList.get(i+1)+1 && hList.get(i) != hList.get(i+1)-1 && hList.get(i) != hList.get(i+1))
				{
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Produce a ':' separated String of all keys in ascending order
	 *
	 * <p>
	 * Perform an in-order traversal of the tree and produce a String
	 * containing the keys in ascending order, separated by ':'s.
	 * 
	 * @return	TBD if kept
	 */
	public String inOrderTraversal()
	{
		if (root.getKey() == null)
		{
			return "Tree is empty";
		}
		else
		{
			ArrayList<T> tList = new ArrayList<T>();
			getKeysIOT(root, tList);

			StringBuilder sb = new StringBuilder();
			for (int i=0; i<tList.size(); i++)
			{
				sb.append(tList.get(i));
				if (i != tList.size()-1)
				{
					sb.append(":");
				}
			}
			return sb.toString();
		}
	}

	/**
	 * Goes to each node in correct order, adding key to ArrayList
	 *
	 * @param curr 	current node of search
	 * 
	 * @param tList list of keys
	 */
	private void getKeysIOT(BTNode<T> curr, ArrayList<T> tList)
	{
		if (curr.getLeft() != null)
		{
			getKeysIOT(curr.getLeft(), tList);
		}
		tList.add(curr.getKey());
		if (curr.getRight() != null)
		{
			getKeysIOT(curr.getRight(), tList);
		}
	}

	/**
	 * Produce String representation of the BST
	 * 
	 * <p>
	 * Perform a pre-order traversal of the BST in order to produce a String
	 * representation of the BST. The reprsentation should be a comma separated
	 * list where each entry represents a single node. Each entry should take
	 * the form: *type*(*key*). You should track 4 node types:
	 *     `R`: The root of the tree
	 *     `I`: An interior node of the tree (e.g., not the root, not a leaf)
	 *     `L`: A leaf of the tree
	 *     `X`: A stand-in for a null reference
	 * For each node, you should list its left child first, then its right
	 * child. You do not need to list children of leaves. The `X` type is only
	 * for nodes that have one valid child.
	 * 
	 * @return	String representation of the BST
	 */
	public String serialize()
	{
		if (root.getKey() == null)
		{
			return "Tree is empty";
		}
		else
		{
			StringBuilder sb = new StringBuilder();
			sb.append("R("+root.getKey()+")");

			getKeysSER(root, sb);

			return sb.toString();
		}
	}

	private void getKeysSER(BTNode<T> curr, StringBuilder sb)
	{
		if (curr.getLeft() != null)
		{
			if (curr.getLeft().getLeft() == null && curr.getLeft().getRight() == null)
				sb.append(",L("+curr.getLeft().getKey()+")");
			else if (curr.getLeft().getLeft() != null || curr.getLeft().getRight() != null)
				sb.append(",I("+curr.getLeft().getKey()+")");
			getKeysSER(curr.getLeft(), sb);
		}
		if (curr.getRight() != null)
		{
			if (curr.getRight().getLeft() == null && curr.getRight().getRight() == null)
				sb.append(",L("+curr.getRight().getKey()+")");
			else if (curr.getRight().getLeft() != null || curr.getRight().getRight() != null)
				sb.append(",I("+curr.getRight().getKey()+")");
			getKeysSER(curr.getRight(), sb);
		}
	}

	/**
	 * Produce a deep copy of the BST that is reversed (i.e., left children
	 * hold keys greater than the current key, right children hold keys less
	 * than the current key).
	 *
	 * @return	Deep copy of the BST reversed
	 */
	public BST_Inter<T> reverse()
	{
		BTNode<T> curr = root;

		reverseRec(curr);

		return this;
	}

	private void reverseRec(BTNode<T> curr)
	{
		//System.out.println("Curr: "+curr.getKey());
		if (curr.getLeft() != null)
		{
			//System.out.println("Going Left");
			BTNode<T> temp = curr.getLeft();
			//System.out.println("Temp: "+temp.getKey());
			if (curr.getRight() != null)
			{
				curr.setLeft(curr.getRight());
				//System.out.println("Left now: "+curr.getLeft().getKey());
				reverseRec(curr.getLeft());
			}
			curr.setRight(temp);
			//System.out.println("Right now: "+curr.getRight().getKey());

			reverseRec(curr.getRight());
		}
		else if (curr.getRight() != null)
		{
			//System.out.println("Going Right");
			curr.setLeft(curr.getRight());
			//System.out.println("Left now: "+curr.getLeft().getKey());

			reverseRec(curr.getLeft());
		}
		//System.out.println("Popped");
	}
}