package com.missfresh.demo.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.missfresh.common.base.AdminBaseController;
import com.missfresh.common.utils.Result;
import com.missfresh.demo.domain.DemoDO;
import com.missfresh.demo.service.DemoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 
 * <pre>
 * 基础表
 * </pre>
 * <small> 2018-07-27 23:38:24 | caigl@missfresh.cn</small>
 */
@Controller
@RequestMapping("/demo/demoBase")
public class DemoController extends AdminBaseController {
	@Autowired
	private DemoService demoBaseService;
	
	@GetMapping()
	@RequiresPermissions("demo:demoBase:demoBase")
	String DemoBase(){
	    return "demo/demoBase/demoBase";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("demo:demoBase:demoBase")
	public Result<Page<DemoDO>> list(DemoDO demoBaseDTO){
        Wrapper<DemoDO> wrapper = new EntityWrapper<DemoDO>().orderBy("id", false);
        wrapper.like("title", demoBaseDTO.getTitle());
        wrapper.like("content", demoBaseDTO.getContent());
        Page<DemoDO> page = demoBaseService.selectPage(getPage(DemoDO.class), wrapper);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("demo:demoBase:add")
	String add(){
	    return "demo/demoBase/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("demo:demoBase:edit")
	String edit(@PathVariable("id") Long id,Model model){
		DemoDO demoBase = demoBaseService.selectById(id);
		model.addAttribute("demoBase", demoBase);
	    return "demo/demoBase/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("demo:demoBase:add")
	public Result<String> save( DemoDO demoBase){
		boolean insert = demoBaseService.insert(demoBase);
        return insert ? Result.ok() : Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("demo:demoBase:edit")
	public Result<String>  update( DemoDO demoBase){
		boolean updateById = demoBaseService.updateById(demoBase);
		return updateById ? Result.ok() : Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("demo:demoBase:remove")
	public Result<String>  remove( Long id){
		demoBaseService.deleteById(id);
        return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("demo:demoBase:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] ids){
		demoBaseService.deleteBatchIds(Arrays.asList(ids));
		return Result.ok();
	}
	
}
