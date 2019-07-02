package com.jordi.spotify.exceptions;

import com.jordi.spotify.responses.SpotifyResponse;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SpotifyRestExceptionHandler {

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public SpotifyResponse unhandledErrors(HttpServletRequest req, Exception ex) {
		return new SpotifyResponse(ExceptionConstants.ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
	}

	@ExceptionHandler({ SpotifyException.class })
	@ResponseBody
	public SpotifyResponse handleLmException(final HttpServletRequest request, final HttpServletResponse response,
                                             final SpotifyException ex) {
		response.setStatus(ex.getCode());
		return new SpotifyResponse(ExceptionConstants.ERROR, String.valueOf(ex.getCode()), ex.getMessage(),
				ex.getErrorList());
	}
}
