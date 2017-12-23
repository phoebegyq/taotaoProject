package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.service.ItemParamListService;

@Controller
public class ItemParamListController {
	@Autowired
	ItemParamListService itemParamListService;
	
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EUDataGridResult getItemParamList(Integer page, Integer rows) {
		EUDataGridResult result = itemParamListService.getItemParamList(page, rows);
		return result;
	}
	
}
