package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentCategoryListService;

@Controller
public class ContentCategoryListController {
	@Autowired
	ContentCategoryListService contentCategoryListService;

	@RequestMapping("/content/query/list")
	@ResponseBody
	public EUDataGridResult ContentCategoryList(long categoryId, Integer page, Integer rows) {
		EUDataGridResult result = contentCategoryListService.getContentCategoryList(categoryId, page, rows);
		return result;
	}

}
