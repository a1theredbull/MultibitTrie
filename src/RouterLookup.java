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
					//DO NOTHING
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
				long ipLong = convertIPtoLong(ip[0]);
				int subnetMask = Integer.parseInt(ip[1]);
				CustomBitSet key = convertLongtoBitSet(ipLong, subnetMask);
				
				trie.insert(key, ips[1], head, 0);
				
				if(++count > 1) break;
			}
			System.out.print("Finished creating Trie.");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private long convertIPtoLong(String ip)
	{
		long result = 0;
		String[] octet = ip.split("\\.");
		
		try
		{
			result += Integer.parseInt(octet[3]) + ( Integer.parseInt(octet[2]) * 256) + 
					(Integer.parseInt(octet[1]) * 65536) + (Integer.parseInt(octet[0]) * 16777216);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	private CustomBitSet convertLongtoBitSet(long value, int mask)
	{
		value = value >>> Long.toBinaryString(value).length() - mask; //find better way to shift!!
		
		CustomBitSet bits = new CustomBitSet(mask);
		int index = 0;
		while (value != 0L)
		{
			if (value % 2L != 0)
				bits.set(index);
			index++;
			value = value >>> 1;
		}
		
		return bits;
	}
}