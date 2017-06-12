package com.zhy.bean;

import com.zhy.tree.bean.TreeCKNode;
import com.zhy.tree.bean.TreeNodeId;
import com.zhy.tree.bean.TreeNodeLabel;
import com.zhy.tree.bean.TreeNodePid;

public class FileBean
{
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;
	@TreeCKNode
	private String ckNode;

	private long length;
	private String desc;


	public FileBean(int id, int pId, String label, String ckNode) {
		super();
		this._id = id;
		this.parentId = pId;
		this.name = label;
		this.ckNode = ckNode;
	}
	public FileBean(int _id, int parentId, String name)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
	}
	


	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCkNode() {
		return ckNode;
	}

	public void setCkNode(String ckNode) {
		this.ckNode = ckNode;
	}
	

}
