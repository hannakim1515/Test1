package com.bookdabang.lhs.persistence;

import java.util.List;

import com.bookdabang.common.domain.ProductVO;
import com.bookdabang.common.domain.VisitorIPCheck;
import com.bookdabang.lhs.domain.VisitorCountWithDateFormat;

public interface ChartDAO {

	public List<ProductVO> getProductSort() throws Exception;

	public List<ProductVO> getRandomSelect() throws Exception;

	public List<VisitorCountWithDateFormat> getVisitorInfo() throws Exception;

	public int autoInsertVisitor(VisitorIPCheck vipc) throws Exception;

	public VisitorIPCheck getTodayVisitor() throws Exception;

}
