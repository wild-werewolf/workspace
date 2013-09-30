package ru.murzoid.minitask.blocks;

import java.util.TreeSet;

public class BlocksStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlocksStarter blocksStarter = new BlocksStarter();
		Result result = new Result();
		blocksStarter.testBlockDistributor(result, true, 24, 7, 4, 6, 4, 2, 3);
		blocksStarter.testBlockDistributor(result, true, 23, 7, 4, 6, 4, 2, 3);
		blocksStarter.testBlockDistributor(result, true, 11, 5, 4, 1, 4);
		blocksStarter.testBlockDistributor(result, true, 11, 5, 1, 1, 8);
		blocksStarter.testBlockDistributor(result, false, 11, 5, 1, 1, 4);
		blocksStarter.testBlockDistributor(result, false, 11, 5, 4, 1, 0);
		blocksStarter.testBlockDistributor(result, false, 1, 7, 4, 6, 4, 2, 3);
		System.out.println("Success: " + result.getSuccessCount() + " Fail: "
				+ result.getFailCount());
	}

	public void testBlockDistributor(Result result, boolean expectedResult,
			int lineSize, int... blocks) {
		TreeSet<BlockInfo> sortedBlocks = new TreeSet<BlockInfo>(
				new BlockInfoComparator());
		for (int i = 0; i < blocks.length; i += 2) {
			BlockInfo block = new BlockInfo(blocks[i], blocks[i + 1]);
			sortedBlocks.add(block);
		}
		testBlockDistributor(result, expectedResult, lineSize, sortedBlocks);
	}

	public void testBlockDistributor(Result result, boolean expectedResult,
			int lineSize, TreeSet<BlockInfo> blocks) {
		System.out.println(blocks + " lineSize=" + lineSize
				+ " expectedResult=" + expectedResult);
		BlockDistributor distributor = new BlockDistributor();
		TreeSet<BlockInfo> realResult = distributor.distributeBlockToLine(
				blocks, lineSize);
		if (realResult != null && expectedResult) {
			System.out.println("Blocks in line " + realResult);
			System.out.println("success");
			result.incrementSuccess();
		} else if (realResult == null && !expectedResult) {
			System.out.println("success");
			result.incrementSuccess();
		} else {
			System.out.println("fail");
			result.incrementFail();
		}
	}

	private static class Result {
		int successCount = 0;
		int failCount = 0;

		private void incrementSuccess() {
			successCount++;
		}

		private void incrementFail() {
			failCount++;
		}

		private int getSuccessCount() {
			return successCount;
		}

		private int getFailCount() {
			return failCount;
		}
	}
}
