package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.DealItem;
import com.ttm.crm.server.service.DealService;

@Component
public class DealItemDao {

	//private final DealService dealService;
	private final SqlSession sqlSession;
	
	public DealItemDao(/*DealService dealService,*/ SqlSession sqlSession) {
		//this.dealService = dealService;
		this.sqlSession = sqlSession;
	}
	
	public List<DealItem> findByDealId(int dealId) {
		//dealService.checkAccess(dealId);
		return this.sqlSession.selectList("selectDealItemsByDealId", dealId);
	}
	
	public DealItem find(int id) {
		DealItem ret = this.sqlSession.selectOne("selectDealItem", id);
		//dealService.checkAccess(ret.getDealId());
		return ret;
	}
	
	public void add(DealItem dealItem) {
		//dealService.checkAccess(dealItem.getDealId());
		int result = this.sqlSession.insert("insertDealItem", dealItem);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void update(DealItem dealItem) {
		//dealService.checkAccess(dealItem.getDealId());
		int result = this.sqlSession.update("updateDealItem", dealItem);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void delete (int id) {
		DealItem dealItem = this.find(id);
		//dealService.checkAccess(dealItem.getDealId());
		int result = this.sqlSession.delete("deleteDealItem", id);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
}