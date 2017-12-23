package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;

public interface ItemCatService {
	List<EUTreeNode> getItemCatList(long parentId);

}
