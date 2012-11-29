import java.io.*;

public class RouterLookup
{
	Trie trie;
	
	public static void main(String[] args)
	{
		BufferedReader fileBr = null;
		int stride = 0;
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter IP file: ");
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
		}
		
		RouterLookup mt = new RouterLookup(fileBr, stride);
	}
	
	public RouterLookup(BufferedReader fromFile, int stride)
	{
		try
		{
			String line = null;
			System.out.print("Creating Trie...\n");
			TrieNode head = new TrieNode(null, null);
			trie = new Trie(head, stride);
			int count = 0;
			
			while((line = fromFile.readLine()) != null)
			{
				String[] ips = line.split(" "); //full IPKey/next hop
				String[] ip = ips[0].split("/");//IPKey with subnet mask
				int subnetMask = Integer.parseInt(ip[1]);
				CustomBitSet key = convertIPtoBitSet(ip[0], subnetMask);

				for(int j = key.capacity-1; j >= 0; j--)
				{
					if(key.get(j))
						System.out.print("1");
					else
						System.out.print("0");
				}
				System.out.println("");
				
				trie.insert(key, ips[1], head, 0);
				
				if(++count > 1) break;
			}
			System.out.print("Finished creating Trie.\n");
			System.out.println("Nodes created(including head): " + trie.findNumNodes(trie.head, 1));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private CustomBitSet convertIPtoBitSet(String ip, int mask)
	{
		String[] octet = ip.split("\\.");
		CustomBitSet fullSet = new CustomBitSet(32);
		int count = 31;

		for(int i = 0; i < octet.length; i++)
		{
			boolean maskFinished = false;
			try
			{
				CustomBitSet piece = convertIntToBitSet(Integer.parseInt(octet[i]));
				for(int j = piece.capacity-1; j >= 0; j--)
				{
					fullSet.set(count, piece.get(j));
					if(--count < 32-mask)
					{
						maskFinished = true;
						break;
					}
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
