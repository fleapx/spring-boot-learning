package com.yangbingdong.blockchain.simple;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @author hezhuofan
 */
public class Demo {
	public static ArrayList<Block> blockchain = new ArrayList<Block>();

	public static void main(String[] args) {
		//add our blocks to the blockchain ArrayList:
		blockchain.add(new Block("Hi im the first block", "0"));
		blockchain.add(new Block("Yo im the second block", blockchain.get(blockchain.size() - 1).hash));
		blockchain.add(new Block("Hey im the third block", blockchain.get(blockchain.size() - 1).hash));

		String blockchainJson = JSON.toJSONString(blockchain, true);
		System.out.println(blockchainJson);
	}
}
