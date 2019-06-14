package com.wm.extendedwiremock.validation.implementation;

import com.wm.extendedwiremock.config.properties.RepositoryProperties;
import com.wm.extendedwiremock.config.properties.WiremockServerConfig;
import com.wm.extendedwiremock.validation.ValidWiremockServerConfig;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WiremockServerConfigValidator implements ConstraintValidator<ValidWiremockServerConfig, WiremockServerConfig> {

	@Autowired(required = false)
	public RepositoryProperties repositoryProperties;

	@Override
	public void initialize(ValidWiremockServerConfig constraintAnnotation) {
	}

	@Override
	public boolean isValid(WiremockServerConfig wiremockServerConfig, ConstraintValidatorContext constraintValidatorContext) {
		if (Optional.ofNullable(repositoryProperties).map(RepositoryProperties::getGit).isPresent()) {
			return !StringUtils.isEmpty(wiremockServerConfig.getMappingsFilesDir());
		}
		return true;
	}
}
