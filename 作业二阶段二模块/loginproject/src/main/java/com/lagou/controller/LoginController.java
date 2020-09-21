package com.lagou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.ResumeService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/resume")
public class LoginController {

    /**
     * Spring容器和SpringMVC容器是有层次的（父子容器）
     * Spring容器：service对象+dao对象
     * SpringMVC容器：controller对象，，，，可以引用到Spring容器中的对象
     */


    @Autowired
    private ResumeService resumeService;

    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    public ModelAndView  queryAll(@RequestParam(value="name", required=true)String name
    		,@RequestParam(value="password", required=true)String password,HttpSession session,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if("admin".equals(name) && "admin".equals(password)) {
    		session.setAttribute("username",name + System.currentTimeMillis());
    	} else {
        	response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("errMsg", "对不起，你没有权限,请重新登录!");
            request.getRequestDispatcher("/resume/index").forward(request, response);
            return null;
        }
    	List<Resume> resumes = resumeService.queryResumeList();
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("resumes",resumes);
        modelAndView.setViewName("success");
        session.setAttribute("name", name);
        session.setAttribute("password", password);
        return modelAndView;
    }
    
    @RequestMapping("/index")
    public ModelAndView  login() throws Exception {
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
    
    @RequestMapping("/update")
    public ModelAndView  add(String id) throws Exception {
    	ModelAndView modelAndView = new ModelAndView();
    	if(!StringUtils.isEmpty(id)) {
    		Resume resume = resumeService.findById(id);
        	modelAndView.addObject("resume",resume);
    	}
        modelAndView.setViewName("update");
        return modelAndView;
    }
    
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    public ModelAndView  save(Resume resume) throws Exception {
    	ModelAndView modelAndView = new ModelAndView();
    	resume.setName(java.net.URLDecoder.decode(resume.getName(),"UTF-8"));;
    	resumeService.save(resume);
    	List<Resume> resumes = resumeService.queryResumeList();
    	modelAndView.addObject("resumes",resumes);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    @RequestMapping(value = "/delete",method = {RequestMethod.GET})
    public ModelAndView  delete(String id) throws Exception {
    	ModelAndView modelAndView = new ModelAndView();
    	resumeService.delete(id);
    	List<Resume> resumes = resumeService.queryResumeList();
    	modelAndView.addObject("resumes",resumes);
        modelAndView.setViewName("success");
        return modelAndView;
    }
}
