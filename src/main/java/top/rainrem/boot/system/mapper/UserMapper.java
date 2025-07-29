package top.rainrem.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import top.rainrem.boot.common.annotation.DataPermission;
import top.rainrem.boot.core.security.model.UserAuthCredentials;
import top.rainrem.boot.system.model.bo.UserBO;
import top.rainrem.boot.system.model.dto.UserExportDTO;
import top.rainrem.boot.system.model.entity.User;
import top.rainrem.boot.system.model.form.UserForm;
import top.rainrem.boot.system.model.query.UserPageQuery;

import java.util.List;

/**
 * 用户持久层接口
 *
 * @author LightRain
 * @since 2025年7月22日19:27:13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户分页列表
     *
     * @param page        分页参数
     * @param queryParams 查询参数
     * @return 用户分页列表
     */
    @DataPermission(deptAlias = "u", userAlias = "u")
    Page<UserBO> getUserPage(Page<UserBO> page, UserPageQuery queryParams);

    /**
     * 获取用户表单详情
     *
     * @param userId 用户ID
     * @return 用户表单详情
     */
    UserForm getUserFormData(Long userId);

    /**
     * 根据用户名获取认证信息
     *
     * @param username 用户名
     * @return 认证信息
     */
    UserAuthCredentials getAuthCredentialsByUsername(String username);

    /**
     * 根据微信openid获取用户认证信息
     *
     * @param openid 微信openid
     * @return 认证信息
     */
    UserAuthCredentials getAuthCredentialsByOpenId(String openid);

    /**
     * 根据手机号获取用户认证信息
     *
     * @param mobile 手机号
     * @return 认证信息
     */
    UserAuthCredentials getAuthCredentialsByMobile(String mobile);

    /**
     * 获取导出用户列表
     *
     * @param queryParams 查询参数
     * @return 导出用户列表
     */
    @DataPermission(deptAlias = "u", userAlias = "u")
    List<UserExportDTO> listExportUsers(UserPageQuery queryParams);

    /**
     * 获取用户个人中心信息
     *
     * @param userId 用户ID
     * @return 用户个人中心信息
     */
    UserBO getUserProfile(Long userId);

}
