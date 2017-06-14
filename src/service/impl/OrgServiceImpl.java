package service.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import dao.NumorgMapper;
import dao.OrganizationMapper;
import dao.OrgmsgMapper;
import entity.Numorg;
import entity.Organization;
import entity.Orgmsg;
import service.OrgService;
import utils.JsonMseeageFactory;

/**
 * @author Chenli
 * @time 2017/3/16 15:48
 */
@Service("orgService")
public class OrgServiceImpl implements OrgService{

    @Resource
    private OrganizationMapper organizationMapper;
    @Resource
    private NumorgMapper numorgMapper;
    @Resource
    private OrgmsgMapper orgmsgMapper;



    @Override
    public String buildOrg(Organization org) {
        int i = organizationMapper.insertSelective(org);
        String re = JsonMseeageFactory.makeSuccessMsg("成功建立");
        return re;
    }

    @Override
    public String joinOrg(Numorg numorg) {
        int i = numorgMapper.insertSelective(numorg);
        return JsonMseeageFactory.makeSuccessMsg("成功加入");
    }

    @Override
    public String createMsg(Orgmsg orgmsg) {
        int i = orgmsgMapper.insertSelective(orgmsg);
        return JsonMseeageFactory.makeSuccessMsg("成功创建");
    }

    @Override
    public Organization selectOrgById(String id) {
        Organization organization = organizationMapper.selectOrgById(id);

        return organization;
    }

    @Override
    public Orgmsg selectOrgmsgById(String id) {
        Orgmsg orgmsg = orgmsgMapper.selectOrgmsgById(id);

        return null;
    }
}
