import java.util.*;

public class Trie
{
	public TrieNode head;
	public int stride;
	
	public Trie(TrieNode head, int stride)
	{
		this.head = head;
		this.stride = stride;
	}
	
	public void insert(CustomBitSet fullKey, String value, TrieNode curr, int offset)
	{
		CustomBitSet key = new CustomBitSet(stride);
		
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
		{
			toGenerate = (int)Math.pow(2, toGenerate);
			for(int i = toGenerate-1; i >= 0; i--)
			{
				curr.children.add(new TrieNode((CustomBitSet)key.clone(), value));
				key.increment();
			}
			
			return;
		}

		for(TrieNode node: curr.children)
		{
			if(key.equals(node.key)) //compare partial key val to val in here
			{
				curr = node;
				insert(fullKey, value, node, offset);
				return;
			}
		}
		
		//no children match keys in current level
		if(offset < fullKeyLength)
		{
			TrieNode child = new TrieNode(key, null);
			curr.children.add(child);
			insert(fullKey, value, child, offset);
		}
		else if(offset == fullKeyLength || reachedEnd)
		{
			TrieNode child = new TrieNode(key, value);
			curr.children.add(child);
		}
	}
	
	public String findValue(CustomBitSet fullKey, TrieNode curr, int offset, String lastVal)
	{
		CustomBitSet key = new CustomBitSet(stride);
		
		if(curr.value != null)
			lastVal = curr.value;
		
		int fullKeyLength = fullKey.capacity;
		for(int i = stride - 1; i >= 0; i--)
		{
			key.set(i, fullKey.get(fullKeyLength - offset - 1));
			offset++;
		}
		
		for(TrieNode node: curr.children)
			if(key.equals(node.key))
				return findValue(fullKey, node, offset, lastVal);
		
		return lastVal;
	}
	
	/*
	 * Finds number of nodes by recursively incrementing per child.
	 * Really only used for debugging purposes.
	 */
	public int findNumNodes(TrieNode curr, int count)
	{
		for(TrieNode child : curr.children)
			count = findNumNodes(child, count) + 1;

		return count;
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
