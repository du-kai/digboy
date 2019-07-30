package test.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smileboy.framework.controller.Controller;
import org.smileboy.framework.controller.RequestTrack;
import org.smileboy.framework.controller.viewobject.ViewModel;

import vo.TestVO;


@Controller
@RequestTrack(value="test2/oo/")
public class Test2 {
	@RequestTrack(value="ex")
	public ViewModel nnd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		TestVO testvo = new TestVO();
		testvo.setId("68478967897");
		testvo.setValue("val");
		testvo.setNum(6);
		
		ViewModel viewModel = new ViewModel("/smileboyhello.jsp");
		viewModel.addAttribute("strX", testvo);
		
		return viewModel;
	}

	@RequestTrack(value="exo")
	public TestVO testExo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		TestVO testvo = new TestVO();
		testvo.setId("56");
		testvo.setValue("valx");
		testvo.setNum(8);
		return testvo;
	}
	
	@RequestTrack(value="testvo")
	public TestVO testVOex(TestVO testvo) throws Exception{
		return testvo;
	}
}
