package com.leyou.item.service;

import com.leyou.item.bo.SpuBo;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.pojo.Stock;
import com.sun.prism.impl.Disposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;


    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupByCid(Long cid){

        SpecGroup record = new SpecGroup();
        record.setCid(cid);
        return this.specGroupMapper.select(record);
    }

    /**
     *根据条件gid查询规格参数
     * @param gid
     * @return
     */
    public List<SpecParam> queryParams(Long gid,Long cid,Boolean generic,Boolean searching) {
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);
        return this.specParamMapper.select(record);
    }


    /**
     * 新增参数
     * @param specParam
     * @return
     */
    @Transactional
    public void saveParam(SpecParam specParam) {
        System.out.println(specParam.getGroupId());
        this.specParamMapper.insert(specParam);
    }


    /**
     * 查询组和规格参数
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupsWithParam(Long cid) {
        List<SpecGroup> groups = this.queryGroupByCid(cid);
        groups.forEach(group->{
            List<SpecParam> params = this.queryParams(group.getId(), null, null, null);
            group.setParams(params);
        });
        return groups;
    }
}
