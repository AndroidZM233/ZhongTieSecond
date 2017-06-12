package com.zhy.bean;

import com.zhy.tree.bean.TreeCKNode;
import com.zhy.tree.bean.TreeNodeId;
import com.zhy.tree.bean.TreeNodeLabel;
import com.zhy.tree.bean.TreeNodePid;

public class Bean {
	@TreeNodeId
	private int id;
	@TreeNodePid
	private int pId;
	@TreeNodeLabel
	private String label;

	@TreeCKNode
	private String ckNode;

	public Bean() {
	}

	public Bean(int id, int pId, String label) {
		this.id = id;
		this.pId = pId;
		this.label = label;
	}

	public Bean(int id, int pId, String label, String ckNode) {
		this.id = id;
		this.pId = pId;
		this.label = label;
		this.ckNode = ckNode;
	}

	public String getCkNode() {
		return ckNode;
	}

	public void setCkNode(String ckNode) {
		this.ckNode = ckNode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
