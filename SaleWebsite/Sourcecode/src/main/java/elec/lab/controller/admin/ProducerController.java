package elec.lab.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import elec.lab.domain.Order;
import elec.lab.domain.Producer;
import elec.lab.domain.Product;
import elec.lab.model.ProducerDto;
import elec.lab.repository.OrderRepository;
import elec.lab.repository.ProducerRepository;
import elec.lab.repository.ProductRepository;

@Controller
@RequestMapping("/admin/producers")
public class ProducerController {

	@Autowired
	ProducerRepository producerRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("producer", new ProducerDto());
		
		//set active front-end
		List<Order> listO = orderRepository.listall();
		model.addAttribute("orders", listO);
		model.addAttribute("demadmin", orderRepository.countAdminorder());
		model.addAttribute("menuPr", "menu");
		return "/admin/addProducer";
	}
	
	@PostMapping("/reset")
	public String reset(ModelMap model) {
		model.addAttribute("producer", new ProducerDto());
		
//		set active front-end
		List<Order> listO = orderRepository.listall();
		model.addAttribute("orders", listO);
		model.addAttribute("demadmin", orderRepository.countAdminorder());
		model.addAttribute("menuPr", "menu");
		return "/admin/addProducer";
	}

	@PostMapping("/add")
	public ModelAndView addd(ModelMap model, @Valid @ModelAttribute("producer") ProducerDto dto, BindingResult result) {
		if (result.hasErrors()) {
			
			//set active front-end
			model.addAttribute("menuPr", "menu");
			return new ModelAndView("admin/addProducer");
		}
		if (!checkProducer(dto.getName()) && !dto.isEdit()) {
			model.addAttribute("error", "Nh??n hi???u n??y ???? t???n t???i!");
			
			//set active front-end
			model.addAttribute("menuPr", "menu");
			return new ModelAndView("admin/addProducer", model);
		}

		Producer c = new Producer();
		BeanUtils.copyProperties(dto, c);
		producerRepository.save(c);
		if(dto.isEdit()) {
			model.addAttribute("message", "S???a th??nh c??ng!");
		} else {
			model.addAttribute("message", "Th??m th??nh c??ng!");
		}
		
		
		//set active front-end
		model.addAttribute("menuPr", "menu");
		return new ModelAndView("forward:/admin/producers", model);
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
		Optional<Producer> opt = producerRepository.findById(id);
		ProducerDto dto = new ProducerDto();
		if (opt.isPresent()) {
			Producer entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setEdit(true);
			model.addAttribute("producer", dto);
			
			//set active front-end
			model.addAttribute("menuPr", "menu");
			return new ModelAndView("/admin/addProducer", model);
		}

		model.addAttribute("error", "Kh??ng t???n t???i th????ng hi???u n??y!");

		//set active front-end
		model.addAttribute("menuPr", "menu");
		return new ModelAndView("forward:/admin/addProducers", model);
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, ModelMap model) {
		Optional<Producer> opt = producerRepository.findById(id);
		if (opt.isPresent()) {
//			categoryRepository.deleteProductByCategoryId(id);
			
			Page<Product> listP = productRepository.findAllProductByProducerId(id, PageRequest.of(0, 100));
			if(listP.getTotalElements() > 0) {
				model.addAttribute("error", "S???n ph???m c???a h??ng n??y v???n c??n, xin h??y xo?? s???n ph???m ???? tr?????c!");
			} else {
				producerRepository.delete(opt.get());
				model.addAttribute("message", "Xo?? th??nh c??ng!");
			}
			
		} else {			
			model.addAttribute("error", "H??ng n??y kh??ng t???n t???i!");
		}
		
		//set active front-end
		List<Order> listO = orderRepository.listall();
		model.addAttribute("orders", listO);
		model.addAttribute("demadmin", orderRepository.countAdminorder());
		model.addAttribute("menuPr", "menu");;
		return new ModelAndView("forward:/admin/producers", model);
	}

	@GetMapping("/search")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name,
			@RequestParam("size") Optional<Integer> size) {
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(0, pageSize);
		Page<Producer> list = producerRepository.findByNameContaining(name, pageable);
		model.addAttribute("producers", list);
		model.addAttribute("name", name);
		
		//set active front-end
		List<Order> listO = orderRepository.listall();
		model.addAttribute("orders", listO);
		model.addAttribute("demadmin", orderRepository.countAdminorder());
		model.addAttribute("menuPr", "menu");
		return "admin/producer";
	}

	@RequestMapping("/page")
	public String page(ModelMap model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @RequestParam(name = "name", required = false) String name) {
		int currentPage = page.orElse(0);
		int pageSize = size.orElse(5);
		if(name.equalsIgnoreCase("null")) {
			name = "";
		}
		Pageable pageable = PageRequest.of(currentPage, pageSize);
		Page<Producer> list = producerRepository.findByNameContaining(name, pageable);
		model.addAttribute("producers", list);
		model.addAttribute("name", name);
		
		//set active front-end
		List<Order> listO = orderRepository.listall();
		model.addAttribute("orders", listO);
		model.addAttribute("demadmin", orderRepository.countAdminorder());
		model.addAttribute("menuPr", "menu");
		return "admin/producer";
	}

	@RequestMapping("")
	public String list(ModelMap model, @RequestParam("size") Optional<Integer> size) {
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(0, pageSize);
		Page<Producer> list = producerRepository.findAll(pageable);
		model.addAttribute("producers", list);
		
		//set active front-end
		List<Order> listO = orderRepository.listall();
		model.addAttribute("orders", listO);
		model.addAttribute("demadmin", orderRepository.countAdminorder());
		model.addAttribute("menuPr", "menu");
		return "/admin/producer";
	}

	// Kiem tra ten thuong hieu
	boolean checkProducer(String name) {
		List<Producer> list = producerRepository.findAll();
		for (Producer item : list) {
			if (item.getName().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}
}
