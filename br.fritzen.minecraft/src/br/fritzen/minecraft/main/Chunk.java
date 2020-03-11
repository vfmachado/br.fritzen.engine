package br.fritzen.minecraft.main;

public interface Chunk {

	/**
	 * 
	 * @param x position
	 * @param y position. Y in this program is used for height
	 * @param z position
	 */
	public void add(int x, int y, int z, BlockType type);
	
	/**
	 * The array is three times bigger than the quantity of blocks.
	 * The format must be 
	 * 
	 * 	[x1, y1, z1, x2, y2, z2, ...]
	 * 
	 * @return An array of translations for all cubes. Each cube has 3 coordinates x, y, z.
	 */
	public float[] getTransforms();
	
	/**
	 * 
	 * return the number of blocks to be rendered in the current chunk
	 */
	public int getQuantity();
}
