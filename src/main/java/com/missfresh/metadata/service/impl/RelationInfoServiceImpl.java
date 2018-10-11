package com.missfresh.metadata.service.impl;

import org.springframework.stereotype.Service;

import com.missfresh.metadata.dao.RelationInfoDao;
import com.missfresh.metadata.domain.RelationInfoDO;
import com.missfresh.metadata.service.RelationInfoService;
import com.missfresh.common.base.CoreServiceImpl;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 
 * <pre>
 *     血缘关系
 * </pre>
 * <small> 2018-10-09 18:55:03 | cgl</small>
 */
@Service
public class RelationInfoServiceImpl extends CoreServiceImpl<RelationInfoDao, RelationInfoDO> implements RelationInfoService {

    @Override
    public HashMap<String, Object> nodeJson(String sourceDbName, String sourceTableName) {

        Assert.notNull(sourceDbName,"sourceDbName must be not null");
        Assert.notNull(sourceTableName,"sourceTableName must be not null");

        HashMap<String,Object> map=new HashMap();
        map.put("project","data_line_work_flow");
        map.put("projectId","1");
        map.put("flow","data_line_work_flow_end");

        List<HashMap<String,Object>>  nodes=new ArrayList<>();

        //子节点
        HashMap<String,String> tableDependents= baseMapper.getDependents(sourceDbName+"."+sourceTableName);
      if(!StringUtils.isEmpty(tableDependents.get("child"))){
            nodes.add(bulidNode(sourceDbName+"."+sourceTableName,tableDependents.get("child")));
            setChild(nodes,tableDependents.get("child"));
        }else {
            nodes.add(bulidNode(sourceDbName+"."+sourceTableName,"start"));
        }
       // nodes.add(bulidNode(sourceDbName+"."+sourceTableName,"start"));

        //父节点
        if(!StringUtils.isEmpty(tableDependents.get("parent"))){

           for(String p : tableDependents.get("parent").split(",")){
               nodes.add(bulidNode(p,sourceDbName+"."+sourceTableName));
           }
            HashSet<String> endNodeChild=new HashSet<>();
            //递归处理父节点
            setParent(nodes,tableDependents.get("parent"),endNodeChild);

        }else{
            nodes.add(bulidNode("end",sourceDbName+"."+sourceTableName));
        }

        //开始节点
        nodes.add(bulidNode("start",null));
        log.info(tableDependents.toString());

        for(int i=0;i<nodes.size();i++){
            HashSet<String> node=( HashSet<String>)(nodes.get(i).get("in"));

            if (node!=null && node.size()>0&&node.contains(nodes.get(i).get("id").toString())){
                node.remove(nodes.get(i).get("id"));
            }

        }

        map.put("nodes",nodes);
        return map;
    }

    //设置子节点
    private  void setChild(List<HashMap<String,Object>> nodes,String tableNames){

        List<HashMap<String,String>> childNodes = baseMapper.getChlidTable(buildSqlValue(tableNames));
        log.info("-------------"+childNodes.size()+"------"+childNodes);

        if (childNodes!=null && childNodes.size()>0){

            StringBuilder sb=new StringBuilder();
            for(String table:tableNames.split(",")) {
                //如果没有子节点，则将其指定到start节点
                boolean isHaveChlid = false;
                for(int i=0;i<childNodes.size();i++) {
                    if (childNodes.get(i).get("table_name").equals(table)) {
                        isHaveChlid = true;
                        nodes.add(bulidNode(table,childNodes.get(i).get("child")));
                        sb.append(","+childNodes.get(i).get("child"));
                        continue;
                    }
                }
                //指向start节点
                if(!isHaveChlid && !StringUtils.isEmpty(table) && !table.equals("null")){
                    log.info("================"+table);
                    nodes.add(bulidNode(table,"start"));
                }
            }
            tableNames=removeExitNode(nodes,new HashSet<String>(Arrays.asList((sb.length()>0?sb.substring(1):"").split(","))));
            if(!StringUtils.isEmpty(tableNames))
                 setChild(nodes,tableNames);
        }else {
            //递归结束  连接start节点
            for(String t:tableNames.split(",")){
                log.info("================++"+t);
               if(!StringUtils.isEmpty(t) && !t.equals("null"))
                   nodes.add(bulidNode(t,"start"));
            }
            return;
        }

    }



