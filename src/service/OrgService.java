package service;

import entity.Numorg;
import entity.Organization;
import entity.Orgmsg;

/**
 * @author Chenli
 * @time 2017/3/16 15:47
 */
public interface OrgService {


    String buildOrg(Organization organization);

    String joinOrg(Numorg numorg);

    String createMsg(Orgmsg orgmsg);
}
