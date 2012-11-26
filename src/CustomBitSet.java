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
}
