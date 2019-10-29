package com.yh.kuangjia.services;

import com.yh.kuangjia.base.ResultList;
import com.yh.kuangjia.entity.AdminLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.kuangjia.models.AdminLog.AdminLogFilter;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 任性
 * @since 2019-10-29
 */
public interface AdminLogService extends IService<AdminLog> {

    ResultList GetPage(AdminLogFilter filter);
}
