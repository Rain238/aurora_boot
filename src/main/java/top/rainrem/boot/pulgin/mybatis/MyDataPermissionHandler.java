package top.rainrem.boot.pulgin.mybatis;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import top.rainrem.boot.common.annotation.DataPermission;
import top.rainrem.boot.common.base.IBaseEnum;
import top.rainrem.boot.common.enums.DataScopeEnum;
import top.rainrem.boot.core.util.SecurityUtils;

import java.lang.reflect.Method;

/**
 * 数据权限控制器
 *
 * @author LightRain
 * @since 2025年7月23日20:02:13
 */
@Slf4j
public class MyDataPermissionHandler implements DataPermissionHandler {

    /**
     * 获取数据权限的sql片段
     *
     * @param where             查询条件
     * @param mappedStatementId mapper接口方法的全路径
     * @return sql片段
     */
    @Override
    @SneakyThrows
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        // 如果是未登录，或者是定时任务执行的SQL，或者是超级管理员，直接返回
        if (SecurityUtils.getUserId() == null || SecurityUtils.isRoot()) {
            return where;
        }
        // 获取当前用户的数据权限
        Integer dataScope = SecurityUtils.getDataScope();
        DataScopeEnum dataScopeEnum = IBaseEnum.getEnumByValue(dataScope, DataScopeEnum.class);
        // 如果是全部数据权限，直接返回
        if (DataScopeEnum.ALL.equals(dataScopeEnum)) {
            return where;
        }
        // 获取当前执行的接口类
        Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(StringPool.DOT)));
        // 获取当前执行的方法名称
        String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(StringPool.DOT) + 1);
        // 获取当前执行的接口类里所有的方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //找到当前执行的方法
            if (method.getName().equals(methodName)) {
                DataPermission annotation = method.getAnnotation(DataPermission.class);
                // 判断当前执行的方法是否有权限注解，如果没有注解直接返回
                if (annotation == null) {
                    return where;
                }
                return dataScopeFilter(annotation.deptAlias(), annotation.deptIdColumnName(), annotation.userAlias(), annotation.userIdColumnName(), dataScopeEnum, where);
            }
        }
        return where;
    }

    /**
     * 构建过滤条件
     *
     * @param where 当前查询条件
     * @return 构建后查询条件
     */
    @SneakyThrows
    public static Expression dataScopeFilter(String deptAlias, String deptIdColumnName, String userAlias, String userIdColumnName, DataScopeEnum dataScopeEnum, Expression where) {
        String userColumnName = StrUtil.isNotBlank(userAlias) ? (userAlias + StringPool.DOT + userIdColumnName) : userIdColumnName;

        Long userId;
        String appendSqlStr;
        switch (dataScopeEnum) {
            case ALL:
                return where;
            case SELF:
                userId = SecurityUtils.getUserId();
                appendSqlStr = userColumnName + StringPool.EQUALS + userId;
                break;
            // 默认本人数据
            default:
                userId = SecurityUtils.getUserId();
                appendSqlStr = userColumnName + StringPool.EQUALS + userId;
                break;
        }

        if (StrUtil.isBlank(appendSqlStr)) {
            return where;
        }

        Expression appendExpression = CCJSqlParserUtil.parseCondExpression(appendSqlStr);

        if (where == null) {
            return appendExpression;
        }

        return new AndExpression(where, appendExpression);
    }


}

