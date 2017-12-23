package com.taotao.common.pojo;

/**
 * easyUI树形控件节点
 * 
 * @author gyq
 *
 */
public class EUTreeNode {
	// 当前节点的id，根据此id查询子节点
	private long id;
	// 节点显示的名称
	private String text;
	// 节点的状态，如果是closed就是一个文件夹形式，
	// 当打开时还会 做一次请求。如果是open就显示为叶子节点。
	private String state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
