package test.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybatis.MybatisBus;

import org.apache.ibatis.session.SqlSession;
import org.smileboy.framework.controller.Controller;
import org.smileboy.framework.controller.RequestTrack;

import dao.AccountManageDao;
import entity.Account;


@Controller
@RequestTrack(value="test1/OKOK/")
public class Test1 {
	@RequestTrack(value="te1")
	public void te1(HttpServletRequest request,HttpServletResponse response) throws Exception{
		SqlSession session = MybatisBus.getSession();
		AccountManageDao dao = session.getMapper(AccountManageDao.class);
		List<Account> list = dao.accountList();
		session.close();
		response.getWriter().print("nndd");
		
	}

	@RequestTrack(value="te2")
	public void te2(HttpServletRequest request,HttpServletResponse response){
		String value = request.getParameter("value");
		try {
			response.getWriter().print(value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
