package com.deskover.controller.ecommerce;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deskover.model.entity.database.UserAddress;
import com.deskover.model.entity.dto.ecommerce.BrandDTO;
import com.deskover.model.entity.dto.ecommerce.FlashSaleDTO;
import com.deskover.model.entity.dto.ecommerce.Item;
import com.deskover.service.ShopService;

@Controller
public class IndexController {
	
	@Autowired
	ShopService shopService;
	
	@GetMapping("")
	public String home() {
		return "redirect:index";
	}

	@GetMapping("/index")
	public String index(Model model) {
		List<Item> listItem1 = shopService.get4TopRate();
		List<Item> listItem2 = shopService.get4TopSale();
		List<Item> listItem3 = shopService.get4TopSold();
		
		model.addAttribute("list1", listItem1);
		model.addAttribute("list2", listItem2);
		model.addAttribute("list3", listItem3);
		
		FlashSaleDTO fs = shopService.getFlashSale();
		List<BrandDTO> brands = shopService.getListBrand();
		
		model.addAttribute("fs", fs);
		model.addAttribute("br", brands);
		
		return "index";
	}
	
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}
	
	@GetMapping("/shop")
	public String shop(HttpServletRequest request) {
		return "shop";
	}
	
	@GetMapping("/order")
	public String order() {
		return "order";
	}
	
	@GetMapping("/ok")
	public String ok() {
		return "ok";
	}
	
	@GetMapping("checkout")
	public String checkout(Model model) {
		return "checkout";
	}
	
	
}
