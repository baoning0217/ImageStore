package com.vincce.image.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
  Created By BaoNing On 2019年1月22日
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageFile {

	public Integer id;
	
	private String name;

	private String url;


}
