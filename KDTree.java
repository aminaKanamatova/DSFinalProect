import java.util.*;

public class KDTree{
	Node root; 

	public KDTree(ArrayList<Word> words){
		root = buildTree(words, 0); //build tree starting at 0 
	}

    //method to build kd tree 
	public Node buildTree(ArrayList<Word> words, int depth) {
        if (words.isEmpty()) {
            return null;
        }

        // Determine the axis based on depth
        int axis = depth % words.get(0).vector.length;

        // Sort by the current axis
        words.sort((w1, w2) -> Float.compare(w1.vector[axis], w2.vector[axis]));

        // Find the median and divide
        int medianIndex = words.size() / 2;
        Word medianWord = words.get(medianIndex);
        Node node = new Node(medianWord);

        node.left = buildTree(new ArrayList<>(words.subList(0, medianIndex)), depth + 1);
        node.right = buildTree(new ArrayList<>(words.subList(medianIndex + 1, words.size())), depth + 1);

        return node;
    }

    public Word findNearestNeighbor(Word tar){
    	return findNearestNeighbor(root, tar, 0);
    }

private Word findNearestNeighbor(Node node, Word tar, int depth){
	if(node == null || tar.vector.length == 0){
		return null;
	}
	int axis = depth % tar.vector.length; 

	Node next = null;
	Node other = null;

	//going left or roght based on the vector of the target
	if(tar.vector[axis] < node.word.vector[axis]){
		next = node.left; 
		other = node.right; 
	} else {
		next = node.right; 
		other = node.left; 
	}

	//recurse down the tree
	Word curr = findNearestNeighbor(next, tar, depth + 1); 

	//check if node is closer than the curr found so far 
	if(curr == null || dist(tar.vector, node.word.vector) < dist(tar.vector, curr.vector)){
		curr = node.word;
	}
	//check if need to go through the other subtree
	if(other != null && Math.abs(tar.vector[axis] - node.word.vector[axis]) < dist(tar.vector, curr.vector)){
		Word othercurr = findNearestNeighbor(other, tar, depth + 1);
		if(othercurr != null && dist(tar.vector, othercurr.vector) < dist(tar.vector, curr.vector)){
			curr = othercurr;
		}
	}
	return curr;

}

public double dist(float[] vec1, float[] vec2){
	double sum = 0; 
	for(int i = 0; i < vec1.length; i++){
		sum += Math.pow(vec1[i] - vec2[i], 2);
	}
	return Math.sqrt(sum);
}


public void traversal(Node node){
	if(node != null){
		traversal(node.left);
		System.out.println(node.word.word);
		traversal(node.right);
	}
}

static class Node{
	Word word; 
	Node left; //left child node 
	Node right; //right child node 

	public Node(Word word){
		this.word = word; 
		this.left = null; 
		this.right = null;
	}
}
}