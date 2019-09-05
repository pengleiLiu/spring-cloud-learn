package org.lpl.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求过滤配置
 *
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-05 17:28
 **/
public class AccessFilter extends ZuulFilter {


  /**
   * 过滤器类型
   */
  @Override
  public String filterType() {
    return "pre";
  }

  /**
   * 过滤去器顺序
   */
  @Override
  public int filterOrder() {
    return 0;
  }

  /**
   * 判断过滤是否需要执行
   */
  @Override
  public boolean shouldFilter() {
    return true;
  }

  /**
   * 具体的执行逻辑
   */
  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();

    Object parameter = request.getParameter("accessToken");
    if (parameter == null) {

      ctx.setSendZuulResponse(false);
      ctx.setResponseStatusCode(401);
      ctx.setResponseBody("access filter failed");
      return null;
    }
    return null;
  }

}