    // 设置其父节点
    private void setParent(List<HashMap<String,Object>> nodes,String tableNames, HashSet<String> endNodeChild){

      List<HashMap<String,String>> parentNodes = baseMapper.getParentTable(buildSqlValue(tableNames));

      if (parentNodes!=null && parentNodes.size()>0){
          HashMap<String,HashSet<String>> parentNodeMap=new HashMap<>();
          for(String child:tableNames.split(",")){
              //如果没有父节点，需要将其父节点指定到 end节点
              boolean isHaveParent=false;

              for(int i=0;i<parentNodes.size();i++){
                  if(parentNodes.get(i).get("table_name").equals(child)){
                      isHaveParent=true;
                      for(String parentNodeName:parentNodes.get(i).get("parent").split(",")) {
                          if(parentNodeMap.containsKey(parentNodeName)){
                              parentNodeMap.get(parentNodeName).add(child);

                          }else{
                              HashSet<String> set=new HashSet<>();
                              set.add(child);
                              parentNodeMap.put(parentNodeName,set);
                          }
                      }
                  }
              }
              if(!isHaveParent){
                  endNodeChild.add(child);
              }
          }
          // 删除已经存在的节点，防止出现死循环
          tableNames=removeExitNode(nodes,parentNodeMap.keySet());
          for(String key:parentNodeMap.keySet()){
                //如果已经已经存在该节点，则执行更新
                boolean flag=true;
                for(int i=0 ;i<nodes.size();i++){
                    if(nodes.get(i).get("id").equals(key)){
                        HashSet<String> in=(HashSet)nodes.get(i).get("in");
                        in.addAll(parentNodeMap.get(key));
                        flag=false;
                        continue;
                    }
                }
                if(flag){
                    nodes.add(bulidSetNode(key,parentNodeMap.get(key)));
                }

              //log.info("---------"+key+"--------"+parentNodeMap.get(key));
          }
          //递归所有的父节点
          setParent(nodes,tableNames,endNodeChild);


      }else{
          //递归结束  创建end节点
          for(String child:tableNames.split(",")){
              endNodeChild.add(child);
          }
          nodes.add(bulidSetNode("end",endNodeChild));
          return;
      }


    }


    // 去除已经存在的节点
    private String removeExitNode(List<HashMap<String,Object>> nodes,Set<String> parentTableNames){
        HashSet<String> newSet=new HashSet<String>();
        if(nodes.size()>0 && parentTableNames.size()>0){
            for(String key:parentTableNames){
                boolean flag=false;
                for(int i=0;i<nodes.size();i++){
                    //log.info(nodes.get(i).get("id").toString());
                    if(key.equals(nodes.get(i).get("id").toString())){
                        flag=true;
                        continue;
                    }
                }
                if(!flag){
                    newSet.add(key);
                }
            }

        }
        log.info("old"+parentTableNames.toString());
        log.info("new"+newSet.toString());
        return set2String(newSet);

    }


    private String  buildSqlValue(String tableNames){
        StringBuilder sb=new StringBuilder();
        if (!StringUtils.isEmpty(tableNames)&&tableNames.split(",").length>0){
            for(String table:tableNames.split(",")){
                sb.append(",'"+table+"'");
            }

        }
        return  sb.length()>0? sb.substring(1).toString():"";

    }




    //根据字符串创建节点
    private HashMap<String,Object> bulidNode(String id,String child){
        HashMap<String,Object> node=new HashMap();

        if(!StringUtils.isEmpty(child))
             node.put("in", new HashSet<>(Arrays.asList(child.split(","))));

        node.put("id",id);
        node.put("type","command");
        node.put("status","SUCCEEDED");
        return  node;
    }

    //根据set集合创建节点
    private HashMap<String,Object> bulidSetNode(String id,HashSet child){
        HashMap<String,Object> node=new HashMap();

        if(child!=null)
            node.put("in", child);
        node.put("id",id);
        node.put("type","command");
        node.put("status","SUCCEEDED");
        return  node;
    }

    //set集合转string
    private String set2String(Set<String> set){

        StringBuilder sb=new StringBuilder();

        if (set !=null && set.size()>0){
            for(String s:set){
                sb.append(","+s);
            }
        }

        return  sb.length()>0? sb.substring(1).toString():"";

    }


}

