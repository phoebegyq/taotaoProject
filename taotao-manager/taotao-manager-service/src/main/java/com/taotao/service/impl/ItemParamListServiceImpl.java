package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamListService;

@Service
public class ItemParamListServiceImpl implements ItemParamListService {
	@Autowired
	TbItemParamMapper itemParamMapper;

	@Override
	public EUDataGridResult getItemParamList(int page, int rows) {
		// 查询商品列表
		TbItemParamExample example = new TbItemParamExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		// 创建一个返回对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		// 取记录总条数
		result.setTotal(pageInfo.getTotal());
		return result;
	}
}
