/** 
 * <pre>项目名称:ssm-user-wdd 
 * 文件名称:TreeBean.java 
 * 包名:com.jk.wdd.pojo 
 * 创建日期:2019年9月3日上午9:21:04 
 * Copyright (c) 2019, yuxy123@gmail.com All Rights Reserved.</pre> 
 */  
package com.jk.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/** 
 * <pre>项目名称：ssm-user-wdd    
 * 类名称：TreeBean    
 * 类描述：    
 * 创建人：wdd   
 * 创建时间：2019年9月3日 上午9:21:04    
 * 修改人：wdd 
 * 修改时间：2019年9月3日 上午9:21:04    
 * 修改备注：       
 * @version </pre>    
 */
@Data
public class TreeModel implements Serializable{

	private static final long serialVersionUID = -7470589936391688460L;
	
	private Integer id;
	private String text;
	private String url;
	private Integer pid;
	private List<TreeModel> children;
	private Boolean checked;
	private String flag;
	private String premission;
}
