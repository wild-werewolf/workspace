package ru.murzoid.minitask.blocks;

import java.util.TreeSet;

public class BlockDistributor {

	public TreeSet<BlockInfo> distributeBlockToLine(TreeSet<BlockInfo> blocks,
			int lineSize) {
		TreeSet<BlockInfo> result = null;
		BlockInfo[] blocksArray = new BlockInfo[blocks.size()];
		blocks.toArray(blocksArray);
		result = notSimpleCheck(blocksArray, lineSize, blocksArray.length - 1);
		return result;
	}

	private TreeSet<BlockInfo> notSimpleCheck(BlockInfo[] blocks,
			int tempLineSize, int iter) {
		if (tempLineSize == 0) {
			return new TreeSet<BlockInfo>(new BlockInfoComparator());
		} else if (iter < 0) {
			return null;
		} else {
			BlockInfo block = blocks[iter];
			int blockNeedCount = tempLineSize / block.getBlockSize();
			int blockMinCount = Math.min(blockNeedCount, block.getBlockCount());
			for (int i = blockMinCount; i >= 0; i--) {
				int remainder = tempLineSize - i * block.getBlockSize();
				TreeSet<BlockInfo> result = notSimpleCheck(blocks, remainder,
						iter - 1);
				if (result != null) {
					result.add(new BlockInfo(block.getBlockSize(), i));
					return result;
				}
			}
		}
		return null;
	}
}
