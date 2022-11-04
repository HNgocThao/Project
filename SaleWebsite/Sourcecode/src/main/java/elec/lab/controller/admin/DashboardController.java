package elec.lab.controller.admin;


import java.util.Date;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



import elec.lab.domain.Category;
import elec.lab.domain.Order;
import elec.lab.domain.Producer;
import elec.lab.model.FillterDayByDay;
import elec.lab.model.ReportInventory;
import elec.lab.model.Statisitc;
import elec.lab.repository.CategoryRepository;
import elec.lab.repository.CustomerRepository;
import elec.lab.repository.OrderDetailRepository;
import elec.lab.repository.OrderRepository;
import elec.lab.repository.ProducerRepository;
import elec.lab.repository.ProductRepository;

@Controller
@RequestMapping("/admin/dashboards")
public class DashboardController {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ProducerRepository producerRepository;

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@RequestMapping("")
	public ModelAndView list(ModelMap model) {
//		Pageable pageable = PageRequest.of(0, 5);
//		Page<Product> list = productRepository.findAll(pageable);
//		model.addAttribute("products", list);
		
		List<Producer> listPr = producerRepository.findAll();
		model.addAttribute("producers", listPr);
		List<Category> listC = categoryRepository.findAll();
		model.addAttribute("categories", listC);
		
		model.addAttribute("dem1", customerRepository.countByuser());
		model.addAttribute("dem2", categoryRepository.countBycategory());
		model.addAttribute("dem3", productRepository.countByproduct());
		model.addAttribute("dem4", orderRepository.countByorder());
		
		
		// set active front-end
		List<Order> listO = orderRepository.listall();
		model.addAttribute("orders", listO);
		model.addAttribute("demadmin", orderRepository.countAdminorder());
		model.addAttribute("menuDa", "menu");
		return new ModelAndView("/admin/maindashboard", model);
	}
	

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/ahihi", method = RequestMethod.GET)
	public ResponseEntity<Object> chart(Pageable pageable, Integer t1, Integer t2){
		Pageable pageable1 = PageRequest.of(0,10);
        Date date = new Date();
        t1 = date.getMonth();
        t2 = date.getYear();
		List<ReportInventory> abc = productRepository.getTop10(pageable1, t1, t2);
        return ResponseEntity.ok(abc);
	}
	

	@RequestMapping(value = "/ahihi1", method = RequestMethod.GET)
	public ResponseEntity<Object> chart1(ModelMap model){
		List<ReportInventory> abc = productRepository.getorderbycategory();
        return ResponseEntity.ok(abc);
	}
	

	@RequestMapping(value = "/ahihi2", method = RequestMethod.GET)
    public ResponseEntity<Object> getStatistic30Day(){
        List<Statisitc> statistics = orderRepository.getStatistic30Day();
        return ResponseEntity.ok(statistics);
    }
	
	@RequestMapping(value = "/ahihi2", method = RequestMethod.POST)
    public ResponseEntity<Object> getStatisticDayByDay(@RequestBody FillterDayByDay filterDayByDay, Date s1, Date s2){
		s1 = filterDayByDay.getToDate();
		s2= filterDayByDay.getFromDate();
        List<Statisitc> statistics = orderRepository.getStatisticDayByDay(s1, s2);
        return ResponseEntity.ok(statistics);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@GetMapping("/ahihi")
//	public String insert(Map<String, ReportInventory> model) {
//		ReportInventory pr = new ReportInventory();
//		model.put("product", pr);
//		return "ahihi";
//	}
	
//	 @RequestMapping(value = "/ahihi", method = RequestMethod.GET)
//	    public ResponseEntity<List<Product>> listAllUsers() {
//	        List<Product> users = productRepository.getgetInventoryByCategory1();
//	        if(users.isEmpty()){
//	            return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
//	        }
//	        return new ResponseEntity<List<Product>>(users, HttpStatus.OK);
//	    }
}
