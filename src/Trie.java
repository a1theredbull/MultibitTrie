import java.util.*;

public class Trie
{
	TrieNode head;
	
	public Trie(TrieNode head)
	{
		this.head = head;
	}
	
	public void insert(TrieNode curr) //PASS KEY IN HERE
	{
		for(TrieNode node: curr.children)
		{
			if(true) //compare partial key val to val in here
				curr = node;
		}
		
		insert(curr);
	}
	
	public void findValue() //PASS KEY IN HERE
	{
		//pretty much same as insert with a little less logic
	}
}

class TrieNode
{
	//KEY HERE, not sure best way to store bits yet
	String value;
	ArrayList<TrieNode> children;
	
	public TrieNode(String value)
	{
		ArrayList<TrieNode> children = new ArrayList<TrieNode>();
		this.value = value;
	}
}
