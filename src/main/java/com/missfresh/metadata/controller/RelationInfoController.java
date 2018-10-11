package com.missfresh.metadata.controller;


import java.util.Arrays;
import java.util.HashMap;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.missfresh.common.base.AdminBaseController;
import com.missfresh.metadata.domain.RelationInfoDO;
import com.missfresh.metadata.service.RelationInfoService;
import com.missfresh.common.utils.Result;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2018-10-09 18:55:03 | missfresh</small>
 */
@Controller
@RequestMapping("/metadata/relationInfo")
public class RelationInfoController extends AdminBaseController {
	@Autowired
	private RelationInfoService relationInfoService;
	
	@GetMapping()
	@RequiresPermissions("metadata:relationInfo:relationInfo")
	String RelationInfo(){
	    return "metadata/relationInfo/relationInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("metadata:relationInfo:relationInfo")
	public Result<Page<RelationInfoDO>> list(RelationInfoDO relationInfoDTO){
        Wrapper<RelationInfoDO> wrapper = new EntityWrapper<RelationInfoDO>().orderBy("id", false);
        Page<RelationInfoDO> page = relationInfoService.selectPage(getPage(RelationInfoDO.class), wrapper);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("metadata:relationInfo:add")
	String add(){
	    return "metadata/relationInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("metadata:relationInfo:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		RelationInfoDO relationInfo = relationInfoService.selectById(id);
		model.addAttribute("relationInfo", relationInfo);
	    return "metadata/relationInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("metadata:relationInfo:add")
	public Result<String> save( RelationInfoDO relationInfo){
		relationInfoService.insert(relationInfo);
        return Result.ok();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("metadata:relationInfo:edit")
	public Result<String>  update( RelationInfoDO relationInfo){
		boolean update = relationInfoService.updateById(relationInfo);
		return update ? Result.ok() : Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("metadata:relationInfo:remove")
	public Result<String>  remove( Integer id){
		relationInfoService.deleteById(id);
        return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("metadata:relationInfo:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] ids){
		relationInfoService.deleteBatchIds(Arrays.asList(ids));
		return Result.ok();
	}


	/**
	 * 查看
	 */
	@GetMapping( "/dataline/{source_db_name}/{source_table_name}")
	@RequiresPermissions("metadata:relationInfo:relationInfo")
	String dataline(@PathVariable("source_db_name") String sourceDbName,@PathVariable("source_table_name") String sourceTableName,Model model){
		model.addAttribute("source_table_name",sourceTableName);
		model.addAttribute("source_db_name",sourceDbName);
		return "metadata/relationInfo/data_line";
	}

	/**
	 * 获取血缘关系图json数据
	 * @param sourceDbName
	 * @param sourceTableName
	 * @return
	 */
	@ResponseBody
	@PostMapping("/nodeJson/{source_db_name}/{source_table_name}")
	@RequiresPermissions("metadata:relationInfo:relationInfo")
	public HashMap<String,Object> nodeJson(@PathVariable("source_db_name") String sourceDbName, @PathVariable("source_table_name") String sourceTableName){
		return relationInfoService.nodeJson(sourceDbName,sourceTableName);
	}



}
