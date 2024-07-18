package com.dms.service.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dms.service.entity.DocumentConfig;
import com.dms.service.repository.DocumentConfigRepository;

@Service
public class ConfigurationService {

	private static Map<String, String> configurationMap;

	private final DocumentConfigRepository documentConfigRepository;

	@Autowired
	public ConfigurationService(DocumentConfigRepository documentConfigRepository) {
		this.documentConfigRepository = documentConfigRepository;
		init();
	}

	public void init() {
		List<DocumentConfig> configurationList;
		try {
			configurationMap = new HashMap<>();
			configurationList = documentConfigRepository.findAll();
			for (DocumentConfig config : configurationList) {
				configurationMap.put(config.getCode(), config.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getAiUrl() {
		return configurationMap.get("AI_URL");
	}

	public String getIntegartionFlag() {
		return configurationMap.get("INTEGRATION_WITH_AI_OFF");
	}
}
