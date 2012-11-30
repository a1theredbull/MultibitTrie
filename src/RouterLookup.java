import java.io.*;

public class RouterLookup
{
	public static void main(String[] args)
	{
		BufferedReader fileBr = null;
		int stride = 0;
		try
		{
			System.out.println("- Multi-bit Trie Creation and Lookup -");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter key/value file: ");
			fileBr = new BufferedReader(new FileReader(br.readLine()));
			System.out.print("Enter stride length(1-4): ");
			do
			{
				try
				{
					stride = Integer.parseInt(br.readLine());
				}
				catch(Exception e)
				{
					System.err.println("Invalid stride.\n");
				}
			}
			while(stride <= 0 && stride > 5);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		RouterLookup mt = new RouterLookup(fileBr, stride);
	}
	
	public RouterLookup(BufferedReader fromFile, int stride)
	{
		Trie trie = createTrie(fromFile, stride);
		
		//IP Lookup
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			System.out.print("Enter IP to lookup: ");
			BufferedReader fileBr = new BufferedReader(new FileReader(br.readLine()));
			String line;
			while((line = fileBr.readLine()) != null)
			{
				CustomBitSet key = convertIPtoBitSet(line, 32);
				String val = trie.findValue(key, trie.head, 0, null);
				
				System.out.println(line + "\t" + val);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private Trie createTrie(BufferedReader fromFile, int stride)
	{
		Trie trie = null;
		
		try
		{
			String line = null;
			System.out.print("Creating Trie...\n");
			TrieNode head = new TrieNode(null, null);
			trie = new Trie(head, stride);
			while((line = fromFile.readLine()) != null)
			{
				String[] ips = line.split(" "); //full IPKey/next hop
				String[] ip = ips[0].split("/");//IPKey with subnet mask
				int subnetMask = Integer.parseInt(ip[1]);
				
				if(subnetMask == 0)
					trie.head.value = ips[1];
				else
				{
					CustomBitSet key = convertIPtoBitSet(ip[0], subnetMask);
				
					trie.insert(key, ips[1], head, 0);
				}
			}
			System.out.print("Finished creating Trie.\n");
			System.out.println("Nodes created(including head): " + trie.findNumNodes(trie.head, 1) + "\n");
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return trie;
	}
	
	private CustomBitSet convertIPtoBitSet(String ip, int mask)
	{
		String[] octet = ip.split("\\.");
		CustomBitSet fullSet = new CustomBitSet(mask);
		int count = mask-1;

		for(int i = 0; i < octet.length; i++)
		{
			boolean maskFinished = false;
			try
			{
				CustomBitSet piece = convertIntToBitSet(Integer.parseInt(octet[i]));
				
				for(int j = piece.capacity-1; j >= 0; j--)
				{
					fullSet.set(count, piece.get(j));
					
					if(count == 0)
					{
						maskFinished = true;
						break;
					}
					count--;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			if(maskFinished) break;
		}
	
		return fullSet;
	}
	
	private CustomBitSet convertIntToBitSet(int value)
	{
		CustomBitSet bits = new CustomBitSet(8);
		int index = 0;
		while (value != 0)
		{
			if (value % 2 != 0)
				bits.set(index);
			index++;
			value = value >>> 1;
		}
		
		return bits;
	}
}
