package co.deu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class AjaxServlet
 */
@WebServlet({ "/AjaxServlet", "/ajax.do" })
public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글 깨지지 않게
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String job = request.getParameter("job");
//		response.getWriter().append("Served at: ").append(request.getContextPath());	//getContextPath() 프로젝트 상위 정보
		PrintWriter out = response.getWriter();	//출력스트림
		
		if(job.equals("html")) {
			
			out.print("<h3>Ajax페이지입니다</h3>");
			out.print("<a href='index.html'>첫페이지로</a>");
			
		} else if(job.equals("json")) {
			
//			out.print("<h3>JSON페이지입니다</h3>");
//			out.print("<a href='index.html'>첫페이지로</a>");
			
			
			//[{"fname":?}, {"lname":?}, {}, {}]
//			String json = "[";
//			EmpDAO dao = new EmpDAO();
//			List<Employee> list = dao.empList();
//			for(int i=0; i<list.size(); i++) {
//				json += "{\"fname\":" + list.get(i).getFirstName() + "}";
//				if(i != list.size() -1) {
//					json += ",";
//				}
//			}
//			json += "]";
//			out.print(json);
			
			
			EmpDAO dao = new EmpDAO();
			List<Employee> list = dao.empList();
			
			Gson gson = new GsonBuilder().create();
			out.print(gson.toJson(list));
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		
		request.setCharacterEncoding("utf-8");		//응답정보?
		response.setCharacterEncoding("utf-8");		//요청정보?
		response.setContentType("text/html;charset=utf-8");
		
		String cmd = request.getParameter("cmd");
		
		String fname =  request.getParameter("fname");		//파라미터 읽어오기
		String lname =  request.getParameter("lname");
		String email =  request.getParameter("email");
		String hdate =  request.getParameter("hdate");
		String job =  request.getParameter("job");
		
		Employee emp = new Employee();
		emp.setFirstName(fname);
		emp.setLastName(lname);
		emp.setEmail(email);
		emp.setHireDate(hdate);
		emp.setJobId(job);
		
		//등록
		if(cmd.equals("insert")) {
				
			EmpDAO dao = new EmpDAO();
			dao.insertEmp(emp);
			
			System.out.println(emp);
			
		//수정
		} else if(cmd.equals("update")) {
			
			EmpDAO dao = new EmpDAO();
			if(dao.updateEmp(emp) == null) {
				// {"retCode" : "error"}
				System.out.println("error");
			} else {
				//{"retCode:"success"}
				System.out.println("success");
			};
		}
		
		Gson gson = new GsonBuilder().create();
		response.getWriter().print(gson.toJson(emp));
	}

}
