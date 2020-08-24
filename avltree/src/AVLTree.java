

/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {

	private IAVLNode root;
//	private IAVLNode treeMin;
//	private IAVLNode treeMax;	
	private IAVLNode currentNode;
	
	public AVLTree(IAVLNode root) {
		this.root = new AVLNode(root.getKey(),root.getValue());
		this.root.setHeight(root.getHeight());
		this.root.setLeft(root.getLeft());
		this.root.setParent(root.getParent());
		this.root.setRight(root.getRight());
		this.root.setSize(root.getSize());
	}
	
	public AVLTree() {
		root = new AVLNode();
		root.setLeft(new AVLNode());
		root.setRight(new AVLNode());
	}
	
	 public void rightRotate(IAVLNode node) {
		 	
		 
		   //IAVLNode temp=node.getLeft().getRight();
		   IAVLNode temp1=node.getLeft();
		   if(node.getParent().isRealNode()) {
			   IAVLNode parent=node.getParent();
			   if(parent.getKey()>node.getKey()) {
				   parent.setRight(temp1);
			   }
			   else {
				   parent.setLeft(temp1);

			   }
			   if(node.getLeft().getRight().isRealNode()) {
				   IAVLNode temp=node.getLeft().getRight();
				   node.setLeft(temp);
				   temp.setParent(node);
			   }
			   temp1.setRight(node);
			   temp1.setParent(parent);
			   node.setParent(temp1);
		   }
		   else {
			   if(node.getLeft().getRight().isRealNode()) {
				   IAVLNode temp=node.getLeft().getRight();
				   node.setLeft(temp);
				   temp.setParent(node);
			   }
			   temp1.setRight(node);
			   temp1.setParent(null);
			   
			   node.setParent(temp1);
			   root=temp1;
			  // System.out.println(node.getRight().getKey()+ "k");

		   }
		   
		   node.setSize(node.getLeft().getSize() + node.getRight().getSize() + 1);
		   temp1.setSize(node.getLeft().getSize() + node.getRight().getSize() + 1);
	   }
	 
	 public void leftRotate(IAVLNode node) {
		   //IAVLNode temp=node.getRight().getLeft();
		   IAVLNode temp1=node.getRight();
		   if(node.getParent().isRealNode()) {
			   IAVLNode parent=node.getParent();
			   if(parent.getKey()<node.getKey()) {
				   parent.setRight(temp1);
			   }
			   else {
				   parent.setLeft(temp1);

			   }
			   if(node.getRight().getLeft().isRealNode()) {
				   IAVLNode temp=node.getLeft().getRight();
				   node.setRight(temp);
				   temp.setParent(node);
			   }
			   temp1.setLeft(node);
			   temp1.setParent(parent);
			   node.setParent(temp1);
		   }
		   else {
			   if(node.getRight().getLeft().isRealNode()) {
				   IAVLNode temp=node.getLeft().getRight();
				   node.setRight(temp);
				   temp.setParent(node);
			   }
			   temp1.setLeft(node);
			   temp1.setParent(null);
			   node.setParent(temp1);
			   root=temp1;
		   }
		   
		   node.setSize(node.getLeft().getSize() + node.getRight().getSize() + 1);
		   temp1.setSize(node.getLeft().getSize() + node.getRight().getSize() + 1);
	   }
	 
	 public void leftThenRightRotate(IAVLNode node) {
		   node=node.getLeft();
		   leftRotate(node);
		   node=node.getParent().getParent();
	   rightRotate(node);
	   }
     
	 public void rightThenLeftRotate(IAVLNode node) {
		   node=node.getRight();
		   rightRotate(node);
		   node=node.getParent().getParent();
		   leftRotate(node);
		  
	   }
	
		
	private void updateSizeToRoot(IAVLNode node, int num) {////////////
		node.modifySize(num);
		while(node.getParent()!=null) {
			node = node.getParent();
			node.modifySize(num);
		}
	}

	
	private boolean isCase1(IAVLNode node) {
		return node.getRight().getHeight() == node.getLeft().getHeight();
	}
	
	private boolean isCase2Right(IAVLNode node) {
		if(node.getLeft().getHeight() - node.getRight().getHeight() != 2) return false;
		return node.getRight().getLeft().getHeight() == node.getRight().getRight().getHeight();
	}
	
	private boolean isCase2Left(IAVLNode node) {
		if(node.getRight().getHeight() - node.getLeft().getHeight() != 2) return false;
		return node.getLeft().getLeft().getHeight() == node.getLeft().getRight().getHeight();
	}
	
	private boolean isCase3Right(IAVLNode node) {
		if(node.getRight().getHeight() - node.getLeft().getHeight() != 2) return false;
		return node.getRight().getLeft().getHeight() - node.getRight().getRight().getHeight() == 1;
	}
	
	private boolean isCase3Left(IAVLNode node) {
		if(node.getRight().getHeight() - node.getLeft().getHeight() != -2) return false;
		return node.getLeft().getLeft().getHeight() - node.getLeft().getRight().getHeight() == -1;
	}
	
	private boolean isCase4Right(IAVLNode node) {
		if(node.getRight().getHeight() - node.getLeft().getHeight() != 2) return false;
		return node.getRight().getLeft().getHeight() - node.getRight().getRight().getHeight() == -1;
	}
	
	private boolean isCase4Left(IAVLNode node) {
		if(node.getRight().getHeight() - node.getLeft().getHeight() != -2) return false;
		return node.getLeft().getLeft().getHeight() - node.getLeft().getRight().getHeight() == 1;
	}
		
	public int delRrebalance(IAVLNode node) {////////
		int numsOfRebalancing = 0;
		while (node!=null && !isBalanced(node)) {
			if (isCase1(node)) {
				demote(node);
				numsOfRebalancing++;				
			}
			else if (isCase2Right(node)) {
				leftRotate(node);
				demote(node);
				premote(node.getRight());
				numsOfRebalancing+=3;
			}
			else if (isCase2Left(node)) {
				rightRotate(node);
				demote(node);
				premote(node.getLeft());
				numsOfRebalancing+=3;
			}
			else if (isCase3Right(node)) {
				leftRotate(node);
				demote(node);
				demote(node);
				numsOfRebalancing+=3;
				node = node.getParent();
			}
			else if (isCase3Left(node)) {
				rightRotate(node);
				demote(node);
				demote(node);
				numsOfRebalancing+=3;
				node = node.getParent();
			}
			else if (isCase4Right(node)) {
				rightThenLeftRotate(node);
				demote(node);
				demote(node);
				node = node.getParent();
				premote(node);
				demote(node.getRight());
				numsOfRebalancing+=6;
			}
			else { //isCase4Left
				leftThenRightRotate(node);
				demote(node);
				demote(node);
				node = node.getParent();
				premote(node);
				demote(node.getLeft());
				numsOfRebalancing+=6;
			}
			node = node.getParent();
		}
		return numsOfRebalancing;
	}
	
	public boolean isLeaf(IAVLNode node) { // checks if a node is a real leaf
		
		if(node.getLeft().isRealNode() | node.getRight().isRealNode()) {
			return false;
		}
		return true;
	}
	
	public boolean isUnarNode(IAVLNode node) {///////////
		if(node.getLeft().isRealNode()) {
			if(node.getRight().isRealNode()) {
				return false;
			}
			return true;
		}
		if(node.getRight().isRealNode()) {
			return true;
		}
		return false;
	}
	
  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
	  if (root.getKey() == -1) return true;
	  return false;
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k)
  {
	  IAVLNode temp=root;
	  IAVLNode temp1=root;
	  
	 while(temp.isRealNode()) {
		  if(temp.getKey()==k) {
			  currentNode=temp;
			  return temp.getValue();
		  }
		  if (temp.getKey()>k) {
			  temp1=temp;
			  temp=temp.getLeft();
		  }
		  else if(temp.getKey()<k) {
			  temp1=temp;
			  temp=temp.getRight();
		  }
	  }
	  currentNode=temp1;
	  return null;
	 // to be replaced by student code
  }

  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the AVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
  public int insert(int k, String i) {
	   IAVLNode node=new AVLNode(k,i);
	   boolean ok=false;
	   if(search(k)!=null) {
		   return -1;
	   }
	   
	   if (empty()) {
		   root = node;
	   }
	   
	   if(k<currentNode.getKey()) {
		   if(!isLeaf(currentNode)) {
			   ok=true;
		   }
		   node.setParent(currentNode);
		   currentNode.setLeft(node);
		   if(ok) {
			   
			   updateSizeToRoot(currentNode,1);
			   node.setParent(currentNode);
			   return 0;
		   }
	   }
	   else {
		   //System.out.println(currentNode.getKey());
		   if(!isLeaf(currentNode)) {
			   ok=true;
		   }
		   currentNode.setRight(node);
		   node.setParent(currentNode);

		   if(ok) {
			   updateSizeToRoot(currentNode,1);

			   return 0;
		   }
	   }
	   updateSizeToRoot(currentNode,1);
	   int numofrebalance=0;
	   
	   if (currentNode.getParent() !=null) {
		   numofrebalance = rebalanceInsert(currentNode,0);
		   //node=node.getParent();
	   }
	   return numofrebalance;
  }
  
  public int rebalanceInsert(IAVLNode node,int count) {
	   if(node == null || balanced(node)) {
		   //System.out.println("kk");
		   return count;
		   
	   }
	   if(node.getHeight()==node.getLeft().getHeight()&&node.getHeight()-1==node.getRight().getHeight()) {
		   premote(node);
		   count++;
		   rebalanceInsert(node.getParent(),count);
	   }
	   if(node.getHeight()==node.getLeft().getHeight()&&node.getHeight()-2==node.getRight().getHeight()) {
		   if(node.getLeft().getHeight()-1==node.getLeft().getLeft().getHeight()) {
			   rightRotate(node);
			   demote(node);
			   count=+2;
			   return count;
		   }
		   if(node.getLeft().getHeight()-1==node.getLeft().getRight().getHeight()) {
			   leftThenRightRotate(node);
			   //System.out.println(node.getKey()+"9");
			   demote(node);
			   premote(node.getParent());
			   demote(node.getParent().getLeft());
			   //System.out.println(node.getHeight());
			   count=+5;
			   return count;
		   }
	   }
	   if(node.getHeight()==node.getRight().getHeight()&&node.getHeight()-1==node.getLeft().getHeight()) {
		   premote(node);
		   System.out.println(node.getKey()+" "+node.getHeight()+" "+node.getParent().getHeight());
		   count++;
		   rebalanceInsert(node.getParent(),count);
	   }
	   if(node.getHeight()==node.getRight().getHeight()&&node.getHeight()-2==node.getLeft().getHeight()) {
		   if(node.getRight().getHeight()-1==node.getRight().getRight().getHeight()) {
			   rightRotate(node);
			   demote(node);
			   count=+2;
			   return count;
		   }
		   if(node.getRight().getHeight()-1==node.getRight().getLeft().getHeight()) {
			   rightThenLeftRotate(node);
			   demote(node);
			   premote(node.getParent());
			   demote(node.getParent().getRight());
			   count=+5;
			   return count;
		   }
	   }
	   return -1;
	   
	   
  }
  
  public boolean balanced(IAVLNode node) {
		//////System.out.println(node.getKey()+" "+node.getHeight()+" "+node.getLeft().getHeight()+" "+node.getRight().getHeight());
		//System.out.println("j");
	   if(node.getHeight()>node.getLeft().getHeight()&&node.getHeight()>node.getRight().getHeight()
			   &&2*node.getHeight()-node.getLeft().getHeight()-node.getRight().getHeight()<4) {
		   return true;
	   }
	   return false;
  }
   
   private IAVLNode getBrother(IAVLNode node) {/////////////
	   if (node.getParent() == null) return null;
	   IAVLNode parent = node.getParent();
	   if (parent.getKey()>node.getKey()) {
		   return parent.getRight();
	   }
	   return parent.getLeft();
	   
   }
   
   private void swichNodes (IAVLNode node1, IAVLNode node2) {////////////////
	   if(node1.getHeight() < node2.getHeight()) {
		   IAVLNode extraNode = node1;
		   node1 = node2;
		   node2 = extraNode;
	   }
	   IAVLNode parent1 = node1.getParent();
	   IAVLNode left1 = node1.getLeft();
	   IAVLNode right1 = node1.getRight();
	   
	   IAVLNode parent2 = node2.getParent();
	   IAVLNode left2 = node2.getLeft();
	   IAVLNode right2 = node2.getRight();
	   
	   if(node1.getKey() == root.getKey()) {
		   root = node2;		   
	   }
	   else {
		   if(parent1.getKey() > node1.getKey()) {
			   parent1.setLeft(node2);
		   }
		   else {
			   parent1.setRight(node2);
		   }
	   }
	   if(parent2.getKey() > node2.getKey()) {
		   parent2.setLeft(node1);
	   }
	   else {
		   parent2.setRight(node1);
	   }
	   
	   node1.setLeft(left2);
	   node1.setRight(right2);
	   node1.setParent(parent2);
	   
	   right2.setParent(node1);
	   left2.setParent(node1);
	   
	   node2.setLeft(left1);
	   node2.setRight(right1);
	   node2.setParent(parent1);
	   
	   right1.setParent(node2);
	   left1.setParent(node2);
	   
	   node2.setSize(right1.getSize() + left1.getSize() + 1);
	   node1.setSize(right2.getSize() + left2.getSize() + 1);
	   
	   node2.setHeight(Math.max(right1.getHeight(), left1.getHeight()) + 1);
	   node1.setHeight(Math.max(right2.getHeight(), left2.getHeight()) + 1);
		   	    
   }

  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	   
	   if(search(k) == null) return -1;
	   IAVLNode nodeToDelete = currentNode;
	   IAVLNode parent = nodeToDelete.getParent();  
	   
	   
	   if(!isLeaf(nodeToDelete) && !isUnarNode(nodeToDelete)) { //is binary node
		   IAVLNode nodeSuccessor = successor(nodeToDelete);
		   swichNodes(nodeToDelete, nodeSuccessor);		   
	   }	   
	   if(nodeToDelete.getKey() == root.getKey()) { //root and not binary
		   if(isLeaf(nodeToDelete)) { //root and leaf
			   root = new AVLNode(-1,null);
			   return 0;
		   }
		   if(nodeToDelete.getRight().isRealNode()) { //root and unary from right
			   root = nodeToDelete.getRight();
			   root.setParent(null);
			   return 0;
		   }
		   root = nodeToDelete.getLeft(); // root is unary from left
		   root.setParent(null);
		   return 0;
	   }	   	      
	   if(isLeaf(nodeToDelete)) {		  
		   if(parent.getKey() > nodeToDelete.getKey()) {
			   parent.setLeft(nodeToDelete.getLeft());
		   }
		   else {
			   parent.setRight(nodeToDelete.getRight());
		   } 
	   }
	   else if(isUnarNode(nodeToDelete)) { 
		   IAVLNode sonNode;
		   if(nodeToDelete.getLeft().isRealNode()) {
			   sonNode = nodeToDelete.getLeft();
		   }
		   else {
			   sonNode = nodeToDelete.getRight();
		   }
		   if(parent.getKey() > nodeToDelete.getKey()) {
			   parent.setLeft(sonNode);
		   }
		   else {
			   parent.setRight(sonNode);
		   }
		   sonNode.setParent(parent);
	   }
	   
	   updateSizeToRoot(parent, -1);
	   
	   return delRrebalance(parent); //not written yet
   }
   
   public void premote(IAVLNode node) {
	   if(node.isRealNode()) {
		   node.setHeight(node.getHeight()+1);

	   }
   }
  
   public void demote(IAVLNode node) {
	   if(node.isRealNode()) {
		   node.setHeight(node.getHeight()-1);
	   }
   }

   public boolean isBalanced(IAVLNode node) {
	   if(node.getHeight()>node.getLeft().getHeight()&&node.getHeight()>node.getRight().getHeight()
			   &&2*node.getHeight()-node.getLeft().getHeight()-node.getRight().getHeight()<4) {
		   return true;
	   }
	   else return false;
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   if (empty()) {
		   return null;
	   }
	   IAVLNode node=this.root;
	   minNode(node);
	   return node.getValue();
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   if (empty()) {
		   return null;
	   }
	   IAVLNode node=this.root;
	   maxNode(node);
	   return node.getValue(); // to be replaced by student code
   }
    
   private IAVLNode[] nodeToArray() { 	   	//////////  
		  
	   	  IAVLNode[] nodeArr = new IAVLNode[root.getSize()];
		  if (empty()) return nodeArr;
		  
		  IAVLNode treeMin = minNode(root);
		  
		  currentNode = treeMin;
		  for(int i= 0; i < root.getSize() - 1; i++) {
			  nodeArr[i] = currentNode;
			  currentNode = successor(currentNode);
		  }
		  nodeArr[root.getSize() - 1] = currentNode;
		  
		  return nodeArr;
	  }
   
   private IAVLNode maxNode (IAVLNode node) {/////////////
   		while (node.getRight().isRealNode()) {
			  node = node.getRight();
		  }
		  return node;
   	  }
	  
   private IAVLNode minNode (IAVLNode node) { // returns the node with the minimum key of the sub-tree which (node) is its root
		  while (node.getLeft().isRealNode()) {
			  node = node.getLeft();
		  }
		  return node;
	  }
 
   private IAVLNode successor (IAVLNode node) { // returns the successor node of (node)
		  if (node.getRight().isRealNode()) {
			  return (minNode(node.getRight()));
		  }
		  IAVLNode parentNode = node.getParent();
		  while(parentNode.isRealNode() & node.getKey() == parentNode.getRight().getKey()) {
			  node = parentNode;
			  parentNode = node.getParent();
		  }
		  return parentNode;
	  }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
   public int[] keysToArray()
   {
       	int[] arr = new int[this.root.getSize()]; // to be replaced by student code
 		IAVLNode temp=root;
 		IAVLNode temp1=root;
 		maxNode(temp1);
 		minNode(temp);
 		int i=0;
 		while(temp.getKey()!=temp1.getKey()) {
 			arr[i]=temp.getKey();
 			temp=successor(temp);
 			i++;
 		}
 		
 		arr[i]=temp1.getKey();
         return arr;              
   } 
  
  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
	  	IAVLNode[] nodeArr = nodeToArray();
        String[] arr = new String[root.getSize()];
        
        for(int i = 0; i < root.getSize(); i++) {
        	arr[i] = nodeArr[i].getValue();
        }
           
        return arr;           
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    */
   public int size()
   {
	   return root.getSize(); 
   }
   
     /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    *
    * precondition: none
    * postcondition: none
    */
   public IAVLNode getRoot()
   {
	   return root;
   }
   
   

   /**
    * public string split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	  * precondition: search(x) != null
    * postcondition: none
    */   
   public AVLTree[] split(int x)////not written
   {
	   search(x);
	   IAVLNode xNode = currentNode;
	   AVLTree bigTree = new AVLTree(xNode.getRight());
	   AVLTree smallTree = new AVLTree(xNode.getLeft());
	   
	   while (xNode.getParent().isRealNode()) {
		   IAVLNode parent = xNode.getParent();
		   IAVLNode tempParent = new AVLNode(parent.getKey(),parent.getValue());
		   if (parent.getKey() < xNode.getKey()) {
			   AVLTree tempTree = new AVLTree(parent.getLeft());
			   smallTree.join(tempParent, tempTree);
		   }
		   else {
			   AVLTree tempTree = new AVLTree(parent.getRight());
			   bigTree.join(tempParent, tempTree);
		   }
		   xNode = xNode.getParent();
	   }
	   AVLTree[] splitTrees = {smallTree, bigTree};
	   return splitTrees;
   }
  
   /**
    * public join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (rank difference between the tree and t)
	  * precondition: keys(x,t) < keys() or keys(x,t) > keys()
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   IAVLNode join= root;
	   IAVLNode joint= t.getRoot();
	   IAVLNode temp;
	   if(join.getHeight()>joint.getHeight()) {
		   if(join.getKey()>joint.getKey()) {
			   while(join.getHeight()>joint.getHeight()) {
				   join=join.getRight();
			   }   
			   temp=join.getParent();
			   x.setLeft(joint);
			   x.setHeight(joint.getHeight()+1);
			   joint.setParent(x);
			   x.setRight(join);
			   join.setParent(x);
			   temp.setLeft(x);
			   x.setParent(temp);
		   }
		   else {
			   while(join.getHeight()>joint.getHeight()) {
				   join=join.getLeft();
			   } 
			   temp=join.getParent();
			   x.setRight(joint);
			   x.setHeight(joint.getHeight()+1);
			   joint.setParent(x);
			   x.setLeft(join);
			   join.setParent(x);
			   temp.setRight(x);
			   x.setParent(temp);
		   }
		   if(x.getHeight()==x.getParent().getHeight()) {
			   int f=rebalanceInsert(x,0);
		   }
	   }
	   else {
		   if(join.getKey()>joint.getKey()) {
			   while(joint.getHeight()>join.getHeight()) {
				   joint=joint.getRight();
			   }   
			   temp=joint.getParent();
			   x.setLeft(joint);
			   x.setHeight(join.getHeight()+1);
			   joint.setParent(x);
			   x.setRight(join);
			   join.setParent(x);
			   temp.setRight(x);
			   x.setParent(temp);
		   }
		   else {
			   while(joint.getHeight()>join.getHeight()) {
				   joint=joint.getLeft();
			   } 
			   temp=joint.getParent();
			   x.setRight(joint);
			   x.setHeight(join.getHeight()+1);
			   joint.setParent(x);
			   x.setLeft(join);
			   join.setParent(x);
			   temp.setLeft(x);
			   x.setParent(temp);
		   }
		   if(x.getHeight()==x.getParent().getHeight()) {
			   int f=rebalanceInsert(x,0);
		   }
	   }
	   return t.getRoot().getHeight()-root.getHeight()+1;

	    
   }

	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode{	
		public int getKey(); //returns node's key (for virtuval node return -1)
		public String getValue(); //returns node's value [info] (for virtuval node return null)
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node
    	public void setHeight(int height); // sets the height of the node
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes)
    	public int getSize();
    	public void modifySize(int num);
    	public void setSize(int newSize);
	}
	
	
	public static void drawTree(IAVLNode root, int space)  
	{  
	    // Base case  
	    if (root == null)  
	        return;  
	  
	    // Increase distance between levels  
	    space += 10;  
	  
	    // Process right child first  
	    drawTree(root.getRight(), space);  
	  
	    // Print current node after space  
	    // count  
	    System.out.print("\n");  
	    for (int i = 10; i < space; i++)  
	        System.out.print(" ");  
	    System.out.print("("+root.getKey() + ", h:" +root.getHeight()+ ", s:" + root.getSize() + ")\n");  
	  
	    // Process left child  
	    drawTree(root.getLeft(), space);  
	}

   /**
   * public class AVLNode
   *
   * If you wish to implement classes other than AVLTree
   * (for example AVLNode), do it in this file, not in 
   * another file.
   * This class can and must be modified.
   * (It must implement IAVLNode)
   */
  public class AVLNode implements IAVLNode{
	  	private int key = -1;
	  	private String val;
	  	private IAVLNode parent;
	  	private IAVLNode left;
	  	private IAVLNode right;
	  	private int height = -1;
	  	private int size = 0;
	  	
	  	public AVLNode(int key, String val) {
			this.key =key;
			this.val =val;
			this.height = 0;
			this.size = 1;
			this.setLeft(new AVLNode());
			this.setRight(new AVLNode());
		}
	  	
		public AVLNode() {			
		}
	  	
	  	public int getSize() {
	  		return size;
	  	}
	  	
	  	public void modifySize(int add) {
	  		size += add;
	  	}
	  	
	  	public void setSize(int newSize) {
			size = newSize;
		}
	  		  
		public int getKey()
		{
			return key; // to be replaced by student code
		}
		public String getValue()
		{
			return val; // to be replaced by student code
		}
		public void setLeft(IAVLNode node)
		{
			this.left = node;
		}
		public IAVLNode getLeft()
		{
			return left;
		}
		public void setRight(IAVLNode node)
		{
			this.right = node;
		}
		public IAVLNode getRight()
		{
			return right;
		}
		public void setParent(IAVLNode node)
		{
			this.parent = node;
		}
		public IAVLNode getParent()
		{
			return parent;
		}
		// Returns True if this is a non-virtual AVL node
		public boolean isRealNode()
		{
			if(key == -1) return false;
			return true;
		}
		public void setHeight(int height)
		{
			this.height = height;
		}
		public int getHeight()
		{    	
			return height; // to be replaced by student code
		}
  }

}
  

