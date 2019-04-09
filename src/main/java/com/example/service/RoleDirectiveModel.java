package com.example.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import freemarker.core.Environment;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateScalarModel;

@Service
public class RoleDirectiveModel implements TemplateDirectiveModel {

	/**
	 * env:环境变量<br/>
	 * params:指令参数<br/>
	 * loopVars:循环变量<br/>
	 * body:指令内容<br/>
	 * 除了params外，其他的都能是null
	 */
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {

		System.out.println("RoleDirectiveModel.execute");

		TemplateScalarModel user = (TemplateScalarModel) params.get("user");
		TemplateScalarModel role = (TemplateScalarModel) params.get("role");

		if ("123456".equals(user.getAsString()) && "admin".equals(role.getAsString())) {
			loopVars[0] = TemplateBooleanModel.TRUE;
		}

		List<String> otherRights = new ArrayList<String>();
		otherRights.add("add");
		otherRights.add("delete");
		otherRights.add("update");
		loopVars[1] = new SimpleSequence(otherRights);

		body.render(env.getOut());
	}

}
