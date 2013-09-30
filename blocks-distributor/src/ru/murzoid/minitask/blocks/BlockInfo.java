package ru.murzoid.minitask.blocks;

public class BlockInfo {
	private int blockSize;
	private int blockCount;

	public BlockInfo(int blockSize, int blockCount) {
		super();
		this.blockSize = blockSize;
		this.blockCount = blockCount;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}

	@Override
	public String toString() {
		return "BlockInfo [blockSize=" + blockSize + ", blockCount="
				+ blockCount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blockSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlockInfo other = (BlockInfo) obj;
		if (blockSize != other.blockSize)
			return false;
		return true;
	}
}
