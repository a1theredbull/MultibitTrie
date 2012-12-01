import java.util.BitSet;

/* BitSet only contains logical length.
 * Added this to keep track of capacity(nbits)
 */
public class CustomBitSet extends BitSet
{
	private static final long serialVersionUID = 1L;
	public int capacity;
	
	public CustomBitSet()
	{
		super();
	}
	
	public CustomBitSet(int nbits)
	{
		super(nbits);
		capacity = nbits;
	}
	
	public void increment()
	{
		int firstZeroIndex = this.capacity-1;
		for(int i = 0; i < this.capacity; i++)
			if(!this.get(i))
			{
				firstZeroIndex = i;
				this.set(i, true);
				break;
			}
		
		for(int i = firstZeroIndex-1; i >= 0; i--)
			this.set(i, false);
	}
	
	public String toString()
	{
		String binary = "";
		
		for(int i = this.capacity-1; i >= 0; i--)
		{
			if(this.get(i))
				binary += "1";
			else
				binary += "0";
		}
		
		return binary;
	}
}
