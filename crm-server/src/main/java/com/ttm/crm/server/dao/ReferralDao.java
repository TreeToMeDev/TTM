package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.Referral;

@Component
public class ReferralDao {
	
	private final SqlSession sqlSession;
	
	public ReferralDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<Referral> find() {
		return this.sqlSession.selectList("selectReferrals");
	}
	
	public List<Referral> findAllOpen() {
		return this.sqlSession.selectList("selectOpenReferrals");
	}
	
	public Referral find(int id) {
		return this.sqlSession.selectOne("selectReferral", id);
	}
	
	public void add(Referral referral) {
		int result = this.sqlSession.insert("insertReferral", referral);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void update(Referral referral) {
		int result = this.sqlSession.update("updateReferral", referral);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void delete(int id) {
		int result = this.sqlSession.delete("deleteReferral", id);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
}
