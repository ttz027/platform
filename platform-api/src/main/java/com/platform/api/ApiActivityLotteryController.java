package com.platform.api;

import com.platform.annotation.IgnoreAuth;
import com.platform.entity.ActivityLotteryVo;
import com.platform.service.ApiActivityLotteryService;
import com.platform.util.ApiBaseAction;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "活动相关")
@RestController
@RequestMapping("/api/lottery")
public class ApiActivityLotteryController extends ApiBaseAction {
    @Autowired
    private ApiActivityLotteryService lotteryService;

    /**
     * 查看列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map param = new HashMap();
        param.put("page", page);
        param.put("limit", size);
        param.put("sidx", "add_time");
        param.put("order", "desc");
        //查询列表数据
        Query query = new Query(param);
        List<ActivityLotteryVo> adList = lotteryService.queryList(query);
        int total = lotteryService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(adList, total, query.getLimit(), query.getPage());

        return toResponsSuccess(pageUtil);
    }


    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        ActivityLotteryVo ad = lotteryService.queryObject(id);

        return R.ok().put("ad", ad);
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ActivityLotteryVo ad) {
        lotteryService.update(ad);

        return R.ok();
    }


}
