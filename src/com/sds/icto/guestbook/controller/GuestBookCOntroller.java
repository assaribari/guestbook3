package com.sds.icto.guestbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.icto.guestbook.dao.GuestBookDao;
import com.sds.icto.guestbook.vo.GuestBookVo;


@Controller
public class GuestBookCOntroller {

	@Autowired
	GuestBookDao GuestBookDao;
	
	@RequestMapping("/index")
	public String index(Model model) {
		List<GuestBookVo> list = GuestBookDao.fetchList();
		model.addAttribute("list", list);
		
		return "index";
	}
	

	@RequestMapping(value= "/insert", method=RequestMethod.POST)
	public String insert( @RequestParam("name") String name, 
			@RequestParam("pass") String password,
			@RequestParam("content") String message)
			{

		GuestBookVo vo = new GuestBookVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setMessage(message);
		
		GuestBookDao.insert(vo);
		return "redirect: index";
	
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteForm(){
		return "deleteform";
	}


	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String join( 
			@RequestParam("no") String no,
			@RequestParam("password") String password
			){

		GuestBookDao.delete(Long.parseLong(no), password);
		
		return "redirect:/index";
	}
	
}
