package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentCategoryListService;

@Service
public class ContentCategoryListServiceImpl implements ContentCategoryListService {
	@Autowired
	TbContentMapper contentMapper;

	@Override
	public EUDataGridResult getContentCategoryList(long categoryId, int page, int rows) {
		// 设置分页
		PageHelper.startPage(page, rows);
		// 创建查询条件
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		// 根据条件查询
		List<TbContent> list = contentMapper.selectByExample(example);
		// 创建一个返回对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		// 取记录总条数
		result.setTotal(pageInfo.getTotal());
		//返回结果
		return result;
		

	}

}
