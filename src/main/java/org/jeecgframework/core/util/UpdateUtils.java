package org.jeecgframework.core.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * 作用:用来实体对象中的null赋值 名称:UpdateUtil 时间:2017-9-28 作者:Administrator
 */
public class UpdateUtils {
	/**
	 * 将空值的属性从目标实体类中复制到源实体类中
	 * 
	 * @param src
	 *            : 要将属性中的空值覆盖的对象(源实体类)
	 * @param target
	 *            :从数据库根据id查询出来的目标对象
	 */
	public static void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullProperties(src));
	}

	/**
	 * 将为空的properties给找出来,然后返回出来
	 * 
	 * @param src
	 * @return
	 */
	private static String[] getNullProperties(Object src) {
		BeanWrapper srcBean = new BeanWrapperImpl(src);
		PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
		Set<String> emptyName = new HashSet<>();
		for (PropertyDescriptor p : pds) {
			Object srcValue = srcBean.getPropertyValue(p.getName());
			if (srcValue == null)
				emptyName.add(p.getName());
		}
		String[] result = new String[emptyName.size()];
		return emptyName.toArray(result);
	}
}

/*
public ReturnMsg updateBanner(Banner banner){
Banner targetBanner=bannerImpl.findBannerById(banner.getBannerId());
UpdateUtil.copyNonNullProperties(banner,targetBanner);
if(bannerImpl.saveBanner(banner)!=null){
    return ReturnMsgUtil.getSuccess();
}
return ReturnMsgUtil.getFailMsg();

}
*/