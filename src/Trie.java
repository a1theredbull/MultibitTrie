import java.util.*;

public class Trie
{
	TrieNode head;
	int stride;
	
	public Trie(TrieNode head, int stride)
	{
		this.head = head;
		this.stride = stride;
	}
	
	public void insert(CustomBitSet fullKey, String value, TrieNode curr, int offset)
	{
		CustomBitSet key = new CustomBitSet(stride);
		
		for(int i = fullKey.length() - 1; i >= 0; i--)
		{
			if(fullKey.get(i))
				System.out.print("1");
			else
				System.out.print("0");
		}
		System.out.println("");
		
		int fullKeyLength = fullKey.capacity;
		boolean reachedEnd = false;
		int toGenerate = 0;
		for(int i = stride - 1; i >= 0; i--)
		{
			if(fullKeyLength - offset - 1 < 0)
			{
				reachedEnd = true;
				toGenerate++;
				continue;
			}
			
			key.set(i, fullKey.get(fullKeyLength - offset - 1));
			offset++;
		}
		if(reachedEnd)
			generateEnd(key, value, toGenerate);
		
		for(int i = key.capacity - 1; i >= 0; i--)
		{
			if(key.get(i))
				System.out.print("1");
			else
				System.out.print("0");
		}
		System.out.println("");
		
		boolean found = false;
		for(TrieNode node: curr.children)
		{
			if(key == node.key) //compare partial key val to val in here
			{
				curr = node;
				found = true;
			}
		}
		
		if(!found && offset < fullKeyLength)
		{
			TrieNode child = new TrieNode(key, null);
			curr.children.add(child);
			insert(fullKey, value, child, offset);
		}
		else if(!found && offset == fullKeyLength)
		{
			TrieNode child = new TrieNode(key, value);
			curr.children.add(child);
		}
	}
	
	private void generateEnd(CustomBitSet key, String value, int toGenerate)
	{
		//TODO FIGURE OUT BEST BITSET BINARY COUNTING GENERATION ALGORITHM
	}
	
	public String findValue(CustomBitSet key)
	{
		return "TrieNode.value";
	}
}

class TrieNode
{
	CustomBitSet key;
	String value;
	ArrayList<TrieNode> children;
	
	public TrieNode(CustomBitSet key, String value)
	{
		this.key = key;
		this.value = value;
		children = new ArrayList<TrieNode>();
	}
}
