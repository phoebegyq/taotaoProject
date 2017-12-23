package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;

public interface ContentCategoryListService {
	
	EUDataGridResult getContentCategoryList(long categoryId,int page, int rows);

}
