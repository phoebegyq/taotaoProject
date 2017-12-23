package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;

public interface ContentCategoryService {
	
	List<EUTreeNode> getCategoryList(long parentId);
	
	TaotaoResult insertContentCategory(long parentId,String name);
	
	TaotaoResult deleteContentCategory(long id);
	//删除内容分类，注意参数名称要与content-category.jsp页面指定的参数名称一致
	TaotaoResult updateContentCategory(long id,String name);

}
