package ru.murzoid.minitask.blocks;

import java.util.Comparator;

public class BlockInfoComparator implements Comparator<BlockInfo> {

	@Override
	public int compare(BlockInfo arg0, BlockInfo arg1) {
		return arg0.getBlockSize() - arg1.getBlockSize();
	}

}
