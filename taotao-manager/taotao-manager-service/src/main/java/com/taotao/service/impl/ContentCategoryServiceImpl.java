package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

/**
 * 内容分类管理
 * 
 * @author gyq
 *
 */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		// 创建查询条件
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 根据条件查询
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		// 返回一个resultList
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}

		return resultList;
	}

	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {

		// 创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		// '状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		// 添加记录
		tbContentCategoryMapper.insert(contentCategory);
		// 查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCategory parentCat = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		// 判断是否为true
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			// 更新父节点
			tbContentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		// 返回结果
		return TaotaoResult.ok(contentCategory);
	}

	// 通过父节点id来查询所有子节点，这是抽离出来的公共方法
	private List<TbContentCategory> getContentCategoryListByParentId(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		return list;
	}

	// 递归删除节点
	private void deleteNode(long parentId) {
		List<TbContentCategory> list = getContentCategoryListByParentId(parentId);
		for (TbContentCategory contentCategory : list) {
			contentCategory.setStatus(2);
			tbContentCategoryMapper.updateByPrimaryKey(contentCategory);

			// TbContentCategoryExample example = new
			// TbContentCategoryExample();
			// Criteria criteria = example.createCriteria();
			// criteria.andParentIdEqualTo(parentId);
			// tbContentCategoryMapper.deleteByExample(example);
			if (contentCategory.getIsParent()) {
				deleteNode(contentCategory.getId());
			}
		}
	}

	@Override
	public TaotaoResult deleteContentCategory(long id) {

		// TbContentCategoryExample example = new TbContentCategoryExample();
		// Criteria criteria = example.createCriteria();
		// criteria.andIdEqualTo(id);
		// tbContentCategoryMapper.deleteByExample(example);
		// TbContentCategory contentCategory =
		// tbContentCategoryMapper.selectByPrimaryKey(id);
		//
		// return TaotaoResult.ok();
		// 删除分类，就是改节点的状态为2
		TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		// 状态。可选值:1(正常),2(删除)
		contentCategory.setStatus(2);
		tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
		// 我们还需要判断一下要删除的这个节点是否是父节点，如果是父节点，那么就级联
		// 删除这个父节点下的所有子节点（采用递归的方式删除）
		if (contentCategory.getIsParent()) {
			deleteNode(contentCategory.getId());
		}
		// 需要判断父节点当前还有没有子节点，如果有子节点就不用做修改
		// 如果父节点没有子节点了，那么要修改父节点的isParent属性为false即变为叶子节点
		TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
		List<TbContentCategory> list = getContentCategoryListByParentId(parent.getId());
		// 判断父节点是否有子节点是判断这个父节点下的所有子节点的状态，如果状态都是2就说明
		// 没有子节点了，否则就是有子节点。
		boolean flag = false;
		for (TbContentCategory tbContentCategory : list) {
			if (tbContentCategory.getStatus() == 0) {
				flag = true;
				break;
			}
		}
		// 如果没有子节点了
		if (!flag) {
			parent.setIsParent(false);
			tbContentCategoryMapper.updateByPrimaryKey(parent);
		}
		// 返回结果
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateContentCategory(long id, String name) {
		// 通过id查询节点对象
		TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		// 判断新的name值与原来的值是否相同，如果相同则不用更新
		if (name != null && name.equals(contentCategory.getName())) {
			return TaotaoResult.ok();
		}
		contentCategory.setName(name);
		// 设置更新时间
		contentCategory.setUpdated(new Date());
		// 更新数据库
		tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
		// 返回结果
		return TaotaoResult.ok();
	}

}
