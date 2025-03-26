package gov.saip.applicationservice.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSourceService {

	private final MessageSource messageSource;

	/**
	 * to get message by key
	 *
	 * @param key
	 * @return
	 */
	public String getMessage(String key) {
		return messageSource.getMessage(key, new Object[] {}, LocaleContextHolder.getLocale());
	}

	/**
	 * to get message by key with array of paramters
	 * @param key
	 * @param params
	 * @return
	 */
	public String getMessage(String key , String [] params) {
		return messageSource.getMessage(key, params , LocaleContextHolder.getLocale());
	}

	public String getMessage(String message, String key ) {

		String []params = {key};

		return getMessage(message, params);
	}


}
